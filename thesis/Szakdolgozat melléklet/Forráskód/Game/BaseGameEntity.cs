using UnityEngine;
using System.Collections;

public class BaseGameEntity : MonoBehaviour { 
	
	float m_fRadius = 1;
	float m_fScaledRadius = 1;

	public Transform Transform { get; private set; }
	
	protected virtual void Awake()
	{
		Transform = GetComponent<Transform>();
		RecalculateScaledValues();
	}
	
	public Vector3 Position {
		get {
			if (Transform == null) {
				Transform = GetComponent<Transform>();
			}
			return Transform.position;
		}
		set {
			Transform.position = value;
		}
	}
	
	public Vector2 Position2D {
		get {
			return new Vector2(Position.x,Position.z);
		}
		
		set {
			Position = new Vector3(value.x,Position.y,value.y);
		}
	}
	
	public float Radius {
		get {
			return m_fRadius;
		}
		set {
			m_fRadius = Mathf.Clamp(value, 0.01f, float.MaxValue);
		}
	}
	
	public Vector3 Scale {
		get{
			if (Transform == null)
			{
				Transform = GetComponent<Transform>();
			}
			return Transform.lossyScale;
		}
	
	}
	
	public float ScaledRadius{
		get
		{
			return m_fScaledRadius;
		}
	}
	
	protected void RecalculateScaledValues(){
		m_fScaledRadius = m_fRadius * Mathf.Max(Scale.x, Mathf.Max(Scale.y, Scale.z));
	}
}