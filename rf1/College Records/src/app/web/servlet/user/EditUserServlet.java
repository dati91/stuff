package app.web.servlet.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.User;

public class EditUserServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		//Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		/*String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password) && service.isAdmin(username)){
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/edituser.jsp");
			dispatcher.forward(request, response);
		}else {*/
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		//}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		String uid = request.getParameter("username");
		User u = service.getUser(uid);
		if(u != null && (username.equals(uid) && (service.login(username, password)) || service.isAdmin(username))){
			request.setAttribute("username", username);
			request.setAttribute("login", true);
			request.setAttribute("admin", service.isAdmin(username));
			
			String edit = request.getParameter("edit");
			if(edit == null){
				request.setAttribute("uid", u.getUsername());
				request.setAttribute("firstname", u.getFirstname());
				request.setAttribute("lastname", u.getLastname());
				request.setAttribute("email", u.getEmail());
				request.setAttribute("phone", u.getPhone());
				request.setAttribute("zip", u.getZip());
				request.setAttribute("city", u.getCity());
				request.setAttribute("street", u.getStreet());
				request.setAttribute("room", u.getRoom());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/edituser.jsp");
				dispatcher.forward(request, response);
			}else{
		    	String firstname = request.getParameter("firstname");
		    	String lastname = request.getParameter("lastname");
		        password = request.getParameter("password");
		        String email = request.getParameter("email");
		        String phone = request.getParameter("phone");
		    	String zip = request.getParameter("zip");
		    	String city = request.getParameter("city");
		    	String street = request.getParameter("street");
		    	String room = request.getParameter("room");
		    	
				if(!firstname.trim().equals(""))u.setFirstname(firstname);
				if(!lastname.trim().equals("")) u.setLastname(lastname);
				if(!password.trim().equals(""))u.setPassword(password);
				if(!email.trim().equals("")) u.setEmail(email);
				if(!phone.trim().equals(""))u.setPhone(phone);
				if(!zip.trim().equals("")) u.setZip(Integer.parseInt(zip));
				if(!city.trim().equals(""))u.setCity(city);
				if(!street.trim().equals("")) u.setStreet(street);
				if(!room.trim().equals("")) u.setRoom(Integer.parseInt(room));
				service.updateUser(u);
				if(!service.isAdmin(username) && !password.trim().equals(""))response.addCookie(new Cookie("password", password));
				String urlWithSessionID = response.encodeRedirectURL("./user");
	            response.sendRedirect( urlWithSessionID );
			}
		}else{
			String urlWithSessionID = response.encodeRedirectURL("./");
            response.sendRedirect( urlWithSessionID );
		}
				
	}
}
