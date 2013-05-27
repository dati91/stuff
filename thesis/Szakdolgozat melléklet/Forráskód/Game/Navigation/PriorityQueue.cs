using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class PriorityQueue
  {
    private List<Node> data;

    public PriorityQueue()
    {
      this.data = new List<Node>();
    }

    public void Enqueue(Node item)
    {
      data.Add(item);
      int ci = data.Count - 1;
      while (ci > 0)
      {
        int pi = (ci - 1) / 2; 
        if (data[ci].FCost >= data[pi].FCost) break; 
        Node tmp = data[ci]; data[ci] = data[pi]; data[pi] = tmp;
        ci = pi;
      }
    }

    public Node Dequeue()
    {
      int li = data.Count - 1;
      Node frontItem = data[0];
      data[0] = data[li];
      data.RemoveAt(li);

      --li;
      int pi = 0;
      while (true)
      {
        int ci = pi * 2 + 1;
        if (ci > li) break;
        int rc = ci + 1; 
        if (rc <= li && data[rc].FCost < data[ci].FCost) 
          ci = rc;
        if (data[pi].FCost <= data[ci].FCost) break; 
        Node tmp = data[pi]; data[pi] = data[ci]; data[ci] = tmp; 
        pi = ci;
      }
      return frontItem;
    }
	
	public void Modify(Node node){
		foreach(Node n in data){
			if(n.Index == node.Index) {
				n.Parent = node.Parent;
				n.GCost = node.GCost;
				n.FCost = node.FCost;
				
				int ci = data.Count - 1; 
				while (ci > 0){
					int pi = (ci - 1) / 2; 
					if (data[ci].FCost >= data[pi].FCost) break;
					Node tmp = data[ci]; data[ci] = data[pi]; data[pi] = tmp;
					ci = pi;
				}
				
				return;
			}
		}
	}
	
	public Node Get(int index) {
		foreach(Node n in data){
			if(n.Index == index) return n;
		}
		return null;
	}

    public int Count()
    {
      return data.Count;
    }
}

public class Node {
	
	public int Index {private set; get;}
	public int Parent {set;get;}
	public float GCost {set;get;}
	public float FCost {set;get;}
	
	public Node(int index, int parent, float gcost, float fcost){
		Index = index;
		Parent = parent;
		GCost = gcost;
		FCost = fcost;
	}
	
	public override string ToString(){
		return "("+Index+","+Parent+","+GCost+","+FCost+")";
	}
}