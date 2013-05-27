using UnityEngine;
using System.Collections;

public class PlayerCamera : MonoBehaviour {

	public float zoomSpeed = 20.0f;
	public float moveSpeed = 15.0f;
	public float rotateSpeed = 120.0f;
	
	public GameObject player = null;
	
	public float distance = 15.0f;
	public float minDistance = 8.0f;
	public float maxDistance = 25.0f;
	
	public float x = 0.0f;
	public float y = 0.0f;
	
	public float yMinLimit = 20f;
    public float yMaxLimit = 80f;
	
	public Quaternion standard_rot;
	public Vector3 standard_pos;
	
	void Start(){
		standard_rot = transform.rotation;
		standard_pos = transform.position;
		x = standard_rot.x;
		y = standard_rot.y+60;
		if(rigidbody) rigidbody.freezeRotation = true;
		
	}
	
	void LateUpdate () {
		if(player != null){
			
		x -= Input.GetAxis("Horizontal") * rotateSpeed * 0.02f;
        y += Input.GetAxis("Vertical") * rotateSpeed * 0.02f;
 
        y = ClampAngle(y, yMinLimit, yMaxLimit);
 
        Quaternion rotation = Quaternion.Euler(y, x, standard_rot.z);
        Vector3 position = rotation * new Vector3(0.0f, 0.0f, -distance) + player.transform.position;

        transform.rotation = rotation;
        transform.position = position;
			
			
		} else {
			float z = moveSpeed * Input.GetAxis("Vertical") * Time.deltaTime;
			float x = moveSpeed * Input.GetAxis("Horizontal") * Time.deltaTime;

			transform.Translate(x, 0, z, Space.World );			
		}
		
		float scroll = Input.GetAxis("Mouse ScrollWheel");
	    if (scroll != 0.0f){
			float s = zoomSpeed * scroll;
			distance -= s;
			
			if(distance < minDistance || distance > maxDistance)
				distance = Mathf.Clamp(distance,minDistance,maxDistance);
			else
				transform.Translate(0, 0, s);
	    }

		
		
	}
	
	public static float ClampAngle(float angle, float min, float max){
        if (angle < -360F)
            angle += 360F;
        if (angle > 360F)
            angle -= 360F;
        return Mathf.Clamp(angle, min, max);
    }
	
	public void ResetCamera(){
		transform.position = standard_pos;
		transform.rotation = standard_rot;
		x = standard_rot.x;
		y = standard_rot.y+60;
		distance = 15.0f;
	}
}
