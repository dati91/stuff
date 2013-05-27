using UnityEngine;
using System.Collections;
using Umba;

public class DamageParams {
	public int damage {get;set;}
	public Umba_bot source {get;set;}
	public Vector3 force {get;set;}
	
	public DamageParams(int dmg, Umba_bot src, Vector3 frc){
		damage = dmg;
		source = src;
		force = frc;
	}
}