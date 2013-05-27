using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Umba_TargetingSystem {

	Umba_bot owner;
	Umba_bot CurrentTarget;
	public Umba_Projectile SpellThreat;
	
	public Umba_TargetingSystem(Umba_bot owner){
		this.owner = owner;
		this.CurrentTarget = null;
	}
	
	public void Update(){
		float ClosestDistSoFar = float.MaxValue;
		ClearTarget();
		
		List<Umba_bot> SensedBots = owner.SensoryMem.GetListOfRecentlySensedOpponents();
		foreach(Umba_bot bot in SensedBots){
			if(bot.isAlive() && bot != owner){
				float dist = Vector2.Distance(bot.Position2D,owner.Position2D);
				if(dist < ClosestDistSoFar){
					ClosestDistSoFar = dist;
					CurrentTarget = bot;
				}
			}
		}
		ClosestDistSoFar = float.MaxValue;
		List<Umba_Projectile> SensedSpells = owner.SensoryMem.GetListOfSensedSpells();
		foreach(Umba_Projectile spell in SensedSpells){
			if(spell != null && spell.Shooter != owner){
				if(owner.Game.CircleLineIntersect(spell.Origin2D, spell.MaxRange * spell.Heading2D + spell.Origin2D,owner.Position2D, 5f)){
					float dist = Vector2.Distance(owner.Position2D,spell.Position2D);
					if(dist < ClosestDistSoFar){
						ClosestDistSoFar = dist;
						SpellThreat = spell;
					}
				}
			}
		}
	}
	
	public bool isTargetPresent(){
		return CurrentTarget != null;
	}
	public bool isTargetWithinRange(){
		return owner.SensoryMem.isOpponentWithinRange(CurrentTarget);
	}
	public bool isTargetShootable(){
		return owner.SensoryMem.isOpponentShootable(CurrentTarget);
	}
	public Vector2 GetLastRecordedPosition(){
		return owner.SensoryMem.GetLastRecordedPositionOfOpponent(CurrentTarget);
	}
	public float GetTimeTargetHasBeenVisible(){
		return owner.SensoryMem.GetTimeOpponnetHasBeenVisible(CurrentTarget);
	}
	public float GetTimeTargetHasBeenOutOfView(){
		return owner.SensoryMem.GetTimeOpponnetHasBeenOutOfView(CurrentTarget);
	}
	public Umba_bot GetTarget(){
		return CurrentTarget;
	}
	public void ClearTarget(){
		CurrentTarget = null;
	}
}
