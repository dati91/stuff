
using UnityEngine;
using System.Collections;

public class Goal_Explore : Goal_Composite {

	bool destinationIsSet;
	Vector2 currentDestination;
	public Goal_Explore(Umba_bot bot):base(bot,goal_type.goal_explore){
		this.destinationIsSet = false;
	}
	
	public override void Activate ()
	{
		status = goal_status.active;
		RemoveAllSubgoals();
		
		if(!destinationIsSet){
			currentDestination = owner.Game.Map.NavGraph.Nodes[Random.Range(0,owner.Game.Map.NavGraph.Nodes.Count)].Position;
			destinationIsSet = true;
		}
		
		AddSubgoal(new Goal_MoveToPosition(owner,currentDestination));
	}
	
	public override goal_status Process(){
		ActivateIfInactive();
		
		status = ProcessSubgoals();
		
		return status;
	}
	
	public override void Terminate(){
		RemoveAllSubgoals();
		status = goal_status.completed;
	}
}
