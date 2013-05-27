using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class SparseGraph { 
	

	public SparseGraph(){}
	
	public List<NavGraphNode> Nodes = new List<NavGraphNode>();
	
	public List<NavGraphEdge> Edges = new List<NavGraphEdge>();
	
	public void AddNode(NavGraphNode node){
		Nodes.Add(node);
	}
	
	public void AddEdge(NavGraphEdge edge){
		Edges.Add(edge);
	}
	
	public bool Remove(int index) {
		NavGraphNode nodeToRemove = FindByIndex(index);
		if(nodeToRemove == null) return false;
		
		Nodes.Remove(nodeToRemove);
		
		foreach(NavGraphEdge edge in Edges){
			if(edge.From.Equals(index) || edge.To.Equals(index)){
				Edges.Remove(edge);
			}
		}
		return true;
	}
	
	 public NavGraphNode FindByIndex(int index)
    {
        foreach (NavGraphNode node in Nodes)
            if (node.index.Equals(index))
                return node;

        return null;
    }
	
	public bool UniqueEdge(NavGraphNode node1, NavGraphNode node2){
		foreach(NavGraphEdge edge in Edges){
			if(edge.Equals(node1,node2)){
				return false;
			}
		}
		return true;
	}
}