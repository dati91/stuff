
using UnityEngine;
using System.Collections;

public class Goal_DodgeSideToSide : Goal {
	
	bool direction;
	Vector2 strafetarget;
	
	public Goal_DodgeSideToSide(Umba_bot bot) :base(bot,goal_type.goal_dodge_side_to_side){
		direction = Random.value > 0.5f;
	}
	
	public override void Activate (){
		status = goal_status.active;
		owner.Steering.SeekOn();
		
		if(direction){
			if(owner.canStepRight(ref strafetarget)){
				owner.Steering.Target = strafetarget;
			}else{
				status = goal_status.inactive;
				direction = !direction;
			}
		}else{
			if(owner.canStepLeft(ref strafetarget)){
				owner.Steering.Target = strafetarget;
			}else{
				status = goal_status.inactive;
				direction = !direction;
			}
		}
	}
	
	public override goal_status Process ()
	{
		ActivateIfInactive();
		
		if(!owner.TargetSys.isTargetPresent() || !owner.TargetSys.isTargetWithinRange() || !owner.TargetSys.isTargetShootable()){
			status = goal_status.completed;
		}else if(owner.isAtPosition(strafetarget)){
			status = goal_status.inactive;
		}
		
		return status;
	}
	
	public override void Terminate ()
	{
		owner.Steering.SeekOff();
		status = goal_status.completed;
	}
}
