using UnityEngine;
using System.Collections;
using Umba;

public class Spell_Fireball : Umba_Spell {

	public Spell_Fireball(Umba_bot owner):base(owner,
								Type.type_fireball,
								25.0f, //max cast range
								4.8f,  //cooldown
								7.0f,  //damage
								15.0f,  //power
								20.0f  //speed
								){		
	}
	
	public override void ShootAt(Vector3 Target){
		if(isReadyForNextShot()){
			owner_of_spell.Game.AddFireball(this,Target);
			UpdateTimeSpellIsNextAvailable();
		}
	}
}
