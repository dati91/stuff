using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;
using System.Threading;

public class Path_Planner {
	
	public Umba_bot owner;
	public SparseGraph NavGraph;
	Vector2 Destination;
	public Graph_SearchAStarTS search;
	public List<Vector2> path;

	int GetClosestNodeToPosition(Vector2 pos){
		float ClosestSoFar = float.MaxValue;
		
		int ClosestNode = -1;
		
		foreach(NavGraphNode Node in NavGraph.Nodes){
			float dist = Vector2.Distance(Node.Position, pos);
			
			if(dist < ClosestSoFar){
				ClosestSoFar = dist;
				ClosestNode = Node.index;
			}
		}
		
		return ClosestNode;
	}
	
	
	public Path_Planner(Umba_bot bot){
		owner = bot;
		NavGraph = owner.Game.Map.NavGraph;
		path = new List<Vector2>();
	}
	
	public bool RequestPathToPosition(Vector2 TargetPos){
		owner.Game.PathManager.UnRegister(this);
		path.Clear();
		
		Destination = TargetPos;
		
		if(!owner.Game.isPathObstructed(owner.Position2D, TargetPos)){
			return false;
		}
		int ClosestNodeToBot = GetClosestNodeToPosition(owner.Position2D);
		if(ClosestNodeToBot == -1) return false;
		
		int ClosestNodeToTarget = GetClosestNodeToPosition(TargetPos);
		if(ClosestNodeToTarget == -1) return false;
		
		search = new Graph_SearchAStarTS(NavGraph,ClosestNodeToBot, ClosestNodeToTarget);
		
		owner.Game.PathManager.Register(this);
		return true;
	}
	
	public int CycleOnce(){
		int result = search.CycleOnce();
		if(result == 1){
			path.Add(Destination);
			search.GetPathToTarget(ref path);
			SmoothPathQuick(path);
			owner.brain.PathRdy();
		}
		
		return result;
		
	}
	
	public void SmoothPathQuick( List<Vector2> path){
		
		if(path.Count < 2) return;
		int index = path.Count - 2;
		Vector2 p1 = owner.Position2D;
		Vector2 mid = path[index + 1];
		Vector2 p2 = path[index];
		
		while(index >= 0){
			
			p2 = path[index];
			if(owner.Game.isPathObstructed(p1,p2)){
				p1 = mid;
				mid = p2;
			}else{
				path.Remove(mid);
				mid = p2;
			}
			index--;
		}
	}
	
}
