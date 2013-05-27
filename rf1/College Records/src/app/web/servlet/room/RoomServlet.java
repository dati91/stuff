package app.web.servlet.room;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.Room;

public class RoomServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password) && service.isAdmin(username)){
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("rooms", service.getRoomsCapacity(true));
			request.setAttribute("rooms2", service.getRoomsCapacity(false));
			request.setAttribute("room0", service.getRoom(0));
			request.setAttribute("list", true);
			request.setAttribute("admin", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/room.jsp");
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
		
		String number = request.getParameter("number");
		assert number != null;
		Room r = service.getRoom(Integer.parseInt(number));
		if(r != null && (service.login(username, password) && service.isAdmin(username))){
			request.setAttribute("list", false);
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", service.isAdmin(username));
			request.setAttribute("room", number);
			request.setAttribute("users", service.getUsersInRoom(Integer.parseInt(number)));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/room.jsp");
			dispatcher.forward(request, response);
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		}
	}
}
