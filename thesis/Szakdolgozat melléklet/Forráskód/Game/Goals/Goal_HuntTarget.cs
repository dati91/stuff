
using UnityEngine;
using System.Collections;

public class Goal_HuntTarget : Goal_Composite {
	
	public Goal_HuntTarget(Umba_bot bot) :base(bot, goal_type.goal_hunt_target){}
		
	public override void Activate ()
	{
		status = goal_status.active;
		RemoveAllSubgoals();
		
		if(owner.TargetSys.isTargetPresent()){
			
			if(owner.TargetSys.isTargetShootable()){
				status = goal_status.completed;
				return;
			}
			
			Vector2 lrp = owner.TargetSys.GetLastRecordedPosition();
			
			if(lrp.Equals(Vector2.zero) || owner.isAtPosition(lrp)){
				AddSubgoal(new Goal_Explore(owner));
			}else{
				AddSubgoal(new Goal_MoveToPosition(owner,lrp));
			}
		}else{
			status = goal_status.completed;
		}
	}
	
	public override goal_status Process ()
	{
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		if(!owner.TargetSys.isTargetPresent()){
			status = goal_status.completed;
		}else if(owner.TargetSys.isTargetWithinRange()){
			status = goal_status.completed;
		}
		
		return status;
	}
	
	public override void Terminate (){
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}