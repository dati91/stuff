using UnityEngine;
using System.Collections;

public class Goal_Panic : Goal_Composite {
	
	Vector2 dest;
	float time;
	
	public Goal_Panic(Umba_bot bot) : base(bot,goal_type.goal_panic){
		dest = Vector2.zero;
	}
	
	public override void Activate ()
	{
		status = goal_status.active;
		RemoveAllSubgoals();
		
		float dist = float.MaxValue;
		
		foreach(Vector2 point in owner.Game.Map.BorderPoints){
			float d = Vector2.Distance(owner.Position2D,point);
			
			if(d < dist){
				dist = d;
				dest = point;
			}
		}
		time = Time.time;
		AddSubgoal(new Goal_SeekToPosition(owner,dest));
	}
	
	public override goal_status Process ()
	{
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		if(!owner.OnLava)
			status = goal_status.completed;
		else if(time + 1.0f < Time.time)
			status = goal_status.inactive;
		
		return status;
	}
	
	public override void Terminate ()
	{
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}
