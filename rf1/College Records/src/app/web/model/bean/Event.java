package app.web.model.bean;

import java.sql.Date;

public class Event {

	private int id;
	private String title;
	private String content;
	private Date date;
	private boolean active;
	
	public Event(int id, String title, String content, Date date,boolean active) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		this.active = active;
	}
	
	public Event(String title, String content, Date date,boolean active) {
		this.id = -1;
		this.title = title;
		this.content = content;
		this.date = date;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
