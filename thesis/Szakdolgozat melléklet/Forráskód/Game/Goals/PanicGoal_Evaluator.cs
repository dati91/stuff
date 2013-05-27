using UnityEngine;
using System.Collections;

public class PanicGoal_Evaluator : Goal_Evaluator {
	
	public PanicGoal_Evaluator(float bias) : base(bias){}
	
	public override float CalculateDesirability(Umba_bot bot){
		float Desirability = 0.0f;
		
		if(bot.OnLava){
			
			Desirability = 1.5f;
			Desirability *= characterBias;
			Desirability = Mathf.Clamp(Desirability,0.0f,2.0f);
		}
		
		return Desirability;
	}
	
	public override void SetGoal(Umba_bot bot){
		bot.brain.AddGoal_Panic();
	}
}

