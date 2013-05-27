using UnityEngine;
using System.Collections;
using Umba;
using System.Collections.Generic;

public class Umba_Game : MonoBehaviour {
	
	public GameObject spell_meteor;
	public GameObject spell_fireball;
	public Material mat_fireball;
	public Material mat_meteor;
	public Material mat_lava;
	public Material mat_floor;
	public Material mat_obs;
	public Material mat_bot;
	private GameObject spellLocation;
	public GameObject spellTarget;
	public GameObject bot;
	public GameObject floor;
	public GameObject column;
	public GameObject wall;
	public GameObject goBots;
	public GameObject goSpells;
	public GameObject goLevel;
	public GameObject goFloor;
	public GameObject goLava;
	public GameObject goObstacles;
	public GameObject goWalls;
	
	private bool targeting {get;set;}
	private Type spell {get;set;}
	
	private bool removeBot = false;
	
	private float lastClickTime;
	
	public Umba_Map Map {get;private set;}
	public List<Umba_bot> Bots {get;private set;}
	public List<Umba_Projectile> Spells {get;private set;}
	public Umba_bot currentPlayer {get;private set;}
	public PathManager PathManager {get;private set;}
	
	public bool Paused;
	
	void Awake(){
		Debug.Log("Game Start");
		goLevel = this.gameObject;
		Bots = new List<Umba_bot>();
		Spells = new List<Umba_Projectile>();
		targeting = false;
		spell = Type.type_none;
		lastClickTime = Time.time;
		Map = new Umba_Map(this);
		Map.GenerateMap(20f,20f,false,Random.Range(0,5));
		PathManager = new PathManager(100);
	}
	
	void Update () {
	
		getPlayerInput();
		StartCoroutine(UpdateSearches());
		foreach(Umba_bot b in Bots){
			if(b.OnLava)
				b.SendMessage("TheLavaHatesYou",SendMessageOptions.DontRequireReceiver);
			
			if(b.isSpawning()){
				ReSpawnBot(b);
			}else if(b.isDead()){
				b.setSpawning();
			}
			
		}
		
		if(removeBot){
			RemoveBot();
			removeBot = false;
		}
	}
	
	public IEnumerator UpdateSearches(){
		PathManager.UpdateSearches();
		yield return null;
	}
	
	public void AddFireball(Spell_Fireball fb, Vector3 Target){
		GameObject fireball = Instantiate(spell_fireball) as GameObject;
		fireball.transform.parent = goSpells.transform;
		Projectile_Fireball pf = (Projectile_Fireball) fireball.GetComponent<Projectile_Fireball>();
		pf.init(fb,Target,this);
		Spells.Add(pf);
	}
	
	public void AddMeteor(Spell_Meteor m, Vector3 Target){
		GameObject meteor = Instantiate(spell_meteor) as GameObject;
		meteor.transform.parent = goSpells.transform;
		Projectile_Meteor pm = (Projectile_Meteor) meteor.GetComponent<Projectile_Meteor>();
		pm.init(m,Target,this);
		Spells.Add(pm);
	}
	
	public void AddFloor(float x, float y, float w, float h){
		GameObject f = Instantiate(floor) as GameObject;
		f.transform.parent = goFloor.transform;
		f.transform.position = new Vector3(x,0,y);;
		f.transform.localScale = new Vector3(w,1,h);
	}
	
	public void AddLava(float x, float y, float w, float h){
		GameObject f = Instantiate(floor) as GameObject;
		f.transform.parent = goLava.transform;
		f.renderer.material = mat_lava;
		f.transform.position = new Vector3(x,0,y);
		f.transform.localScale = new Vector3(w,1,h);
	}
	
	public void AddObstacle(float x, float y, float radius){
		AddObstacle(new Vector2(x,y),radius);
	}
	
	public void AddObstacle(Vector2 pos, float radius){
		GameObject c = Instantiate(column) as GameObject;
		c.transform.parent = goObstacles.transform;
		c.transform.position = new Vector3(pos.x,2,pos.y);
		c.transform.localScale = new Vector3(radius,2,radius);
	}
	
	public void AddWall(float x, float y, float w, float h, bool r){
		GameObject nw = Instantiate(wall) as GameObject;
		nw.transform.parent = goWalls.transform;
		nw.transform.position = new Vector3(x ,1f,y );
		nw.transform.localScale = new Vector3(w ,2,h);
		nw.transform.rotation = Quaternion.Euler(new Vector3(0,r?90:0,0));
	}
	
