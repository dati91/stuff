using UnityEngine;
using System.Collections;

public class ExploreGoal_Evaluator : Goal_Evaluator {
	
	public ExploreGoal_Evaluator(float bias) : base(bias){}
	
	public override float CalculateDesirability(Umba_bot bot){
		float Desirability = 0.05f;
		
		Desirability *= characterBias;
		Desirability = Mathf.Clamp(Desirability,0.0f,1.0f);
		return Desirability;
	}
	
	public override void SetGoal(Umba_bot bot){
		bot.brain.AddGoal_Explore();
	}
}
