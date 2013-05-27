using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class Goal_FollowPath : Goal_Composite {

	List<Vector2> path;
	public Goal_FollowPath(Umba_bot bot, List<Vector2> path):base(bot,goal_type.goal_follow_path){
		this.path = path;
	}
	
	public override void Activate ()
	{
		status = goal_status.active;
		Vector2 pos = path[path.Count-1];
		path.Remove(pos);
		AddSubgoal(new Goal_SeekToPosition(owner,pos));
	}
	
	public override goal_status Process(){
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		if(status == goal_status.completed && path.Count != 0){
			Activate();
		}
		
		return status;
	}
	
	public override void Terminate(){
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}