	public void RemoveSpell(Umba_Projectile spell){
		if(Spells.Count == 0){
			Debug.LogError("Error: Umba_Game::RemoveSpell Spell count is zero");
			return;
		}
		NotifyAllBotsOfRemoval(spell);
		Spells.Remove(spell);
		Destroy(spell.gameObject);
	}
	
	void AddBot(){
		GameObject b = Instantiate(bot) as GameObject;
		b.transform.parent = goBots.transform;
		Umba_bot newBot = (Umba_bot) b.GetComponent("Umba_bot");
		ReSpawnBot(newBot);
		Bots.Add(newBot);
	}
	
	void ReSpawnBot(Umba_bot dbot){
		dbot.Spawn((Vector2) Map.SpawnPoints[Random.Range(0,Map.SpawnPoints.Count)]);
	}
		
	public void NotifyAllBotsOfRemoval(Umba_bot bot){
		foreach(Umba_bot b in Bots){
			b.SendMessage("UserHasRemovedBot",bot,SendMessageOptions.DontRequireReceiver);
		}
	}
	
	public void NotifyAllBotsOfRemoval(Umba_Projectile spell){
		foreach(Umba_Projectile s in Spells){
			s.SendMessage("SpellDestroyed",spell,SendMessageOptions.DontRequireReceiver);
		}
	}
	
	public void RemoveBot(){
		if(Bots.Count != 0){
			Umba_bot rbot = Bots[Bots.Count-1];
			if(rbot.Equals(currentPlayer)){
				LeaveBot();
			}
			NotifyAllBotsOfRemoval(rbot);
			Bots.Remove(rbot);
			Destroy(rbot.gameObject);
		}
	}
	
	public void RemoveBot(Umba_bot bot){}
	
	public void LeaveBot(){
		currentPlayer.Exorcise();
		currentPlayer = null;
		targeting = false;
		spellLocation = null;
		Camera.main.GetComponent<PlayerCamera>().player = null;
		Camera.main.GetComponent<PlayerCamera>().ResetCamera();
	}
	
		public bool isPathObstructed(Vector2 A, Vector2 B) {
		foreach(Vector2 obs in Map.Obstacles){
			if(CircleLineIntersect(A,B,obs,2.5f)){
				return true;
			}
		}
		return false;
	}
	
	public bool CircleLineIntersect(Vector2 A, Vector2 B, Vector2 C, float r){
		Vector2 d = B - A;
		Vector2 f = A - C;
		float a = Vector2.Dot(d,d);
		float b = 2 * Vector2.Dot(f,d);
		float c = Vector2.Dot(f,f) - r*r;
		
		float discriminant = b*b-4*a*c;
		if( discriminant < 0 )
		{
			return false;
		}
		else
		{	
		  discriminant = Mathf.Sqrt(discriminant);
		  float t1 = (-b - discriminant)/(2*a);
		  float t2 = (-b + discriminant)/(2*a);
		
		  if( t1 >= 0 && t1 <= 1 )
		  {
		    return true ;
		  }
		  if( t2 >= 0 && t2 <= 1 )
		  {
		    return true ;
		  }
		
		  return false ;
		}
	}
	
	public bool inViewRange(Vector2 first, Vector2 second, float range){
		return Vector2.Distance(first, second) <= range;
	}
	
	public void TogglePause(){Paused = !Paused;}
	
	void OnGUI(){
		if(currentPlayer == null) return;
		GUI.TextField(new Rect(Screen.width/2 - 120, Screen.height - 100, 100, 20), "Fireball"+getSpellCD(Type.type_fireball), 25);
		GUI.TextField(new Rect(Screen.width/2 + 20, Screen.height - 100, 100, 20), "Meteor"+getSpellCD(Type.type_meteor), 25);
	}
	
	private string getSpellCD(Type type){
		if(currentPlayer == null) return "";
		Umba_Spell spell = currentPlayer.SpellSys.GetSpellFromInventory(type);
		if(spell.isReadyForNextShot())
			return " ready";
		else
			return ": "+System.String.Format("{0:F2}",spell.cdTime());

	}
	
