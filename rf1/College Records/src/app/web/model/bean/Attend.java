package app.web.model.bean;

public class Attend {

	private int id;
	private String username;
	private int eventid;
	
	public Attend(int id, String username, int eventid) {
		this.id = id;
		this.username = username;
		this.eventid = eventid;
	}
	
	public Attend(String username, int eventid) {
		this.id = -1;
		this.username = username;
		this.eventid = eventid;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getEventid() {
		return eventid;
	}
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}
}
