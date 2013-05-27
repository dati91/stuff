package app.web.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringEscapeUtils;

import app.web.model.DAO;
import app.web.model.bean.Attend;
import app.web.model.bean.Balance;
import app.web.model.bean.Comment;
import app.web.model.bean.Event;
import app.web.model.bean.Log;
import app.web.model.bean.News;
import app.web.model.bean.Room;
import app.web.model.bean.RoomChange;
import app.web.model.bean.User;

public class Logic{
	
	private DAO dao = new DAO();
	   
	public String escape(String str){
		return StringEscapeUtils.escapeHtml4(str);
	}
	
	public List<News> escapeNews(List<News> news){
		for(News n : news){
			n = escapeNews(n);
		}
		return news;
	}
	
	public News escapeNews(News n){
			n.setTitle(escape(n.getTitle()));
			n.setContent(escape(n.getContent()));
		return n;
	}
	
	public List<Event> escapeEvents(List<Event> events){
		for(Event e : events){
			e = escapeEvent(e);
		}
		return events;
	}
	
	public Event escapeEvent(Event e){
			e.setTitle(escape(e.getTitle()));
			e.setContent(escape(e.getContent()));
		return e;
	}
	
	public List<Comment> escapeComments(List<Comment> comments){
		for(Comment c : comments){
			c = escapeComment(c);
		}
		return comments;
	}
	
	public Comment escapeComment(Comment c){
			c.setText(escape(c.getText()));
		return c;
	}
	
	public String unescape(String str){
		return StringEscapeUtils.unescapeHtml4(str);
	}
	
	public List<Log> getLogs(){
		return dao.getLogs();
	}
	
	public List<Log> getLogsR(){
		List<Log> logs =  dao.getLogs();
		Collections.reverse(logs);
		return logs;
	}
	
	public boolean	promoteToAdmin(String username){
		return dao.addAdmin(username);
	}
	
	public boolean isAdmin(String username){
		return dao.isAdmin(username);
	}

	public boolean demoteAdmin(String username){
		return dao.removeAdmin(username);
	}
	
	public boolean addUser(User u) {
		if(check_username(u.getUsername())) return false;
		return dao.addUser(u);
	}
	
	public String getCookieValue(Cookie[] cookies,String cookieName){
		if(cookies == null) return "";
		for(int i=0; i<cookies.length; i++) {
		    if (cookieName.equals(cookies[i].getName()))
		    	return(cookies[i].getValue());
		    }
		return "";
	}

