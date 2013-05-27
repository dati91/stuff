using UnityEngine;
using System.Collections;
using Umba;

public class Projectile_Fireball : Umba_Projectile {
		
	protected override void Awake(){
		base.Awake();
	}
	
	public void init(Spell_Fireball fireball, Vector3 target, Umba_Game game){
		Game = game;
		type_of_spell = Type.type_fireball;
		Shooter = fireball.owner_of_spell;
		Target = target;
		Target2D = new Vector2(target.x,target.z);
		Heading = Shooter.Heading;
		Position = Shooter.Position+Heading.normalized*Shooter.Radius;
		Origin = Position;
		Origin2D = Position2D;
		Position2D = new Vector2(Position.x,Position.z);
		MaxRange = fireball.max_cast_range;
		Damage = fireball.damage;
		MaxSpeed = fireball.speed;
		Mass = 1.0f;
		Power = fireball.power;
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
		
		if(collision.tag == "Player"){
	        Vector3 pushDir = new Vector3(transform.forward.x, 0,transform.forward.z);
	        collision.SendMessage("TakeThatDMG",new DamageParams((int) Damage,Shooter, pushDir.normalized * Power),SendMessageOptions.DontRequireReceiver);
		}else if(collision.tag == "trigger"){
			return;
		}
		DestroySpell();
	}
}
