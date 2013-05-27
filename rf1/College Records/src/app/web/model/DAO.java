package app.web.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.web.model.bean.Attend;
import app.web.model.bean.Balance;
import app.web.model.bean.Comment;
import app.web.model.bean.Event;
import app.web.model.bean.Log;
import app.web.model.bean.News;
import app.web.model.bean.Room;
import app.web.model.bean.RoomChange;
import app.web.model.bean.User;

public class DAO {
	
	private static List<User> users = new ArrayList<User>();
	private static List<News> news = new ArrayList<News>();
	private static List<Balance> balances = new ArrayList<Balance>();
	private static List<Event> events = new ArrayList<Event>();
	private static List<Comment> comments = new ArrayList<Comment>();
	private static List<Attend> attends = new ArrayList<Attend>();
	private static List<RoomChange> roomchanges = new ArrayList<RoomChange>();
	private static List<Log> logs = new ArrayList<Log>();
	private static List<Room> rooms = new ArrayList<Room>();
	
	private static final String dbfile = System.getenv("rfDB");
	
	//log
	private static final String SQL_getLogs = 
			"select * from Log";
	//user
	private static final String SQL_addUser = 
			"insert into User (username, firstname,lastname, password, email, phone, zip, city, street, room, active) values (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_getUsers = 
			"select * from User";
	private static final String SQL_removeUser = 
			"delete from User WHERE username = ?";
	private static final String SQL_updateUser = 
			"update User set firstname=?, lastname=?, password=?, email=?, phone=?,zip=?, city=?, street=?, room=?, active=? where username=?";
	//admin
	private static final String SQL_addAdmin = 
			"insert into Admin (username) values(?)";
	private static final String SQL_removeAdmin = 
			"delete from Admin WHERE username = ?";
	private static final String SQL_getAdmins = 
			"select * from Admin";
	//news
	private static final String SQL_addNews = 
			"insert into News (title,content,date) values(?,?,?)";
	private static final String SQL_getAllNews = 
			"select * from News";
	private static final String SQL_removeNews = 
			"delete from News WHERE id = ?";
	private static final String SQL_updateNews = 
			"update News set title=?, content=?, date=? where id=?";
	//balance
	private static final String SQL_addBalance = 
			"insert into Balance (username, amount, description, paid, deadline, payDate) values (?,?,?,?,?,?)";
	private static final String SQL_getBalances = 
			"select * from Balance";
	private static final String SQL_removeBalance = 
			"delete from Balance WHERE id = ?";
	private static final String SQL_updateBalance = 
			"update Balance set username=?, amount=?, description=?, paid=?, deadline=?, payDate=? where id=?";
	//event
	private static final String SQL_addEvent = 
			"insert into Event (title,content,date,active) values (?,?,?,?)";
	private static final String SQL_getEvents = 
			"select * from Event";
	private static final String SQL_removeEvent = 
			"delete from Event WHERE id = ?";
	private static final String SQL_updateEvent = 
			"update Event set title=?,content=?,date=?,active=? where id=?";
	//comment
	private static final String SQL_addComment = 
			"insert into Comment (username,eventid,text,date) values (?,?,?,?)";
	private static final String SQL_getComments = 
			"select * from Comment";
	private static final String SQL_removeComment = 
			"delete from Comment WHERE id = ?";
	private static final String SQL_updateComment = 
			"update Comment set username=?,eventid=?,text=?,date=? where id=?";
	//attend
	private static final String SQL_addAttend = 
			"insert into Attend (username,eventid) values (?,?)";
	private static final String SQL_getAttends = 
			"select * from Attend";
	private static final String SQL_removeAttend = 
			"delete from Attend WHERE id = ?";
	private static final String SQL_updateAttend = 
			"update Attend set username=?,eventid=? where id=?";
	//roomchange
	private static final String SQL_addRoomChange = 
			"insert into RoomChange (username,room) values (?,?)";
	private static final String SQL_getRoomChanges = 
			"select * from RoomChange";
	private static final String SQL_removeRoomChange = 
			"delete from RoomChange WHERE id = ?";
	private static final String SQL_updateRoomChange = 
			"update RoomChange set username=?,room=? where id=?";
	//room
	private static final String SQL_getRooms = 
			"select * from Room";
	private static final String SQL_updateRoom =
			"update Room set maxCapacity=?,curCapacity=? where number=?";

	
	public DAO(){
        try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public List<Log> getLogs(){
		Connection conn = null;
		Statement st = null;
		logs.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getLogs);
			while(rs.next()){
				Log l = new Log(rs.getInt("id"),
						rs.getString("content"),
						rs.getDate("date"));
				logs.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return logs;
	}
	
	public boolean addAdmin(String username){
		
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addAdmin);
			int index = 1;
			pst.setString(index++, username);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;	
	}
	
