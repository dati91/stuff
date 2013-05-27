using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public abstract class Goal {
	
	public enum goal_status {active, inactive, completed, failed};
	public enum goal_type{
		goal_think,
		goal_arrive_at_position,
		goal_seek_to_position,
		goal_follow_path,
		goal_move_to_position,
		goal_casting,
		goal_cast_spell,
		goal_explore,
		goal_attack_move,
		goal_attack_target,
		goal_adjust_range,
		goal_dodge_side_to_side,
		goal_hunt_target,
		goal_panic,
		goal_evade_attack
	};
	public Umba_bot owner;
	public goal_status status;
	public goal_type type;
	
	public Goal(Umba_bot owner, goal_type type){
		this.type = type;
		this.owner = owner;
		this.status = goal_status.inactive;
	}
	
	public bool isComplete(){return status.Equals(goal_status.completed);} 
	public bool isActive(){return status.Equals(goal_status.active);}
	public bool isInactive(){return status.Equals(goal_status.inactive);}
	public bool hasFailed(){return status.Equals(goal_status.failed);}
	public new goal_type GetType(){return this.type;}
	
	public void ReactivateIfFailed(){
		if (hasFailed())
			Activate();
	}
	public void ActivateIfInactive(){
		if (isInactive())
			Activate();
	}
	
	public abstract  void Activate();
	
	public abstract goal_status Process();
	
	public abstract  void Terminate();
	
	public virtual void AddSubgoal(Goal g){Debug.LogError("Cannot add goals to atomic goals");}
	
	public virtual bool HandleMessage(string msg){ return false;}
	
}
