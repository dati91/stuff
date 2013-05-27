using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class NavGraphNode : GraphNode{ 
	
	public Vector2 Position {set;get;}
	public List<NavGraphNode> Neighbors;
	
	public NavGraphNode(){}
	public NavGraphNode(int ind, Vector2 pos) : base(ind) {
		Position = pos;
		Neighbors = new List<NavGraphNode>();
	}

	
}

