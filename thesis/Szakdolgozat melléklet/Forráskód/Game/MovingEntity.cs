using UnityEngine;
using System.Collections;

public class MovingEntity : BaseGameEntity {

	float m_fInternalMass = 1;
	float m_fMaxSpeed = 7;
	float m_fMaxTurnRate = 5f;
	
		
	protected override void Awake()
	{
		base.Awake();
		GameObject = gameObject;
        Rigidbody = GetComponent<Rigidbody>();
		if(Rigidbody != null) Rigidbody.freezeRotation = true;
	}
	
	public GameObject GameObject { get; private set; }
		
	public Vector2 Velocity { get; set; }
	
	public Rigidbody Rigidbody { get; private set; }
	
	public float Mass {
		get
		{
			return (Rigidbody != null) ? Rigidbody.mass : m_fInternalMass;
		}
		protected set
		{
			if(Rigidbody != null)
			{
				Rigidbody.mass = value;
			}
			else
			{
				m_fInternalMass = value;
			}
		}
	}
	
	public Vector2 Heading2D {
		get
		{
			return new Vector2(Heading.x,Heading.z);
		}
		set
		{
			Heading = new Vector3(value.x,Heading.y,value.y);
		}
	}
	
	public Vector3 Heading {	
		get
		{
			return Transform.forward;
		}
		set
		{
			Transform.forward = value;
		}
	}
	
	public Quaternion Orientation {
		get 
		{
			return Transform.rotation;
		}
		set
		{
			Transform.rotation = value;
		}
	}

	public float MaxSpeed {
		get {
			return this.m_fMaxSpeed;
		}
		protected set {
			m_fMaxSpeed = Mathf.Clamp(value, 0, float.MaxValue);
		}
	}

	public float MaxTurnRate {
		get {
			return this.m_fMaxTurnRate;
		}
		protected set {
			m_fMaxTurnRate = Mathf.Clamp(value, 0.1f, float.MaxValue);
		}
	}
	
}