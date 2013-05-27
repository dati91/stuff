package app.web.servlet.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.User;

public class BalanceServlet extends HttpServlet{
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
			request.setAttribute("balances", service.getBalancesR());
			request.setAttribute("list", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/balance.jsp");
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
		
		String uid = request.getParameter("username");
		String bid = request.getParameter("id");
		User u = service.getUser(uid);
		if(u != null && (username.equals(uid) && (service.login(username, password)) || service.isAdmin(username))){
			if(bid != null && service.getBalance(Integer.parseInt(bid)) != null){
				request.setAttribute("list", false);
				request.setAttribute("balance", service.getBalance(Integer.parseInt(bid)));
			}else{
				request.setAttribute("list", true);
				request.setAttribute("balances", service.getBalancesR(uid));
			}
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", service.isAdmin(username));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/balance.jsp");
			dispatcher.forward(request, response);
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
	        response.sendRedirect( urlWithSessionID );
		}
	}
}
