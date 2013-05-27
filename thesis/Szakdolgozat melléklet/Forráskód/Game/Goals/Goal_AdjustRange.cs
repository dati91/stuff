
using UnityEngine;
using System.Collections;

public class Goal_AdjustRange : Goal {
	
	public Goal_AdjustRange(Umba_bot bot) :base(bot,goal_type.goal_adjust_range) {}
	
	public override void Activate (){
		status = goal_status.active;
		owner.Steering.SeekOn();
		owner.Steering.Target = owner.TargetSys.GetTarget().Position2D;
		
	}
	
	public override goal_status Process (){
		ActivateIfInactive();
		
		if(owner.TargetSys.isTargetWithinRange() || !owner.TargetSys.isTargetShootable()){
			status = goal_status.completed;
		}else{
			status = goal_status.inactive;
		}
		
		return status;
	}
	
	public override void Terminate ()
	{
		owner.Steering.SeekOff();
	}
}
