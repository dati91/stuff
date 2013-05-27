using UnityEngine;
using System.Collections;

public class Goal_Casting : Goal_Composite {

	Vector3 target;
	public Goal_Casting(Umba_bot bot, Vector3 pos):base(bot,goal_type.goal_casting){
		this.target = pos;
	}
	
	public override void Activate ()
	{
		status = goal_status.active;
		owner.casting = true;
		RemoveAllSubgoals();
		AddSubgoal(new Goal_CastSpell(owner,target));
	}
	
	public override goal_status Process(){
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		return status;
	}
	
	public override void Terminate(){
		owner.casting = false;
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}
