using UnityEngine;
using System.Collections;

public class AttackMoveGoal_Evaluator : Goal_Evaluator {
	
	public AttackMoveGoal_Evaluator(float bias) : base(bias){}
	
	public override float CalculateDesirability(Umba_bot bot){
		float Desirability = 0.0f;
		
		if(bot.TargetSys.isTargetPresent()){
			
			Desirability =  Umba_Feature.Healt(bot) * Umba_Feature.TotalSpellStrength(bot);
			Desirability *= characterBias;
			Desirability = Mathf.Clamp(Desirability,0.0f,1.0f);
		}
		
		return Desirability;
	}
	
	public override void SetGoal(Umba_bot bot){
		bot.brain.AddGoal_AttackMove();
	}
}
