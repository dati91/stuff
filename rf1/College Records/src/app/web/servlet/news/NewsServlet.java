package app.web.servlet.news;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.News;

public class NewsServlet extends HttpServlet{
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
			request.setAttribute("news", service.escapeNews(service.getAllNewsR()));
			request.setAttribute("list", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/news.jsp");
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
		
		String id = request.getParameter("id");
		if(id == null){
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		}else{
			News n = service.getNews(Integer.parseInt(id));
			if(n == null){
				String urlWithSessionID = response.encodeRedirectURL("./");
	            response.sendRedirect( urlWithSessionID );
			}else{
				request.setAttribute("news", service.escapeNews(n));
				request.setAttribute("list", false);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/news.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
