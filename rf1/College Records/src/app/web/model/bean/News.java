package app.web.model.bean;

import java.sql.Date;

public class News {
	
	private int id;
	private String title;
	private String content;
	private Date date;
	
	public News(int id, String title, String content, Date date){
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	public News(String title, String content, Date date){
		this.id = -1;
		this.title = title;
		this.content = content;
		this.date = date;
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

	public String toString() {
		return id + "-" + title + "-" + (content.length()>13?content.subSequence(0, 10)+"...":content);
	}
}
