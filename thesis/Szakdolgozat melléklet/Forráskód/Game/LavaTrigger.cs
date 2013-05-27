using UnityEngine;
using System.Collections;
public class LavaTrigger : MonoBehaviour{
	void OnTriggerEnter(Collider collision) {
		if(collision.tag == "Player"){
			collision.GetComponent<Umba_bot>().OnLava = false;
		}
	}
	
	void OnTriggerExit(Collider collision){
		if(collision.tag =="Player"){
			collision.GetComponent<Umba_bot>().OnLava = true;
		}
	}
}