	void OnDrawGizmos() {    
		
    	if(Map == null) return;
		
		Gizmos.color = Color.white;
		if(Map.NavGraph == null || Map.NavGraph.Edges.Count == 0) return;
		foreach(NavGraphEdge edge in Map.NavGraph.Edges){
			Vector2 pos1 = Map.NavGraph.FindByIndex(edge.From).Position;
			Vector2 pos2 = Map.NavGraph.FindByIndex(edge.To).Position;
			Gizmos.DrawLine(new Vector3(pos1.x,0,pos1.y),new Vector3(pos2.x,0.2f,pos2.y));
		}
		
		Gizmos.color = Color.red;
		if(Map.NavGraph == null || Map.NavGraph.Nodes.Count == 0) return;
		foreach(NavGraphNode node in Map.NavGraph.Nodes){
			Gizmos.DrawSphere (new Vector3(node.Position.x,0.2f,node.Position.y), 0.2f);
		}
		
		Gizmos.color = Color.green;
		if(Map.BorderPoints.Count == 0) return;
		foreach(Vector2 b in Map.BorderPoints){
			Gizmos.DrawSphere(new Vector3(b.x,0.2f,b.y), 0.2f);
		}
		Gizmos.color = Color.yellow;
		if(Map.SpawnPoints.Count == 0) return;
		foreach(Vector2 b in Map.SpawnPoints){
			Gizmos.DrawSphere(new Vector3(b.x,0.2f,b.y), 0.2f);
		}
		
	}
	
	void OnDisable(){
		Debug.Log("Game Over");
	}
	
	void getPlayerInput(){
		
		if(currentPlayer != null){
			if(Input.GetButton("Fire2") && lastClickTime < Time.time - 0.3f){
				Ray ray = Camera.main.ScreenPointToRay (Input.mousePosition);
	            RaycastHit hit;
	            if (Physics.Raycast (ray,out hit)){
					if(Input.GetButton("Shift")){
						currentPlayer.brain.QueueGoal_MoveToPosition(new Vector2(hit.point.x,hit.point.z));
					}else{
						currentPlayer.brain.RemoveAllSubgoals();
						currentPlayer.brain.AddGoal_MoveToPosition(new Vector2(hit.point.x,hit.point.z));
					}
				}
				lastClickTime = Time.time;
			}
			if(Input.GetButtonDown("Spell1")){
				if(spell == Type.type_fireball){
					targeting=!targeting;
				}else{
					spell = Type.type_fireball;
					targeting = true;
					if(spellLocation != null) spellLocation.renderer.material = mat_fireball;
				}
			}else if(Input.GetButtonDown("Spell2")){
				if(spell == Type.type_meteor){
					targeting=!targeting;
				}else{
					spell = Type.type_meteor;
					targeting = true;
					if(spellLocation != null) spellLocation.renderer.material = mat_meteor;
				}
			}else if(Input.GetButtonDown("Leave")){
				LeaveBot();
			}
				
	        if(targeting){
	            Ray ray = Camera.main.ScreenPointToRay (Input.mousePosition);
	            RaycastHit hit;
	            if (Physics.Raycast (ray,out hit)){
					if(spellLocation == null){
						spellLocation = Instantiate( spellTarget, new Vector3(hit.point.x,currentPlayer.transform.position.y,hit.point.z), Quaternion.identity) as GameObject;
						spellLocation.transform.parent = goSpells.transform;
						if(Input.GetButton("Shift")){
							currentPlayer.CastSpell(spellLocation.transform.position,spell);
							targeting=false;
							Destroy(spellLocation);
						}else if(spell == Type.type_fireball){
							spellLocation.renderer.material = mat_fireball;
						}else{
							spellLocation.renderer.material = mat_meteor;
						}
					}else if(hit.transform != spellLocation.transform){
						spellLocation.transform.position = new Vector3(hit.point.x,currentPlayer.transform.position.y,hit.point.z);	
					}
					
					if(Input.GetButtonDown("Fire1")){
						currentPlayer.CastSpell(spellLocation.transform.position,spell);
						targeting=false;
						Destroy(spellLocation);
					}
				}
	        }else if(spellLocation != null){
				Destroy(spellLocation);
			}
		}else{
			if(Input.GetButtonDown("Fire2")){
				Ray ray = Camera.main.ScreenPointToRay (Input.mousePosition);
	            RaycastHit hit;
	            if (Physics.Raycast (ray,out hit)){
					if(hit.transform.gameObject.tag == "Player"){
						currentPlayer = hit.transform.gameObject.GetComponent<Umba_bot>();
						currentPlayer.TakePossession();
						currentPlayer.brain.RemoveAllSubgoals();
						Camera.main.GetComponent<PlayerCamera>().player = currentPlayer.gameObject;
						lastClickTime = Time.time;
					}
				}
			}
		}

		if(Input.GetButtonDown("AddBot")){
			AddBot();
		}else if(Input.GetButtonDown("RemoveBot")){
			removeBot = true;
		}
	}
	
}