	public boolean login(String username, String password) {
		List<User> users = dao.getUsers();
		for (User u : users) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password) && u.isActive() == true){
				return true;
			}
	    }
		return false;
	}

	public boolean check_username(String username) {
		List<User> users = dao.getUsers();
		for (User u : users) {
			if(u.getUsername().equals(username)){
				System.out.println("Error: "+username+" already in user!");
				return true;
			}
	    }
		return false;
	}
	
	public List<User> getUsers(){
		return dao.getUsers();
	}
	
	public List<User> getUsers(boolean active){
		List<User> users = dao.getUsers();
		List<User> activeusers = new ArrayList<User>();
		for (User u : users) {
			if(u.isActive() == active){
				activeusers.add(u);
			}
	    }
		return  activeusers;
	}
	
	public User getUser(String username){
		List<User> users = dao.getUsers();
		for (User u : users) {
			if(u.getUsername().equals(username)){
				return u;
			}
	    }
		return  null;
	}
	
	public boolean removeUser(String username) {
		return dao.removeUser(username);
	}
	
	public boolean updateUser(User u){
		return dao.updateUser(u);
	}
	
	public List<User> listUsersAdmin(){
		return getUsers();
	}
	
	public List<User> listUsers(){
		List<User> users = dao.getUsers();
		for (User u : users) {
			u.setPassword("");
	    }
		return users;
	}
	
	public List<User> listUsersGuest(){
		List<User> users = dao.getUsers();
		for (User u : users) {
			u.setPassword("");
			u.setEmail("");
			u.setPhone("");
			u.setCity("");
			u.setStreet("");
	    }
		return users;
	}

	public boolean addNews(News n){
		return dao.addNews(n);
	}
	
	public List<News> getAllNews(){
		return dao.getAllNews();
	}
	
	public List<News> getAllNewsR(){
		List<News> news =  dao.getAllNews();
		Collections.reverse(news);
		return news;
	}
	
	public News getNews(int id){
		List<News> news =  dao.getAllNews();
		for (News n : news) {
			if(n.getId() == id){
				return n;
			}
	    }
		return null;
	}
	
	public boolean removeNews(int id){
		return dao.removeNews(id);
	}
	
	public boolean updateNews(News n){
		return dao.updateNews(n);
	}
	
	public boolean addBalance(Balance b){
		return dao.addBalance(b);
	}
	
	public List<Balance> getBalances(){
		return dao.getBalances();
	}
	
	public List<Balance> getBalances(String username){
		List<Balance> balances =  dao.getBalances();
		List<Balance> userbalances =  new ArrayList<Balance>();
		for (Balance b : balances) {
			if(b.getUsername().equals(username)){
				userbalances.add(b);
			}
	    }
		return userbalances;
	}
	
	public List<Balance> getBalancesR(String username){
		List<Balance> balances =  getBalances(username);
		Collections.reverse(balances);
		return balances;
	}
	
	public List<Balance> getBalances(String username, boolean paid){
		List<Balance> balances =  dao.getBalances();
		List<Balance> userbalances =  new ArrayList<Balance>();
		for (Balance b : balances) {
			if(b.getUsername().equals(username) && b.isPaid() == paid){
				userbalances.add(b);
			}
	    }
		return userbalances;
	}
	
	public List<Balance> getBalancesR(String username, boolean paid){
		List<Balance> balances =  getBalances(username,paid);
		Collections.reverse(balances);
		return balances;
	}
	
	public List<Balance> getBalances(boolean paid){
		List<Balance> balances =  dao.getBalances();
		List<Balance> userbalances =  new ArrayList<Balance>();
		for (Balance b : balances) {
			if(b.isPaid() == paid){
				userbalances.add(b);
			}
	    }
		return userbalances;
	}
	
	public List<Balance> getBalancesR(boolean paid){
		List<Balance> balances =  getBalances(paid);
		Collections.reverse(balances);
		return balances;
	}
	
	public List<Balance> getBalancesR(){
		List<Balance> balances =  dao.getBalances();
		Collections.reverse(balances);
		return balances;
	}
	
	public Balance getBalance(int id){
		List<Balance> balances =  dao.getBalances();
		for (Balance b : balances) {
			if(b.getId() == id){
				return b;
			}
	    }
		return null;
	}
	
	public boolean removeBalance(int id){
		return dao.removeBalance(id);
	}
	
	public boolean updateBalance(Balance b){
		return dao.updateBalance(b);
	}
	
	public boolean addEvent(Event e){
		return dao.addEvent(e);
	}
	
	public List<Event> getEvents(){
		return dao.getEvents();
	}
	
	public List<Event> getEventsR(){
		List<Event> events =  dao.getEvents();
		Collections.reverse(events);
		return events;
	}
	
	public Event getEvent(int id){
		List<Event> events =  dao.getEvents();
		for (Event e : events) {
			if(e.getId() == id){
				return e;
			}
	    }
		return null;
	}
	
	public List<Event> getEvents(boolean active){
		List<Event> events =  dao.getEvents();
		List<Event> events2 =  new ArrayList<Event>();
		for (Event e : events) {
			if(e.isActive() == active){
				events2.add(e);
			}
	    }
		return events2;
	}
	
	public List<Event> getEventsR(boolean active){
		List<Event> events =  getEvents(active);
		Collections.reverse(events);
		return events;
	}
	
	public boolean removeEvent(int id){
		return dao.removeEvent(id);
	}
	
	public boolean updateEvent(Event e){
		return dao.updateEvent(e);
	}
	
	public boolean addComment(Comment c){
		return dao.addComment(c);
	}
	
	public List<Comment> getComments(){
		return dao.getComments();
	}
	
	public List<Comment> getComments(Event e){
		List<Comment> comments =  dao.getComments();
		List<Comment> eventcomments =  new ArrayList<Comment>();
		for (Comment c : comments) {
			if(c.getEventid() == e.getId()){
				eventcomments.add(c);
			}
	    }
		return eventcomments;
	}
	
	public Comment getComment(int id){
		List<Comment> comments =  dao.getComments();
		for (Comment c : comments) {
			if(c.getId() == id){
				return c;
			}
	    }
		return null;
	}
	
	public boolean removeComment(int id){
		return dao.removeComment(id);
	}
	
	public boolean updateComment(Comment c){
		return dao.updateComment(c);
	}
	
	public boolean addAttend(Attend a){
		return dao.addAttend(a);
	}
	
	public List<Attend> getAttends(){
		return dao.getAttends();
	}
	
	public List<Attend> getAttends(Event e){
		List<Attend> attends =  dao.getAttends();
		List<Attend> eventattends =  new ArrayList<Attend>();
		for (Attend c : attends) {
			if(c.getEventid() == e.getId()){
				eventattends.add(c);
			}
	    }
		return eventattends;
	}
	
	public Attend getAttend(Event e, String username){
		List<Attend> attends =  getAttends(e);
		for (Attend a : attends) {
			if(a.getUsername().equals(username)){
				return a;
			}
	    }
		return null;
	}
	
	public List<Attend> getAttends(User u){
		List<Attend> attends =  dao.getAttends();
		List<Attend> userattends =  new ArrayList<Attend>();
		for (Attend c : attends) {
			if(c.getUsername() == u.getUsername()){
				userattends.add(c);
			}
	    }
		return userattends;
	}
	
	public Attend getAttend(int id){
		List<Attend> attends =  dao.getAttends();
		for (Attend a : attends) {
			if(a.getId() == id){
				return a;
			}
	    }
		return null;
	}
	
	public boolean removeAttend(int id){
		return dao.removeAttend(id);
	}
	
	public boolean updateAttend(Attend a){
		return dao.updateAttend(a);
	}
	
	public boolean addRoomChange(RoomChange rc){
		return dao.addRoomChange(rc);
	}
	
	public List<RoomChange> getRoomChanges(){
		return dao.getRoomChanges();
	}
	
	public List<RoomChange> getRoomChanges(int room){
		List<RoomChange> roomchanges =  dao.getRoomChanges();
		List<RoomChange> roomnumberroomchages =  new ArrayList<RoomChange>();
		for (RoomChange rc : roomchanges) {
			if(rc.getRoom() == room){
				roomnumberroomchages.add(rc);
			}
	    }
		return roomnumberroomchages;
	}
	
	public List<RoomChange> getRoomChanges(User u){
		List<RoomChange> roomchanges =  dao.getRoomChanges();
		List<RoomChange> userroomchages =  new ArrayList<RoomChange>();
		for (RoomChange rc : roomchanges) {
			if(rc.getUsername().equals(u.getUsername())){
				userroomchages.add(rc);
			}
	    }
		return userroomchages;
	}
	
	public boolean getRoomChange(String username){
		List<RoomChange> roomchanges =  dao.getRoomChanges();
		for (RoomChange rc : roomchanges) {
			if(rc.getUsername().equals(username)){
				return true;
			}
	    }
		return false;
	}
	
	public RoomChange getRoomChange(int id){
		List<RoomChange> roomchanges =  dao.getRoomChanges();
		for (RoomChange rc : roomchanges) {
			if(rc.getId() == id){
				return rc;
			}
	    }
		return null;
	}
	
	public boolean removeRoomChange(int id){
		return dao.removeRoomChange(id);
	}
	
	public boolean updateRoomChange(RoomChange rc){
		return dao.updateRoomChange(rc);
	}
	
	public List<Room> getRooms(){
		return dao.getRooms();
	}
	
	public Room getRoom(int number){
		List<Room> rooms =  dao.getRooms();
		for (Room r : rooms) {
			if(r.getNumber() == number){
				return r;
			}
	    }
		return null;
	}
	
	public List<User> getUsersInRoom(int number){
		Room room = getRoom(number);
		List<User> users = dao.getUsers();
		List<User> ret = new ArrayList<User>();
		for(User u : users){
			if(u.getRoom() == room.getNumber()){
				ret.add(u);
			}
		}
		return ret;
	}
	
	public boolean updateRoom(Room r){
		return dao.updateRoom(r);
	}
	
	public List<Room> getRoomsCapacity(boolean full){
		List<Room> rooms = dao.getRooms();
		List<Room> ret = new ArrayList<Room>();
		for(Room r : rooms){
			if(r.getCurCapacity()<r.getMaxCapacity() != full && r.getNumber() != 0){
				ret.add(r);
			}
		}
		return ret;
	}
	
	public List<Room> getRoomsCapacity(boolean full, int room){
		List<Room> rooms = dao.getRooms();
		List<Room> ret = new ArrayList<Room>();
		for(Room r : rooms){
			if(r.getCurCapacity()<r.getMaxCapacity() != full && r.getNumber() != 0 && r.getNumber() != room){
				ret.add(r);
			}
		}
		return ret;
	}
	
	public boolean getRoomCapacity(int number){
		Room r = getRoom(number);
		return r.getCurCapacity()<r.getMaxCapacity();
	}
	
	public int nextAvailableRoom(){
		List<Room> rooms = getRoomsCapacity(false);
		if(!rooms.isEmpty()) return rooms.get(0).getNumber();
		return 0;
	}
}
