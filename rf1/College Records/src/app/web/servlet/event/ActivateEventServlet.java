package app.web.servlet.event;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.Event;

public class ActivateEventServlet extends HttpServlet{
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
			String event = request.getParameter("id");
			Event e = service.getEvent(Integer.parseInt(event));
			if(e != null ){
				e.setActive(true);
				service.updateEvent(e);
			}
			String urlWithSessionID = response.encodeRedirectURL("./event");
            response.sendRedirect( urlWithSessionID );
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
	        response.sendRedirect( urlWithSessionID );
		}
	}
}
