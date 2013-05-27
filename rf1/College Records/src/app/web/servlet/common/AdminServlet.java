package app.web.servlet.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;

public class AdminServlet extends HttpServlet{
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
				request.setAttribute("logs", service.getLogsR());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/admin.jsp");
				dispatcher.forward(request, response);
			}else {
				String urlWithSessionID = response.encodeRedirectURL("./");
	            response.sendRedirect( urlWithSessionID );
			}
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			doGet(request,response);
		}
}
