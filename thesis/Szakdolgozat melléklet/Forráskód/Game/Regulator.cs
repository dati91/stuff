using UnityEngine;
using System.Collections;

public class Regulator{
	
	private float UpdatePeriod;
	private float LastUpdateTime;
	
	public Regulator(float period){
		UpdatePeriod = period;
		LastUpdateTime = 0;
	}
	
	public bool isReady(){
		if(Time.time > LastUpdateTime + UpdatePeriod) {
			LastUpdateTime = Time.time;
			return true;
		}
		
		return false;
	}
}