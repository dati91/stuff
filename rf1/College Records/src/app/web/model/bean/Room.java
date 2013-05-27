package app.web.model.bean;

public class Room {

	private int number;
	private int maxCapacity;
	private int curCapacity;
	
	public Room(int number,int maxCapacity,int curCapacity){
		this.number = number;
		this.maxCapacity = maxCapacity;
		this.curCapacity = curCapacity;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getCurCapacity() {
		return curCapacity;
	}

	public void setCurCapacity(int curCapacity) {
		this.curCapacity = curCapacity;
	}
}
