using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Goal_Think : Goal_Composite{
	
	private List<Goal_Evaluator> Evaluators = new List<Goal_Evaluator>();
	
	public Goal_Think(Umba_bot bot):base(bot,goal_type.goal_think){
		const float LowRangeOfBias = 0.5f;
		const float HighRangeOfBias = 1.5f;
		
		float ExploreBias = Random.Range(LowRangeOfBias,HighRangeOfBias);
		float AttackMoveBias = Random.Range(LowRangeOfBias,HighRangeOfBias);
		float AttackTargetBias = Random.Range(LowRangeOfBias,HighRangeOfBias);
		float EvadeAttackBias = Random.Range(LowRangeOfBias,HighRangeOfBias);
		float PanicBias = Random.Range(LowRangeOfBias,HighRangeOfBias);
		
		Evaluators.Add(new ExploreGoal_Evaluator(ExploreBias));
		Evaluators.Add(new AttackMoveGoal_Evaluator(AttackMoveBias));
		Evaluators.Add(new AttackTargetGoal_Evaluator(AttackTargetBias));
		Evaluators.Add(new EvadeAttackGoal_Evaluator(EvadeAttackBias));
		Evaluators.Add(new PanicGoal_Evaluator(PanicBias));
	}
	
	public void Arbitrate(){
		float best = 0;
		Goal_Evaluator MostDesirable = null;
		
		foreach(Goal_Evaluator ge in Evaluators){
			float desirability = ge.CalculateDesirability(owner);
			
			if(desirability >= best){
				best = desirability;
				MostDesirable = ge;
			}
		}
		
		if(MostDesirable == null) Debug.LogError("Goal_Think Arbitrate: no evaluator selected");
		MostDesirable.SetGoal(owner);
	}
	
	public bool notPresent(goal_type GoalType){
		if(subGoals.Count != 0){
			return !(subGoals[subGoals.Count-1].GetType().Equals(GoalType));
		}
		
		return true;
	}
	
	public override goal_status  Process(){
		ActivateIfInactive();
		
		goal_status SubgoalStatus = ProcessSubgoals();
		
		if(SubgoalStatus.Equals(goal_status.completed) || SubgoalStatus.Equals(goal_status.failed)){
			if(!owner.Possessed){
				status = goal_status.inactive;
			}
		}
		
		return status;
	}
	
	public override void Activate(){
		if(!owner.Possessed){
			Arbitrate();
		}
		status = goal_status.active;
	}
	
	public override void Terminate(){}
	
	public void AddGoal_MoveToPosition(Vector2 pos){
		AddSubgoal(new Goal_MoveToPosition(owner,pos));
	}
	
	public void AddGoal_Casting(Vector3 pos){
		if(notPresent(goal_type.goal_casting)){
			RemoveAllSubgoals();
			AddSubgoal(new Goal_Casting(owner,pos));
		}
	}
	
	public void AddGoal_Explore(){
		if(notPresent(goal_type.goal_explore)){
			RemoveAllSubgoals();
			AddSubgoal(new Goal_Explore(owner));
		}
	}
	
	public void AddGoal_AttackTarget(){
		if(notPresent(goal_type.goal_attack_target)){
			RemoveAllSubgoals();
			AddSubgoal(new Goal_AttackTarget(owner));
		}
	}
	
	public void AddGoal_AttackMove(){
		if(notPresent(goal_type.goal_attack_move)){
			RemoveAllSubgoals();
			AddSubgoal(new Goal_AttackMove(owner));
		}
	}
	
	public void AddGoal_Panic(){
		if(notPresent(goal_type.goal_panic)){
			RemoveAllSubgoals();
			AddSubgoal(new Goal_Panic(owner));
		}
	}
	
	public void AddGoal_EvadeAttack(){
		if(notPresent(goal_type.goal_evade_attack)){
			RemoveAllSubgoals();
			AddSubgoal(new Goal_EvadeAttack(owner));
		}
	}
	
	public void QueueGoal_MoveToPosition(Vector2 pos){
		if(subGoals.Count != 0){
			Goal lastGoal = subGoals[subGoals.Count-1];
			subGoals.Remove(lastGoal);
			AddSubgoal(new Goal_MoveToPosition(owner,pos));
			AddSubgoal(lastGoal);
		}else{
			AddSubgoal(new Goal_MoveToPosition(owner,pos));
		}
	}
	
	public void PathRdy(){
		this.HandleMessage("path ready");
	}

}
