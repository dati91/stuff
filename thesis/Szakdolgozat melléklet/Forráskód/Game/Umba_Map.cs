using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Umba_Map {
	
	public SparseGraph NavGraph;
	public List<Vector2> SpawnPoints = new List<Vector2>();
	public List<Vector2> Obstacles = new List<Vector2>();
	public List<Vector2> BorderPoints = new List<Vector2>();
	public Umba_Game Game;
	
	public float TopX {get;private set;}
	public float BottomX {get;private set;}
	public float LeftY {get;private set;}
	public float RightY {get;private set;}

	public Umba_Map(Umba_Game game){
		NavGraph = null;
		Game = game;	
	}
	
	public void AddSpawnPoint(Vector2 sp){
		SpawnPoints.Add(sp);
	}
	
	public List<Vector2> GetSpawnPoints(){
		return SpawnPoints;
	}
	
	public void AddObstacle(Vector2 ob){
		Obstacles.Add(ob);
	}
	
	public List<Vector2> GetObstacles(){
		return Obstacles;
	}
	
	public void GenerateMap(float maxx, float maxy, bool donut, int obsnum ){
		
		TopX = maxx;
		BottomX = -maxx;
		LeftY = -maxy;
		RightY = maxy;
		
		float width = maxx * 2;
		float height = maxy * 2;

		
		float d = maxx;
		for(float i = -width + d/2; i <= width -d/2;i+=d){
			for(float j = -height + d/2; j <= height - d/2;j+= d){
				if((-maxx <= i && i <= maxx) && (-maxy <= j && j <= maxy))
					Game.AddFloor(i,j,d/10,d/10);
				else
					Game.AddLava(i,j,d/10,d/10);
			}
		}
		Game.AddWall(width,0,height * 2,1,true);
		Game.AddWall(-width,0,height * 2,1,true);
		Game.AddWall(0,height,width * 2,1,false);
		Game.AddWall(0,-height,width * 2,1,false);
		
		Vector2[] obspos = new Vector2[4] {new Vector2(maxx/2,0), new Vector2(-maxx/2,0), new Vector2(0,maxy/2),new Vector2(0,-maxy/2)};
		obsnum = Mathf.Clamp(obsnum,0,4);
		while(obsnum >0){
			int r = Random.Range(0,4);
			if(!Obstacles.Contains(obspos[r])){
				Obstacles.Add (obspos[r]);
				Game.AddObstacle(obspos[r],2.5f);
				obsnum--;
			}
		}
		AddSpawnPoint(new Vector2(maxx/2,maxy/2));
		AddSpawnPoint(new Vector2(maxx/2,-maxy/2));
		AddSpawnPoint(new Vector2(-maxx/2,-maxy/2));
		AddSpawnPoint(new Vector2(-maxx/2,maxy/2));

		float dist = maxx / 10.0f;
		dist = Mathf.Clamp(dist,1.0f,5.0f);
		
		NavGraph = new SparseGraph();
		int index = 1;
		for(float x = -maxx + dist; x <= maxx - dist; x += dist){
			for(float y = -maxy + dist; y <= maxy - dist; y += dist){
				if(!isObstacle(new Vector2(x,y))){
					NavGraph.AddNode(new NavGraphNode( index, new Vector2(x,y)));
					index++;
				}
			}
		}
		
		float nodedist = Mathf.Sqrt(dist * dist + dist * dist)+0.2f;
		foreach(NavGraphNode node1 in NavGraph.Nodes){
			foreach(NavGraphNode node2 in NavGraph.Nodes){
				if(node1.index != node2.index && Vector2.Distance(node1.Position,node2.Position) < nodedist){
					if(NavGraph.UniqueEdge(node1,node2)){
						NavGraph.AddEdge(new NavGraphEdge(node1.index,node2.index,Vector2.Distance(node1.Position,node2.Position)));
						if(node1.Neighbors.Contains(node2)) Debug.LogError("nem kellene...");
						if(node2.Neighbors.Contains(node1)) Debug.LogError("nem kellene...");
						node1.Neighbors.Add(node2);
						node2.Neighbors.Add(node1);
					}
				}
			}
		}
		
		foreach(NavGraphNode node in NavGraph.Nodes){
			if(node.Position.x == maxx - dist ||
			   node.Position.x == -maxx + dist||
			   node.Position.x == maxy - dist||
			   node.Position.x == -maxy + dist||
			   node.Position.y == maxx - dist||
			   node.Position.y == -maxx + dist||
			   node.Position.y == maxy - dist||
			   node.Position.y == -maxy + dist)
					BorderPoints.Add(node.Position);
		}
		
		LavaTrigger lt = GameObject.FindGameObjectWithTag("trigger").GetComponent<LavaTrigger>();
		lt.transform.localScale = new Vector3(width,10,height);
	}
	
	private bool isObstacle(Vector2 pos){
		foreach(Vector2 obs in Obstacles){
			if(Vector2.Distance(obs,pos) < 2.5f)
				return true;
		}
		return false;
	}
	
}
