using UnityEngine;
using System.Collections;
using Umba;

public class Umba_bot : MovingEntity {
	
	public enum status {alive,dead,spawning};
	
	public status Status{get;private set;}
	public bool Possessed {get; private set;}
	public Umba_bot Target {get; private set;}
	public Umba_Game Game {get; private set;}
	public Umba_SpellSystem SpellSys {get; private set;}
	public Umba_SteeringBehaviors Steering {get; private set;}
	public Goal_Think brain {get; private set;}
	public Path_Planner PathPlanner { get; private set;}
	public Umba_SensoryMemory SensoryMem { get; private set;}
	public Umba_TargetingSystem TargetSys { get; private set;}
	public int Health { get; private set;}
	public int MaxHealth { get; private set;}
	public int Score { get; private set;}
	public int Death { get; private set;}
	public bool OnLava {get; set;}
	public bool Hit {get;private set;}
	public float lastHitTime {get;private set;}
	
	private Regulator SpellSelectionRegulator {get;set;}
	private Regulator GoalSelectionRegulator {get;set;}
	private Regulator TargetSelectionRegulator {get;set;}
	private Regulator VisionUpdateRegulator {get;set;}
	private Regulator LavaDMGRegualtor {get;set;}
	
	public float ViewRange { get; private set;}
	public float ReactionTime { get; private set;}
	public float MemorySpan { get; private set;}
	public float AimAccuracy { get; private set;}
	
	private const int yOffset = 40;
	private Vector3 screenPos {get;set;}
	
	public bool casting { get; set;}
	public float MaxCastingTurnRate { get; private set;}
	public float MaxRunSpeed { get; private set;}
	
	private Umba_bot lastDMGDealer = null;
	
	protected override void Awake()
	{
		base.Awake();
		
		ViewRange = 25.0f;
		ReactionTime = 0.2f;
		MemorySpan = 5.0f;
		AimAccuracy = 1.0f;
		
		casting = false;
		MaxCastingTurnRate = 20.0f;
		MaxRunSpeed = 5.0f;
		
		Status = status.alive;
		Possessed = false;
		Target = null;
		Game = GameObject.FindGameObjectWithTag("Game").GetComponent<Umba_Game>();
		SpellSys = new Umba_SpellSystem(this,ReactionTime,AimAccuracy);
		Steering = new Umba_SteeringBehaviors(this);
		brain = new Goal_Think(this);
		PathPlanner = new Path_Planner(this);
		SensoryMem = new Umba_SensoryMemory(this, MemorySpan);
		TargetSys = new Umba_TargetingSystem(this);
		SpellSelectionRegulator = new Regulator(0.5f); //2/sec
		GoalSelectionRegulator = new Regulator(0.25f); //4/sec
		TargetSelectionRegulator = new Regulator(0.5f); //2/sec
		VisionUpdateRegulator = new Regulator(0.25f); //4/sec
		
		LavaDMGRegualtor = new Regulator(0.1f);
		
		MaxHealth = 100;
		Health = MaxHealth;
		Score = 0;
		Death = 0;
		OnLava = false;
		
		screenPos = Camera.main.WorldToScreenPoint(transform.position);
		
	}
	
	void Update () {
		MaxSpeed = MaxRunSpeed;
		if(!isAlive()) return;
		
		screenPos = Camera.main.WorldToScreenPoint(transform.position);
		
		brain.Process();
		
		UpdateMovement();
		
		if(!Possessed) {
			
			if(TargetSelectionRegulator.isReady())
				TargetSys.Update();
			
			if(GoalSelectionRegulator.isReady())
				brain.Arbitrate();
			
			if(VisionUpdateRegulator.isReady())
				SensoryMem.UpdateVision();
			
			if(SpellSelectionRegulator.isReady())
				SpellSys.SelectSpell();
			
		}
		
		if(Hit && lastHitTime+0.5f < Time.time){
			Hit = false;
		}
	}
	
