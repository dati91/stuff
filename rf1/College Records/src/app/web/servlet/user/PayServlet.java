package app.web.servlet.user;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.Balance;

public class PayServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password)){
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", service.isAdmin(username));
			request.setAttribute("balances", service.getBalancesR(username, false));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/pay.jsp");
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
		
		String[] ids = request.getParameterValues("balance");
		if(ids != null && service.login(username, password)){
			for(String id : ids){
				Balance b = service.getBalance(Integer.parseInt(id));
				b.setPayDate(new Date(System.currentTimeMillis()));
				b.setPaid(true);
				service.updateBalance(b);
			}
		}
		String urlWithSessionID = response.encodeRedirectURL("./pay");
        response.sendRedirect( urlWithSessionID );
	}
}
