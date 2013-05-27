
using UnityEngine;
using System.Collections;

public class Goal_AttackMove : Goal_Composite {


	public Goal_AttackMove(Umba_bot bot):base(bot,goal_type.goal_attack_move){}
	
	public override void Activate (){
		status = goal_status.active;
		RemoveAllSubgoals();
		
		if(!owner.TargetSys.isTargetPresent()){
			status = goal_status.completed;
			return;
		}
		
		if(owner.TargetSys.isTargetShootable()){
			if(!owner.TargetSys.isTargetWithinRange()){
				AddSubgoal(new Goal_AdjustRange(owner));
			}else if(owner.canDodge()){
				AddSubgoal(new Goal_DodgeSideToSide(owner));
			}else{
				AddSubgoal(new Goal_SeekToPosition(owner,owner.TargetSys.GetTarget().Position2D));
			}
		}else{
			AddSubgoal(new Goal_HuntTarget(owner));
		}
	}
	
	public override goal_status Process(){
		
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		ReactivateIfFailed();
		
		if(owner.Hit){
			status = goal_status.inactive;
		}else if(!owner.TargetSys.isTargetPresent()){
			status = goal_status.failed;
		}else if(!owner.TargetSys.isTargetShootable()){
			status = goal_status.inactive;
		}
		
		return status;
	}
	
	public override void Terminate(){
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}
