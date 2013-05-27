using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public abstract class Goal_Composite : Goal {
	
  protected List<Goal> subGoals = new List<Goal>();

  public Goal_Composite(Umba_bot owner, goal_type type):base(owner,type){}

	public void RemoveAllSubgoals(){
		while (subGoals.Count != 0){
			subGoals[subGoals.Count-1].Terminate();
			subGoals.RemoveAt(subGoals.Count-1);
		}
		if(subGoals.Count != 0){
			Debug.LogError("Hiba!! nem ures a lista");
		}
	}

	public goal_status ProcessSubgoals(){ 
		while (subGoals.Count != 0 && (subGoals[subGoals.Count-1].isComplete() || subGoals[subGoals.Count-1].hasFailed())){
			subGoals[subGoals.Count-1].Terminate();
			subGoals.RemoveAt(subGoals.Count-1);
		}
		
		if (subGoals.Count != 0){ 
			goal_status StatusOfSubGoals = subGoals[subGoals.Count-1].Process();
			
			if (StatusOfSubGoals.Equals(goal_status.completed) && subGoals.Count > 1)
				return goal_status.active;
			
			return StatusOfSubGoals;
		}
		
		return goal_status.completed;
	}

	public override void AddSubgoal(Goal g){   
		subGoals.Add(g);
	}
	
	public override bool HandleMessage (string msg){
	
		if(subGoals.Count > 0)
			return subGoals[subGoals.Count-1].HandleMessage(msg);

		return false;
	}
}