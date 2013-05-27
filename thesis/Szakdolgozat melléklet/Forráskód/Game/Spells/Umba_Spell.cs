using UnityEngine;
using System.Collections;
using Umba;

public abstract class Umba_Spell {

	public Type type_of_spell;
	public float max_cast_range{get;private set;}
	public float cooldown{get; private set;}
	public float timeNextAvailable{get; private set;}
	public float damage{get; private set;}
	public float power{get;private set;}
	public float speed{get;private set;}
	public Umba_bot owner_of_spell{get;private set;}
	
	abstract public void ShootAt(Vector3 Target);
	
	public Type GetTypeOfSpell(){
		return type_of_spell;
	}
	
	public float getSpellSpeed(){
		return speed;
	}
	
	public bool isReadyForNextShot(){
		return Time.time > timeNextAvailable;
	}
	
	public float cdTime(){
		float cd = timeNextAvailable - Time.time;
		return Mathf.Clamp(cd,0.0f,cooldown);
	}
	
	public float getNextTimeAvailable(){
		return timeNextAvailable;
	}
	
	protected void UpdateTimeSpellIsNextAvailable(){
		timeNextAvailable = Time.time + cooldown;
	}
	
	public float getCooldown(){
		return cooldown;
	}

	public Umba_Spell(Umba_bot owner,
			   Type type,
			   float max_cast_range,
			   float cd,
			   float damage,
			   float power,
			   float speed){
		timeNextAvailable = Time.time;
		this.owner_of_spell = owner;
		this.type_of_spell = type;
		this.max_cast_range = max_cast_range;
		this.cooldown = cd;
		this.damage = damage;
		this.power = power;
		this.speed = speed;
	}
}