	public boolean isAdmin(String username){
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getAdmins);
			while(rs.next()){
				if(rs.getString("username").equals(username)) return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean removeAdmin(String username){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeAdmin);
			int index = 1;
			pst.setString(index++, username);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addUser(User u){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addUser);
			int index = 1;
			pst.setString(index++, u.getUsername());
			pst.setString(index++, u.getFirstname());
			pst.setString(index++, u.getLastname());
			pst.setString(index++, u.getPassword());
			pst.setString(index++, u.getEmail());
			pst.setString(index++, u.getPhone());
			pst.setInt(index++, u.getZip());
			pst.setString(index++, u.getCity());
			pst.setString(index++, u.getStreet());
			pst.setInt(index++, u.getRoom());
			pst.setBoolean(index++, u.isActive());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<User> getUsers(){
		Connection conn = null;
		Statement st = null;
		users.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getUsers);
			while(rs.next()){
				User u = new User(rs.getString("username"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("password"),
						rs.getString("email"),
						rs.getString("phone"),
						rs.getInt("zip"),
						rs.getString("city"),
						rs.getString("street"),
						rs.getInt("room"),
						rs.getBoolean("active"));
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	public boolean removeUser(String username){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeUser);
			int index = 1;
			pst.setString(index++, username);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateUser(User user){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateUser);
			int index = 1;
			pst.setString(index++, user.getFirstname());
			pst.setString(index++, user.getLastname());
			pst.setString(index++, user.getPassword());
			pst.setString(index++, user.getEmail());
			pst.setString(index++, user.getPhone());
			pst.setInt(index++, user.getZip());
			pst.setString(index++, user.getCity());
			pst.setString(index++, user.getStreet());
			pst.setInt(index++, user.getRoom());
			pst.setBoolean(index++, user.isActive());
			pst.setString(index++, user.getUsername());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean addBalance(Balance b){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addBalance);
			int index = 1;
			pst.setString(index++, b.getUsername());
			pst.setInt(index++, b.getAmount());
			pst.setString(index++, b.getDescription());
			pst.setBoolean(index++, b.isPaid());
			pst.setDate(index++, b.getDeadline());
			pst.setDate(index++, b.getPayDate());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<Balance> getBalances(){
		Connection conn = null;
		Statement st = null;
		balances.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getBalances);
			while(rs.next()){
				Balance b = new Balance(rs.getInt("id"),
						rs.getString("username"),
						rs.getInt("amount"),
						rs.getString("description"),
						rs.getBoolean("paid"),
						rs.getDate("deadline"),
						rs.getDate("paydate"));
				balances.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return balances;
	}
	
	public boolean removeBalance(int id){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeBalance);
			int index = 1;
			pst.setInt(index++, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateBalance(Balance b){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateBalance);
			int index = 1;
			pst.setString(index++, b.getUsername());
			pst.setInt(index++, b.getAmount());
			pst.setString(index++, b.getDescription());
			pst.setBoolean(index++, b.isPaid());
			pst.setDate(index++, (Date) b.getDeadline());
			pst.setDate(index++, (Date) b.getPayDate());
			pst.setInt(index++, b.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addEvent(Event e){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addEvent);
			int index = 1;
			pst.setString(index++, e.getTitle());
			pst.setString(index++, e.getContent());
			pst.setDate(index++, (Date) e.getDate());
			pst.setBoolean(index++, e.isActive());
			pst.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}
	
	public List<Event> getEvents(){
		Connection conn = null;
		Statement st = null;
		events.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getEvents);
			while(rs.next()){
				Event e = new Event(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getDate("date"),
						rs.getBoolean("active"));
				events.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return events;
	}
	
	public boolean removeEvent(int id){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeEvent);
			int index = 1;
			pst.setInt(index++, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateEvent(Event event){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateEvent);
			int index = 1;
			pst.setString(index++, event.getTitle());
			pst.setString(index++, event.getContent());
			pst.setDate(index++, (Date) event.getDate());
			pst.setBoolean(index++, event.isActive());
			pst.setInt(index++, event.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addNews(News n){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addNews);
			int index = 1;
			pst.setString(index++, n.getTitle());
			pst.setString(index++, n.getContent());
			pst.setDate(index++, (Date) n.getDate());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<News> getAllNews(){
		Connection conn = null;
		Statement st = null;
		news.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getAllNews);
			while(rs.next()){
				News n = new News(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getDate("date"));
				news.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return news;
	}
	
	public boolean removeNews(int id){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeNews);
			int index = 1;
			pst.setInt(index++, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateNews(News n){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateNews);
			int index = 1;
			pst.setString(index++, n.getTitle());
			pst.setString(index++, n.getContent());
			pst.setDate(index++, (Date) n.getDate());
			pst.setInt(index++, n.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addComment(Comment c){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addComment);
			int index = 1;
			pst.setString(index++, c.getUsername());
			pst.setInt(index++, c.getEventid());
			pst.setString(index++, c.getText());
			pst.setDate(index++, (Date) c.getDate());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<Comment> getComments(){
		Connection conn = null;
		Statement st = null;
		comments.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getComments);
			while(rs.next()){
				Comment c = new Comment(rs.getInt("id"),
						rs.getString("username"),
						rs.getInt("eventid"),
						rs.getString("text"),
						rs.getDate("date"));
				comments.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return comments;
	}
	
	public boolean removeComment(int id){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeComment);
			int index = 1;
			pst.setInt(index++, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateComment(Comment c){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateComment);
			int index = 1;
			pst.setString(index++, c.getUsername());
			pst.setInt(index++, c.getEventid());
			pst.setString(index++, c.getText());
			pst.setDate(index++, (Date) c.getDate());
			pst.setInt(index++, c.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addAttend(Attend a){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addAttend);
			int index = 1;
			pst.setString(index++, a.getUsername());
			pst.setInt(index++, a.getEventid());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<Attend> getAttends(){
		Connection conn = null;
		Statement st = null;
		attends.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getAttends);
			while(rs.next()){
				Attend a = new Attend(rs.getInt("id"),
						rs.getString("username"),
						rs.getInt("eventid"));
				attends.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return attends;
	}
	
	public boolean removeAttend(int id){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeAttend);
			int index = 1;
			pst.setInt(index++, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateAttend(Attend a){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateAttend);
			int index = 1;
			pst.setString(index++, a.getUsername());
			pst.setInt(index++, a.getEventid());
			pst.setInt(index++, a.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean addRoomChange(RoomChange rc){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_addRoomChange);
			int index = 1;
			pst.setString(index++, rc.getUsername());
			pst.setInt(index++, rc.getRoom());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<RoomChange> getRoomChanges(){
		Connection conn = null;
		Statement st = null;
		roomchanges.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getRoomChanges);
			while(rs.next()){
				RoomChange rc = new RoomChange(rs.getInt("id"),
						rs.getString("username"),
						rs.getInt("room"));
				roomchanges.add(rc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return roomchanges;
	}
	
	public boolean removeRoomChange(int id){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_removeRoomChange);
			int index = 1;
			pst.setInt(index++, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean updateRoomChange(RoomChange rc){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateRoomChange);
			int index = 1;
			pst.setString(index++, rc.getUsername());
			pst.setInt(index++, rc.getRoom());
			pst.setInt(index++, rc.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<Room> getRooms(){
		Connection conn = null;
		Statement st = null;
		rooms.clear();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQL_getRooms);
			while(rs.next()){
				Room r = new Room(rs.getInt("number"),
						rs.getInt("maxCapacity"),
						rs.getInt("curCapacity"));
				rooms.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return rooms;
	}
	
	public boolean updateRoom(Room r){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbfile);			
			pst = conn.prepareStatement(SQL_updateRoom);
			int index = 1;
			pst.setInt(index++, r.getMaxCapacity());
			pst.setInt(index++, r.getCurCapacity());
			pst.setInt(index++, r.getNumber());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
