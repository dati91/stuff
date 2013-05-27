using UnityEngine;
using System.Collections;
using Umba;

public abstract class Umba_Projectile : MovingEntity {

	public Umba_bot Shooter { get; protected set; }
	public Vector3 Target { get; protected set; }
	public Vector2 Target2D {get; protected set;}
	public Vector3 Origin { get; protected set; }
	public Vector2 Origin2D {get; protected set;}
	public float MaxRange{ get; protected set; }
	public float Damage { get; protected set; }
	public float TimeOfCreation { get; private set; }
	public float Power { get; protected set; }
	public float Distance { get; protected set; }
	public bool Death {get;protected set;}
	public Type type_of_spell {get; protected set;}
	public Umba_Game Game {get; protected set; }
	
	protected override void Awake(){
		base.Awake();
		TimeOfCreation = Time.time;
		MaxTurnRate = 0;
		Velocity = Vector3.zero;
		Distance = 0;
		Death = false;
	}

	protected void DestroySpell(){
		Game.SendMessage("RemoveSpell",this,SendMessageOptions.DontRequireReceiver);
		Death = true;
	}

}
