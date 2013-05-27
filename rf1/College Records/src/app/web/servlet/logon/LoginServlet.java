package app.web.servlet.logon;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;

public class LoginServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/login-form.jsp");
		dispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
		if(service.login(username, password)){
	        response.addCookie(new Cookie("username", username));
	        response.addCookie(new Cookie("password", password));
            String urlWithSessionID = response.encodeRedirectURL("./welcome");
            response.sendRedirect( urlWithSessionID );
		}else{
	        request.setAttribute("error", "Invalid username or password");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/login-form.jsp");
			dispatcher.forward(request, response);
		}
	}

	
}
