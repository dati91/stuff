using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class MemoryRecord {
	
	public float TimeLastSensed {get;set;}
	public float TimeBecameVisible {get;set;}
	public float TimeLastVisible {get;set;}
	public Vector2 LastSensedPosition {get;set;}
	public bool WithinRange {get;set;}
	public bool Shootable {get;set;}
	
	public MemoryRecord(){
		TimeLastSensed = -999;
		TimeBecameVisible = -999;
		TimeLastVisible = 0;
		LastSensedPosition = Vector2.zero;
		WithinRange = false;
		Shootable = false;
	}
	
	
	
}