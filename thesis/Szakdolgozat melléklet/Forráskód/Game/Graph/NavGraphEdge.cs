using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class NavGraphEdge : GraphEdge { 
	
	public NavGraphEdge(int fr, int to, float cost) : base(fr,to,cost) {}
}

