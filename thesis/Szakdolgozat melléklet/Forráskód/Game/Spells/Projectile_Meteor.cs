using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using Umba;

public class Projectile_Meteor : Umba_Projectile {
	
	public float AOE { get; private set; }
	public float FallFromTheSky = 10.0f;
	
	protected override void Awake(){
		base.Awake();
	}
	
	public void init(Spell_Meteor meteor, Vector3 target,Umba_Game game){
		Game = game;
		type_of_spell = Type.type_meteor;
		Shooter = meteor.owner_of_spell;
		Target = target;
		Position = Shooter.Position + new Vector3(0,FallFromTheSky,0);
		Heading = Target - Position;
		Origin = Position;
		Origin2D = Position2D;
		Position2D = new Vector2(Position.x,Position.z);
		MaxRange = Vector3.Distance(Position,Target)+1.0f;
		Damage = meteor.damage;
		MaxSpeed = meteor.speed;
		Mass = 1.0f;
		Power = meteor.power;
		AOE = meteor.aoe;
	}
	
	void Update(){
		if(Death) return;
		
		Position += Heading.normalized * Time.deltaTime * MaxSpeed;
		Distance += Time.deltaTime * MaxSpeed;
		if(MaxRange < Distance){
			DestroySpell();
		}
	}
	
	void OnTriggerEnter(Collider collision) {
		if(Death) return;
		
		if(collision.tag == "floor"){
			List<Umba_bot> bots = Shooter.Game.Bots;
			Vector3 pushDir;
			foreach(Umba_bot bot in bots){
				if(bot.Equals(Shooter)) continue;
				float dist = (bot.Position - transform.position).magnitude;
				if(dist <= AOE){
					pushDir = new Vector3(bot.Position2D.x - Position.x,0,bot.Position2D.y - Position.z);
					bot.SendMessage("TakeThatDMG",new DamageParams((int) (Damage - (dist/AOE * Damage/2)),Shooter,pushDir.normalized * Power),SendMessageOptions.DontRequireReceiver);
				}
			}
		}else if(collision.tag == "Player"){
			return;
		}else if(collision.tag == "trigger"){
			return;
		}
		DestroySpell();
	}
	
	void OnDrawGizmos() {
		Gizmos.color = Color.red;
		Gizmos.DrawWireSphere(Position,AOE);
	}
}
