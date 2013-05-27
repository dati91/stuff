package app.web.servlet.event;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.Comment;
import app.web.model.bean.Event;

public class RemoveCommentServlet extends HttpServlet{
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
			int id = Integer.parseInt(request.getParameter("id"));
			int eid = Integer.parseInt(request.getParameter("event"));
			Comment c = service.getComment(id);
			if(c != null && (c.getUsername().equals(username) || service.isAdmin(username))){
				service.removeComment(id);
			}
			Event e = service.getEvent(eid);
			if(e != null){
				request.setAttribute("event", service.escapeEvent(e));
				request.setAttribute("comments", service.getComments(e));
				request.setAttribute("attends", service.getAttends(e));
				request.setAttribute("attend", service.getAttend(e, username));
				request.setAttribute("list", false);
				request.setAttribute("username", username);
				request.setAttribute("login", service.login(username, password));
				request.setAttribute("admin", service.isAdmin(username));
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
