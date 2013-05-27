using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Goal_MoveToPosition : Goal_Composite {

	Vector2 destination;
	bool waitForPath = false;
	public Goal_MoveToPosition(Umba_bot bot, Vector2 pos):base(bot,goal_type.goal_move_to_position){
		destination = pos;
	}
	
	public override void Activate ()
	{
		status = goal_status.active;
		RemoveAllSubgoals();	
		waitForPath =  owner.PathPlanner.RequestPathToPosition(destination);
		AddSubgoal(new Goal_SeekToPosition(owner,destination));
	}
	
	public override goal_status Process(){
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		ReactivateIfFailed();
		
		return status;
	}
	
	public override void Terminate(){
		if(waitForPath)
			owner.Game.PathManager.UnRegister(owner.PathPlanner);
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
	
	public void ModifyPath(){
		if(!waitForPath) {Debug.LogError("nem is varunk utat!");return;}
		waitForPath = false;
		RemoveAllSubgoals();
		AddSubgoal(new Goal_FollowPath(owner,owner.PathPlanner.path));
		
	}
	
	public override bool HandleMessage (string msg)
	{
		switch(msg){
		case "path ready":
			ModifyPath();
			return true;
		default: return false;
		}
	}
}
