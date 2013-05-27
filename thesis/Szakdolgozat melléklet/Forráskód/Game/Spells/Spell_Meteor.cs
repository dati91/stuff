using UnityEngine;
using System.Collections;
using Umba;

public class Spell_Meteor : Umba_Spell {
	
	public float aoe{get; private set;}

	public Spell_Meteor(Umba_bot owner):base(owner,
								Type.type_meteor,
								25.0f, //max range
								16.0f,  //cooldown
								15.0f,  //damage
								25.0f,  //power
								20.0f  //speed
								){
		aoe = 5.0f;
	}
	
	public override void ShootAt(Vector3 Target){
		if(isReadyForNextShot()){
			owner_of_spell.Game.AddMeteor(this,Target);
			UpdateTimeSpellIsNextAvailable();
		}
	}
}
