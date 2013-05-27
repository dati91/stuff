using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class GraphEdge { 
	
	public int From {set;get;}
	public int To {set;get;}
	public float Cost {set;get;}

	public GraphEdge(int fr, int to, float cost){From = fr; To = to; Cost = cost;}
	public GraphEdge(int fr, int to){From = fr; To = to; Cost = 1;}
	public GraphEdge(){From = -1; To = -1; Cost = 1;}
	
	~GraphEdge(){}
	
	
	public bool Equals(int fr, int to){
		return (fr.Equals(this.From) && to.Equals(this.To)) || (fr.Equals(this.To) && to.Equals(this.From));
	}
	
	public bool Equals(NavGraphNode n1, NavGraphNode n2){
		return (n1.index.Equals(this.From) && n2.index.Equals(this.To)) || (n1.index.Equals(this.To) && n2.index.Equals(this.From));
	}
	
	public bool NotEquals(GraphEdge edge){
		return !(edge.Equals(this));
	}
	
}
