package app.web.servlet.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;

public class MainServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		request.setAttribute("username", username);
		request.setAttribute("login", service.login(username, password));
		request.setAttribute("admin", service.isAdmin(username));
		request.setAttribute("news", service.escapeNews(service.getAllNewsR()));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/front.jsp");
		dispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request,response);
	}
}
