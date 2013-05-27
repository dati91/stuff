package app.web.model.bean;

import java.sql.Date;

public class Balance {
	private int id;
	private String username;
	private int amount;
	private String description;
	private boolean paid;
	private Date deadline;
	private Date payDate;
	
	
	public Balance(int id, String username, int amount, String description,
			boolean paid, Date deadline, Date payDate) {
		this.id = id;
		this.username = username;
		this.amount = amount;
		this.description = description;
		this.paid = paid;
		this.deadline = deadline;
		this.payDate = payDate;
	}
	
	public Balance(String username, int amount, String description,
			boolean paid, Date deadline, Date payDate) {
		this.id = -1;
		this.username = username;
		this.amount = amount;
		this.description = description;
		this.paid = paid;
		this.deadline = deadline;
		this.payDate = payDate;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	
}