	void UpdateMovement(){
		Vector2 force = Steering.Calculate();
		
		if (force.Equals(Vector2.zero)){
			const float BrakingRate = 0.8f; 
			Velocity *= BrakingRate;
		}
		
		Vector2 accel = force / Mass;
		
		Velocity += accel;
		
		if(Velocity.magnitude > MaxSpeed)
			Velocity = Velocity.normalized * MaxSpeed;
		
		Position2D += Velocity * Time.deltaTime;
		
		if(!force.Equals(Vector2.zero) && !casting) 
			RotateTowardPosition(Steering.Target);
	}
	
	
	public bool RotateTowardPosition(Vector2 target){
		
		Vector3 toTarget = new Vector3(target.x - Position2D.x,0,target.y - Position2D.y);
		
		Quaternion dir = Quaternion.LookRotation(toTarget);
		
		const float WeaponAimTolerance = 0.3f;
		
		if(dir.Equals(Quaternion.Euler(Vector3.zero)) || Quaternion.Angle(Orientation,dir) < WeaponAimTolerance )
			return true;
		
		Orientation = Quaternion.LookRotation(Vector3.RotateTowards(Heading,toTarget,(casting?MaxCastingTurnRate:MaxTurnRate) * Time.deltaTime, 0.0f));
		
		return false;
	}
	
	void ReduceHealth(int val){
		Health -= val;
		
		if (Health <= 0)
			setDead();
	}

	void RestoreHealthToMaximum(){
		Health = MaxHealth;
	}

	void IncreaseHealth(int val){
	  Health+=val; 
	  Mathf.Clamp(Health, 0, MaxHealth);
	}
	
	public void TakePossession(){
	  if (!( isSpawning() || isDead() ) ){
	    Possessed = true;
		brain.RemoveAllSubgoals();
	    Debug.Log("Player Possesses bot "+gameObject.name);
	  }
	}
	
	public void Exorcise(){
	  Possessed = false;
	  brain.AddGoal_Explore();
	  Debug.Log("Player is exorcised from bot "+ gameObject.name);
	}

	public void CastSpell(Vector3 pos, Type type){
		SpellSys.ChangeSpell(type);
		if(SpellSys.currentSpell.isReadyForNextShot())
			brain.AddGoal_Casting(pos);
		else
			Debug.Log("Spell in cd: "+SpellSys.currentSpell.cdTime()+" sec remaining...");
	}
	
	void ApplyImpulse(Vector3 push){
		Rigidbody.AddForce(push,ForceMode.Impulse);
	}
	
	public void PathRdy(){
		brain.PathRdy();
	}
	
	public void Spawn(Vector2 pos){
		setAlive();
		brain.RemoveAllSubgoals();
		TargetSys.ClearTarget();
		Position2D = pos;
		Velocity = Vector2.zero;
		Rigidbody.velocity = Vector3.zero;
		SpellSys.Initialize();
		RestoreHealthToMaximum();
		lastDMGDealer = null;
		Hit = false;
	}
	
	public float CalculateTimeToReachPosition(Vector2 pos){
		return Vector2.Distance(Position2D,pos) / MaxSpeed;
	}
	
	public bool isAtPosition(Vector2 pos){
		const float tolerance = 1.0f;
		return Mathf.Pow(Vector2.Distance(pos,Position2D),2) < tolerance * tolerance;
	}
	
	public bool isAlive(){
		return Status.Equals(status.alive);
	}
	
	public bool isSpawning(){
		return Status.Equals(status.spawning);
	}
	
	public bool isDead(){
		return Status.Equals(status.dead);
	}
	
	public void IncrementScore(){
		Score++;
	}
	
	public void IncrementDeath(){
		Death++;
	}
	
	public void setAlive(){
		Status = status.alive;
	}
	
	public void setSpawning(){
		Status = status.spawning;
	}
	
	public void setDead(){
		Status = status.dead;
	}
	
