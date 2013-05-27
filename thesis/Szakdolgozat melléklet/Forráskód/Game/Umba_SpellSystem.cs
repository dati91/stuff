using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using Umba;

public class Umba_SpellSystem {

	Umba_bot owner;
	float ReactionTime;
	float AimAccuracy;
	public Umba_Spell currentSpell;
	const float GlobalCooldown = 0.5f;
	float nextTimeToShot;
	
	public Dictionary<Type,Umba_Spell> SpellMap = new Dictionary<Type, Umba_Spell>();	
	
	public Umba_SpellSystem(Umba_bot owner, float reaction_time,float aim_accuracy){
		this.owner = owner;
		this.ReactionTime = reaction_time;
		this.AimAccuracy = aim_accuracy;
		nextTimeToShot = Time.time;
		Initialize();
	}
	
	public void Initialize(){
		SpellMap.Clear();
		SpellMap.Add(Type.type_fireball,new Spell_Fireball(owner));
		SpellMap.Add(Type.type_meteor,new Spell_Meteor(owner));
		currentSpell = SpellMap[Type.type_fireball];
	}
	
	public void SelectSpell(){
		if(currentSpell.isReadyForNextShot()) return;
		foreach(var pair in SpellMap){
			if(pair.Value.isReadyForNextShot())
			{
				ChangeSpell(pair.Key);
				return;
			}
		}
	}
	
	public Umba_Spell GetSpellFromInventory(Type spellType){
		return SpellMap[spellType];
	}
	
	void UpdateShotTime(){
		nextTimeToShot = Time.time + GlobalCooldown;
	}
	
	bool isReadyForNextShot(){
		return (Mathf.Max(nextTimeToShot,currentSpell.getNextTimeAvailable()) < Time.time);
	}
	
	public void ChangeSpell(Type type){
		if(SpellMap.ContainsKey(type)){
			currentSpell = SpellMap[type];
		}
	}
	
	public void ShootAt(Vector3 pos){
		currentSpell.ShootAt(pos);
		UpdateShotTime();
	}
	
	public void ShootAt2D(Vector2 pos) {
		currentSpell.ShootAt(new Vector3(pos.x,owner.Position.y,pos.y));
		UpdateShotTime();
	}
	
	Vector2 PredictFuturePositionOfTarget(){
		
	  float MaxSpeed = currentSpell.getSpellSpeed();
	  
	  Vector3 ToEnemy = owner.TargetSys.GetTarget().Position2D - owner.Position2D;
	 
	  float LookAheadTime = ToEnemy.magnitude / 
	                        (MaxSpeed + owner.TargetSys.GetTarget().MaxSpeed);
	  
	  return owner.TargetSys.GetTarget().Position2D + 
	        owner.TargetSys.GetTarget().Velocity * LookAheadTime;
	}
	
	public bool TakeAimAndShoot(Vector2 AimingPos){
	  if(owner.TargetSys.isTargetPresent()){
	      AimingPos = PredictFuturePositionOfTarget();
	      if ( owner.RotateTowardPosition(AimingPos) && isReadyForNextShot() && owner.TargetSys.GetTimeTargetHasBeenVisible() > ReactionTime){	
			ShootAt2D(AimingPos);
			return true;
	      }
	  }
		
	return false;
	}
	
	public Vector2 AddNoiseToAim(Vector2 AimingPos){
		Vector2 toPos = AimingPos - owner.Position2D;
		Vector3 toPos3 = new Vector3(toPos.x,0,toPos.y);
		Quaternion noise = Quaternion.AngleAxis(Random.Range(-AimAccuracy,AimAccuracy),Vector3.forward);
		toPos3 = noise * toPos3;
		return toPos + AimingPos;	
	}
	
}
