package app.web.servlet.event;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.Event;

//@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet{
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
			request.setAttribute("events", service.escapeEvents(service.getEventsR()));
			request.setAttribute("list", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/event.jsp");
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
		request.setAttribute("username", username);
		request.setAttribute("login", service.login(username, password));
		request.setAttribute("admin", service.isAdmin(username));
		if(service.login(username, password)){
			String id = request.getParameter("id");
			if(id == null){
				String urlWithSessionID = response.encodeRedirectURL("./");
	            response.sendRedirect( urlWithSessionID );
			}else{
				Event e = service.getEvent(Integer.parseInt(id));
				if(e == null){
					String urlWithSessionID = response.encodeRedirectURL("./");
		            response.sendRedirect( urlWithSessionID );
				}else{
					request.setAttribute("event", service.escapeEvent(e));
					request.setAttribute("comments", service.escapeComments(service.getComments(e)));
					request.setAttribute("attends", service.getAttends(e));
					request.setAttribute("attend", service.getAttend(e, username));
					request.setAttribute("list", false);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/event.jsp");
					dispatcher.forward(request, response);
				}
			}
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
	        response.sendRedirect( urlWithSessionID );
		}
	}
}
