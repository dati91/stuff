using UnityEngine;
using System.Collections;

public class EvadeAttackGoal_Evaluator : Goal_Evaluator {
	
	public EvadeAttackGoal_Evaluator(float bias) : base(bias){}
	
	public override float CalculateDesirability(Umba_bot bot){
		float Desirability = 0.0f;
		
		if(bot.TargetSys.SpellThreat != null){			
			Desirability = 0.8f;
			Desirability *= characterBias;
			Desirability = Mathf.Clamp(Desirability,0.0f,1.0f);
		}
		return Desirability;
	}
	
	public override void SetGoal(Umba_bot bot){
		bot.brain.AddGoal_EvadeAttack();
	}
}

