using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class Graph_SearchAStarTS {
	
	SparseGraph Graph;
	int Source;
	int Target;
	NavGraphNode TargetNode;
	
	PriorityQueue Open;
	Node[] Closed;
	
	public Graph_SearchAStarTS(SparseGraph G, int source, int target) {
		Graph = G;
		Open = new PriorityQueue();
		Closed = new Node[Graph.Nodes.Count+1];
		Source = source;
		Target = target;
		TargetNode = Graph.Nodes[Target-1];
		
		Open.Enqueue(new Node(Source,0,0,Vector2.Distance(Graph.Nodes[Source-1].Position, TargetNode.Position)));

	}
	
	public int CycleOnce(){
		
		if(Open.Count() == 0){
			return -1;
		}
		
		Node NextClosestNode = Open.Dequeue();
		Closed[NextClosestNode.Index] = NextClosestNode;
		
		if(NextClosestNode.Index == Target){
			return 1;
		}
		
		NavGraphNode nextCNode = Graph.Nodes[NextClosestNode.Index -1];
			foreach(NavGraphNode node in nextCNode.Neighbors){
				int index = node.index;
				
				float hcost = Vector2.Distance(node.Position, TargetNode.Position);
				float gcost = NextClosestNode.GCost + Vector2.Distance(node.Position, nextCNode.Position);
				Node qnode = new Node(index, NextClosestNode.Index,gcost,gcost + hcost);
					
				if(Closed[qnode.Index] == null){
					Open.Enqueue(qnode);
				}else if(qnode.GCost < Closed[qnode.Index].GCost){
					Closed[qnode.Index] = qnode;
				}
			}
		
		return 0;
	}
	
	public void GetPathToTarget(ref List<Vector2> path){
		
		if(Target<0) {Debug.LogError("nincs ilyen"); return;}
		
		int nd = Target;
		path.Add(Graph.Nodes[nd-1].Position);
		while((nd != Source) && ( Closed[nd] != null)){
			nd = Closed[nd].Parent;
			path.Add(Graph.Nodes[nd-1].Position);
		}
	}
	
}
