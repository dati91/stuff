package app.web.servlet.room;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.RoomChange;
import app.web.model.bean.User;

public class RoomChangeServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password) && service.isAdmin(username)){
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", true);
			request.setAttribute("roomchanges", service.getRoomChanges());
			request.setAttribute("list", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/roomchange.jsp");
			dispatcher.forward(request, response);
		}else {
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		
		String id = request.getParameter("id");
		RoomChange rc = service.getRoomChange(Integer.parseInt(id));
		if(rc != null && (service.login(username, password) && service.isAdmin(username))){
			String type = request.getParameter("type");
			if(type == null){
			request.setAttribute("list", false);
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", service.isAdmin(username));
			request.setAttribute("rc", rc);
			request.setAttribute("users", service.getUsersInRoom(rc.getRoom()));
			request.setAttribute("emptyspace", service.getRoomCapacity(rc.getRoom()));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/roomchange.jsp");
			dispatcher.forward(request, response);
			}else{
				User user = service.getUser(rc.getUsername());
				if(type.equals("move")){
					user.setRoom(rc.getRoom());
					service.updateUser(user);
					service.removeRoomChange(rc.getId());
				}else if(type.equals("swap")){
					User user2 = service.getUser(request.getParameter("with"));
					if(user2 != null){
						user2.setRoom(user.getRoom());
						user.setRoom(rc.getRoom());
						service.updateUser(user);
						service.updateUser(user2);
						service.removeRoomChange(rc.getId());
					}
				}
				String urlWithSessionID = response.encodeRedirectURL("./roomchange");
	            response.sendRedirect( urlWithSessionID );
			}
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		}
	}
}
