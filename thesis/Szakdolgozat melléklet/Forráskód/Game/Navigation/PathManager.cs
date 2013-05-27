using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class PathManager{
	
	List<Path_Planner> SearcRequests = new List<Path_Planner>();
	
	int NumSearcgCyclesPerUpdate;
	public PathManager(int ncpu) {NumSearcgCyclesPerUpdate = ncpu;}
	private int cur = 0;
	int NumCyclesRemaining = 0;
	bool removeCur = false;
	
	public void UpdateSearches(){	
		cur = 0;
		NumCyclesRemaining = NumSearcgCyclesPerUpdate;
		while(NumCyclesRemaining > 0 && SearcRequests.Count != 0){
			int result = SearcRequests[cur].CycleOnce();
			
			if( result == -1 || result == 1 || removeCur){
				SearcRequests.RemoveAt(cur);
				removeCur = false;
				
			}else{
				cur++;
			}
			
			
			if(cur == SearcRequests.Count) cur = 0;
			NumCyclesRemaining--;
		}
	}
	
	public void Register(Path_Planner pathplanner){
		if(!SearcRequests.Contains(pathplanner)){
			SearcRequests.Add(pathplanner);
		}
	}
	
	public void UnRegister(Path_Planner pathplanner){
		if(SearcRequests.Count > 0 && SearcRequests.Count < cur && pathplanner.Equals(SearcRequests[cur]) && NumCyclesRemaining > 0){
			removeCur = true;
		} else {
			SearcRequests.Remove(pathplanner);
		}
	}
	
	public int GetNumActiveSearces(){ return SearcRequests.Count;}
	public bool isFinished(){ return NumCyclesRemaining == 0 || SearcRequests.Count == 0;}
}
