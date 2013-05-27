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

public class EditBalanceServlet extends HttpServlet{
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
			request.setAttribute("users", service.getUsers(true));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/editbalance.jsp");
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
			String uid = request.getParameter("username");
			int id = Integer.parseInt(request.getParameter("id"));
	        String amount = request.getParameter("amount");
	        String description = request.getParameter("description");
			String deadline = request.getParameter("deadline");
			String paydate = request.getParameter("paydate");
			Balance b = service.getBalance(id);
			Date dl = deadline == null?null:Date.valueOf( deadline );
			Date pd = (paydate == null || paydate.trim().equals(""))?null:Date.valueOf( paydate );
			if(id == -1){
				service.addBalance(new Balance(uid, Integer.parseInt(amount),description,pd != null,dl,pd));
				String urlWithSessionID = response.encodeRedirectURL("./balance");
	            response.sendRedirect( urlWithSessionID);
			}else if(b != null){
				if(request.getParameter("edit") != null){
					service.updateBalance(new Balance(id,uid, Integer.parseInt(amount),description,pd != null,dl,pd));
					String urlWithSessionID = response.encodeRedirectURL("./balance");
		            response.sendRedirect( urlWithSessionID);
				}else{
					request.setAttribute("login", true);
					request.setAttribute("admin", true);
					request.setAttribute("username", username);
					request.setAttribute("uid", b.getUsername());
					request.setAttribute("amount", b.getAmount());
					request.setAttribute("description", b.getDescription());
					request.setAttribute("deadline", b.getDeadline());
					request.setAttribute("paydate", b.getPayDate());
					request.setAttribute("id", b.getId());
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/editbalance.jsp");
					dispatcher.forward(request, response);
				}
			}
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		}
	}
}
