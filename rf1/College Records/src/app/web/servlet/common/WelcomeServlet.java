package app.web.servlet.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;

//@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(!service.login(username, password)){
            String urlWithSessionID = response.encodeRedirectURL("./login");
            response.sendRedirect( urlWithSessionID );
		}else{
			if(service.isAdmin(username)){
				request.setAttribute("username", "admin "+username);
			}else{
				request.setAttribute("username", username);
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/welcome.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request,response);
	}
}
