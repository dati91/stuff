package app.web.servlet.event;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.Attend;
import app.web.model.bean.Event;

public class AttendEventServlet extends HttpServlet{
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
		if(service.login(username, password)){
			int eid = Integer.parseInt(request.getParameter("event"));
			Event e = service.getEvent(eid);
			if(e != null){
				service.addAttend(new Attend(username,eid));
				request.setAttribute("username", username);
				request.setAttribute("login", service.login(username, password));
				request.setAttribute("admin", service.isAdmin(username));
				request.setAttribute("event", service.escapeEvent(e));
				request.setAttribute("comments", service.escapeComments(service.getComments(e)));
				request.setAttribute("attends", service.getAttends(e));
				request.setAttribute("attend", service.getAttend(e, username));
				request.setAttribute("list", false);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/event.jsp");
				dispatcher.forward(request, response);
				
			}else{
				String urlWithSessionID = response.encodeRedirectURL("./");
		        response.sendRedirect( urlWithSessionID );
			}
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
	        response.sendRedirect( urlWithSessionID );
		}
	}
}
