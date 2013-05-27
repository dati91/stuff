package app.web.servlet.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.User;

public class UserServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password) && service.isAdmin(username)){
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("users", service.getUsers(true));
			request.setAttribute("users2", service.getUsers(false));
			request.setAttribute("list", true);
			request.setAttribute("admin", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/user.jsp");
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
		
		String uid = request.getParameter("username");
		User u = service.getUser(uid);
		if(u != null && (username.equals(uid) && (service.login(username, password)) || service.isAdmin(username))){
			request.setAttribute("list", false);
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", service.isAdmin(username));
			request.setAttribute("user", u);
			request.setAttribute("rooms", service.getRoomChange(username) || service.getRoomsCapacity(false,u.getRoom()).isEmpty()?null:service.getRoomsCapacity(false,u.getRoom()));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/user.jsp");
			dispatcher.forward(request, response);
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		}
	}
}
