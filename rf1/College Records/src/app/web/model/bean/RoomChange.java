package app.web.model.bean;

public class RoomChange {

	private int id;
	private String username;
	private int room;
	
	
	public RoomChange(int id, String username, int room) {
		this.id = id;
		this.username = username;
		this.room = room;
	}
	
	public RoomChange(String username, int room) {
		this.id = -1;
		this.username = username;
		this.room = room;
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
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	
	
}
