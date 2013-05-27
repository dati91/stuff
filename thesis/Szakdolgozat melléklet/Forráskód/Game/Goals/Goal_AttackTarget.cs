
using UnityEngine;
using System.Collections;

public class Goal_AttackTarget : Goal_Composite {


	public Goal_AttackTarget(Umba_bot bot):base(bot,goal_type.goal_attack_target){}
	
	public override void Activate (){
		status = goal_status.active;
		RemoveAllSubgoals();
		
		if(!owner.TargetSys.isTargetPresent()){
			status = goal_status.completed;
			return;
		}
		
		if(owner.TargetSys.isTargetShootable() && owner.TargetSys.isTargetWithinRange()){
			AddSubgoal(new Goal_Casting( owner, owner.TargetSys.GetTarget().Position));
		}else{
			status = goal_status.completed;
		}
	}
	
	public override goal_status Process(){
		
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		//ReactivateIfFailed();
		
		return status;
	}
	
	public override void Terminate(){
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}
