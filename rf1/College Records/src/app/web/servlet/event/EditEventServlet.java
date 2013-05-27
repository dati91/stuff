package app.web.servlet.event;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.Event;

//@WebServlet("/EditEventServlet")
public class EditEventServlet extends HttpServlet{
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
			request.setAttribute("id", -1);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/editevent.jsp");
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
		if(service.login(username, password) && service.isAdmin(username)){
			String title = request.getParameter("title");
	        String content = request.getParameter("content");
	        String date = request.getParameter("date");
			int id = Integer.parseInt(request.getParameter("id"));
			boolean edit = Boolean.parseBoolean(request.getParameter("edit"));
			Event e = service.getEvent(id);
			if(id == -1){
				service.addEvent(new Event(title, content,Date.valueOf(date),true));
				String urlWithSessionID = response.encodeRedirectURL("./event");
	            response.sendRedirect( urlWithSessionID);
			}else if(e != null){
				if(edit == true){
					service.updateEvent(new Event(id,title,content,Date.valueOf(date),e.isActive()));
					String urlWithSessionID = response.encodeRedirectURL("./event");
		            response.sendRedirect( urlWithSessionID);
				}else{
					request.setAttribute("username", username);
					request.setAttribute("login", true);
					request.setAttribute("admin", true);
					request.setAttribute("title", service.escape(e.getTitle()));
					request.setAttribute("content", service.escape(e.getContent()));
					request.setAttribute("date", e.getDate());
					request.setAttribute("id", e.getId());
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/editevent.jsp");
					dispatcher.forward(request, response);
				}
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