	public void UserHasRemovedBot(Umba_bot rbot){
		SensoryMem.RemoveBotFromMemory(rbot);
		if(rbot.Equals(TargetSys.GetTarget()))
			TargetSys.ClearTarget();
		if(rbot.Equals(lastDMGDealer))
			lastDMGDealer = null;
	}
	
	public void SpellDestroyed(Umba_Projectile spell){
		SensoryMem.RemoveSpellFromMemory(spell);
	}
	
	public void YouGotMe(Umba_bot dbot){
		IncrementScore();
		if(dbot.Equals(TargetSys.GetTarget()))
			TargetSys.ClearTarget();
	}
	
	public void TakeThatDMG(DamageParams dmgp){
		if(isDead() || isSpawning()) return;
		
		lastDMGDealer = dmgp.source;
		Hit = true;
		lastHitTime = Time.time;
		ReduceHealth(dmgp.damage);
		ApplyImpulse(dmgp.force);
		
		if(isDead()){
			dmgp.source.SendMessage("YouGotMe",this,SendMessageOptions.DontRequireReceiver);
			IncrementDeath();
		}
	}
	
	public void TheLavaHatesYou(){
		if(isDead() || isSpawning()) return;
		
		if(LavaDMGRegualtor.isReady())
			ReduceHealth(1);
		
		if(isDead()){
			if(lastDMGDealer != null)
				lastDMGDealer.SendMessage("YouGotMe",this,SendMessageOptions.DontRequireReceiver);
			
			IncrementDeath();
		}
	}
	
	public void SpellSound(Umba_bot sbot){
		SensoryMem.UpdateWithSoundSource(sbot);
	}
	
	public bool canDodge(){
		Vector2 dummy = Vector2.zero;
		return canStepLeft(ref dummy) || canStepRight(ref dummy);
	}
	
	public bool canStepLeft(ref Vector2 pos){
		float StepDistance = ScaledRadius * 3.0f;
		Vector2 right = new Vector2(transform.right.x,transform.right.z);
		pos = Position2D + right.normalized * StepDistance;
		return canWalkTo(pos);
	}
	
	public bool canStepRight(ref Vector2 pos){
		float StepDistance = ScaledRadius * 3.0f;
		Vector2 right = new Vector2(transform.right.x,transform.right.z);
		pos = Position2D - right.normalized * StepDistance;
		return canWalkTo(pos);
	}
	
	public bool canWalkTo(Vector2 pos){
		if(pos.x < Game.Map.BottomX ||
			pos.x > Game.Map.TopX ||
			pos.y < Game.Map.LeftY ||
			pos.y > Game.Map.RightY)
			return false;
		return !Game.isPathObstructed(Position2D,pos);
	}
	
	void OnDrawGizmos() {
		Gizmos.color = Color.gray;
		Gizmos.DrawWireSphere(Position,ViewRange);
		if(Steering == null) return;
		Gizmos.color = Color.cyan;
		Gizmos.DrawLine(this.Position, new Vector3(Steering.Target.x, 0.5f, Steering.Target.y));
		
		if(TargetSys.SpellThreat == null) return;
		Gizmos.color = Color.green;
		Gizmos.DrawWireSphere(TargetSys.SpellThreat.Position,2.0f);
		Gizmos.color = Color.magenta;
		Gizmos.DrawLine(this.Position, TargetSys.SpellThreat.Position);
	}
	
	void OnGUI(){
		
		if(MaxHealth/3 > Health)
			GUI.contentColor = Color.red;
		else if(MaxHealth/3 * 2 < Health)
			GUI.contentColor = Color.green;
		else
			GUI.contentColor = Color.yellow;
		
    	GUI.Label(new Rect(screenPos.x - yOffset, Screen.height - screenPos.y - yOffset, 100, 20),  "H:"+Health.ToString()+" K:"+Score.ToString()+" D:"+Death.ToString());

	}
}
