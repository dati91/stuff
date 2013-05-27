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

//@WebServlet("/RequestEventServlet")
public class RequestEventServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password)){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/requestevent.jsp");
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
		if(service.login(username, password)){
	        String title = request.getParameter("title");
	    	String content = request.getParameter("content");
	    	String date = request.getParameter("date");
	    	service.addEvent(new Event(title,content,Date.valueOf(date),false));
		}
		String urlWithSessionID = response.encodeRedirectURL("./");
        response.sendRedirect( urlWithSessionID );
	}
}
