package app.web.servlet.news;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.News;

public class EditNewsServlet extends HttpServlet{
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/editnews.jsp");
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
			assert title != null;
	        String content = request.getParameter("content");
	        assert content != null;
			int id = Integer.parseInt(request.getParameter("id"));
			boolean edit = Boolean.parseBoolean(request.getParameter("edit"));
			News n = service.getNews(id);
			if(id == -1){
				service.addNews(new News(title, content,new Date(System.currentTimeMillis())));
				String urlWithSessionID = response.encodeRedirectURL("./");
	            response.sendRedirect( urlWithSessionID);
			}else if(n != null){
				if(edit == true){
					service.updateNews(new News(id,title,content,new Date(System.currentTimeMillis())));
					String urlWithSessionID = response.encodeRedirectURL("./");
		            response.sendRedirect( urlWithSessionID);
				}else{
					request.setAttribute("username", username);
					request.setAttribute("login", true);
					request.setAttribute("admin", true);
					request.setAttribute("title", service.escape(n.getTitle()));
					request.setAttribute("content", service.escape(n.getContent()));
					request.setAttribute("id", n.getId());
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/editnews.jsp");
					dispatcher.forward(request, response);
				}
			}
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
			response.sendRedirect( urlWithSessionID );
		}
	}
}
