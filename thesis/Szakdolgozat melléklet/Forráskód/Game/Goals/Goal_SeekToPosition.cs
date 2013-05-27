using UnityEngine;
using System.Collections;

public class Goal_SeekToPosition : Goal {

	Vector2 pos;
	float timeToReachPos;
	float startTime;
	bool last = true;
	
	bool isStuck(){
		float TimeTaken = Time.time - startTime;
		
		if(TimeTaken > timeToReachPos){
			return true;
		}
		return false;
	}
	
	public Goal_SeekToPosition(Umba_bot bot, Vector2 target):base(bot,goal_type.goal_seek_to_position){
		this.pos = target;
		this.timeToReachPos = 0.0f;
	}
	
	public Goal_SeekToPosition(Umba_bot bot, Vector2 target,bool last):base(bot,goal_type.goal_seek_to_position){
		this.pos = target;
		this.timeToReachPos = 0.0f;
		this.last = last;
	}
	
	public override void Activate(){
		status = goal_status.active;
		startTime = Time.time;
		timeToReachPos = owner.CalculateTimeToReachPosition(pos);
		
		const float MarginOfError = 1.0f;
		
		timeToReachPos += MarginOfError;
		
		owner.Steering.Target = pos;
		
		if(last) owner.Steering.ArriveOn();
		else owner.Steering.SeekOn();
	}
	
	public override goal_status Process(){
		ActivateIfInactive();
		
		if(isStuck()){
			status = goal_status.failed;
		}else{
			if(owner.isAtPosition(pos)){
				status = goal_status.completed;
			}
		}
		
		return status;
	}
	
	public override void Terminate(){
		owner.Steering.SeekOff();
		owner.Steering.ArriveOff();
		status = goal_status.completed;
	}
	
	
}
