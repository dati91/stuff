using UnityEngine;
using System.Collections;

public class Umba_SteeringBehaviors { 
	
	public Umba_SteeringBehaviors(Umba_bot owner){
		this.owner = owner;
	}

	private bool isSeek { get; set;}
	private bool isArrive {get; set;}
	
	public Umba_bot owner { get; private set; }
	public Vector2 SteeringForce {get;set;}
	public Vector2 Target {get;set;}
	
	
	public Vector2 Calculate(){
		SteeringForce = Vector2.zero;
		if(isSeek){
			SteeringForce += Seek(Target);
		}
		
		if(isArrive) {
			SteeringForce += Arrive(Target);
		}
		return SteeringForce;
	}
	
	public void SeekOn(){
		isSeek = true;
	}
	
	public void SeekOff(){
		isSeek = false;
	}
	
	public void ArriveOn(){
		isArrive = true;
	}
	
	public void ArriveOff(){
		isArrive = false;
	}
	
	Vector2 Seek(Vector2 TargetPos)
	{
		Vector2 DesiredVelocity = TargetPos - owner.Position2D;
		DesiredVelocity.Normalize();
		DesiredVelocity *= owner.MaxSpeed;

		return (DesiredVelocity - owner.Velocity);
	}
	
	Vector2 Arrive(Vector2 TargetPos)
	{
		Vector2 ToTarget = Target - owner.Position2D;
		
		float dist = ToTarget.magnitude;
		
		if(dist > 0)
		{
			const float DecelerationTweaker = 0.3f;
			
			float speed = dist / DecelerationTweaker;
			
			speed = Mathf.Min(speed,owner.MaxSpeed);
			
			Vector2 DesiredVelocity = ToTarget * speed / dist;
			
			return (DesiredVelocity - owner.Velocity);
		}
		
		return Vector2.zero;
	}
}