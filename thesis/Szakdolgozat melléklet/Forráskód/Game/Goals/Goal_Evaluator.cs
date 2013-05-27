using UnityEngine;
using System.Collections;

public abstract class Goal_Evaluator {
	
	protected float characterBias;
	
	public Goal_Evaluator(float characterBias){this.characterBias = characterBias;}
	
	public abstract float CalculateDesirability(Umba_bot owner);
	
	public abstract void SetGoal(Umba_bot owner);
	
}
