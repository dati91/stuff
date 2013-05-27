package app.web.model.bean;

import java.sql.Date;

public class Comment {

	private int id;
	private String username;
	private int eventid;
	private String text;
	private Date date;
	
	
	public Comment(int id, String username, int eventid, String text, Date date) {
		this.id = id;
		this.username = username;
		this.eventid = eventid;
		this.text = text;
		this.date = date;
	}
	
	public Comment(String username, int eventid, String text, Date date) {
		this.id = -1;
		this.username = username;
		this.eventid = eventid;
		this.text = text;
		this.date = date;
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
	public void setUser(String username) {
		this.username = username;
	}
	public int getEventid() {
		return eventid;
	}
	public void setEvent(int eventid) {
		this.eventid = eventid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
