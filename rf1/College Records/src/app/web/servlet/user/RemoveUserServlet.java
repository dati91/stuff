package app.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.User;

public class RemoveUserServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String urlWithSessionID = response.encodeRedirectURL("./");
        response.sendRedirect( urlWithSessionID );
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password) && service.isAdmin(username)){
			String uid = request.getParameter("username");
			User u = service.getUser(uid);
			if(u != null && !service.isAdmin(uid)){
				service.removeUser(uid);
			}else if(u != null && !username.equals(uid)){
				service.demoteAdmin(uid);
				service.removeUser(uid);
			}
			String urlWithSessionID = response.encodeRedirectURL("./user");
            response.sendRedirect( urlWithSessionID );
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
	        response.sendRedirect( urlWithSessionID );
		}
	}
}
