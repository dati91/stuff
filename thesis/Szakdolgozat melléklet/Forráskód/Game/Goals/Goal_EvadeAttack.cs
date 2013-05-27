
using UnityEngine;
using System.Collections;
using Umba;

public class Goal_EvadeAttack : Goal_Composite {
	Umba_Projectile Spell;
	
	public Goal_EvadeAttack(Umba_bot bot) :base(bot, goal_type.goal_evade_attack){}
	
	public override void Activate ()
	{
		status = goal_status.active;
		
		Spell = owner.TargetSys.SpellThreat;
		if(Spell == null){
			status = goal_status.completed;
			return;
		}
		
		
		if(Spell.type_of_spell == Type.type_fireball){
			Vector2 evadePos = (owner.Position2D - Spell.Position2D).normalized * 2.0f;
			AddSubgoal(new Goal_MoveToPosition(owner,evadePos));
		}else if(Spell.type_of_spell == Type.type_meteor){
			Vector2 evadePos = (owner.Position2D - Spell.Position2D).normalized * 6.0f;
			AddSubgoal(new Goal_MoveToPosition(owner,evadePos));
		}else{
			status = goal_status.failed;
			return;
		}

	}
	
	public override goal_status Process ()
	{
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		if(owner.TargetSys.SpellThreat == null)
			status = goal_status.completed;
		
		return status;
	}
	
	public override void Terminate ()
	{
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}