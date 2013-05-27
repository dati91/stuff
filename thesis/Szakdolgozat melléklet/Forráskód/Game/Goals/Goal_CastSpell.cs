using UnityEngine;
using System.Collections;

public class Goal_CastSpell : Goal {

	Vector2 pos2D;
	float castTime;
	float startTime;
	
	public Goal_CastSpell(Umba_bot bot, Vector3 target):base(bot,goal_type.goal_cast_spell){
		this.pos2D = new Vector2(target.x,target.z);
		castTime = 1f;
	}
	
	public override void Activate(){
		status = goal_status.active;
		startTime = Time.time;
		if(!owner.SpellSys.currentSpell.isReadyForNextShot()){
			status = goal_status.failed;
			return;
		}
	}
	
	public override goal_status Process(){
		ActivateIfInactive();
		
		if(Time.time > startTime + castTime){
			status = goal_status.failed;
		}else if(owner.SpellSys.currentSpell.isReadyForNextShot()){
			if(owner.Possessed){
				if(owner.RotateTowardPosition(pos2D)){
					owner.SpellSys.ShootAt2D(pos2D);
					status = goal_status.completed;
				}
			}else if(owner.TargetSys.isTargetPresent() && owner.SpellSys.TakeAimAndShoot(pos2D)){
					status = goal_status.completed;
			}
		}else{
			status = goal_status.failed;
		}
		
		return status;
	}
	
	public override void Terminate(){
		status = goal_status.completed;
	}
	
	
}
