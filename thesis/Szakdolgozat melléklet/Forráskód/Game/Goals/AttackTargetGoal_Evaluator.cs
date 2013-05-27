using UnityEngine;
using System.Collections;

public class AttackTargetGoal_Evaluator : Goal_Evaluator {
	
	public AttackTargetGoal_Evaluator(float bias) : base(bias){}
	
	public override float CalculateDesirability(Umba_bot bot){
		float Desirability = 0.0f;
		
		if(bot.TargetSys.isTargetPresent() && bot.TargetSys.isTargetWithinRange() && bot.TargetSys.isTargetShootable()){			
			Desirability = bot.SpellSys.currentSpell.isReadyForNextShot()?1:0;
			Desirability *= characterBias;
			Desirability = Mathf.Clamp(Desirability,0.0f,1.0f);
		}
		return Desirability;
	}
	
	public override void SetGoal(Umba_bot bot){
		bot.brain.AddGoal_AttackTarget();
	}
}
