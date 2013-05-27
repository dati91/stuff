package app.web.servlet.logon;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.web.logic.Logic;
import app.web.model.bean.User;

public class SignupServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password)){
            String urlWithSessionID = response.encodeRedirectURL("./welcome");
            response.sendRedirect( urlWithSessionID );
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/signup-form.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Logic service = new Logic();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = service.getCookieValue(request.getCookies(),"username");
		String password = service.getCookieValue(request.getCookies(),"password");
		if(service.login(username, password)){
            String urlWithSessionID = response.encodeRedirectURL("./welcome");
            response.sendRedirect( urlWithSessionID );
		}
		
        username = request.getParameter("username");
        assert username != null;
    	String firstname = request.getParameter("firstname");
    	assert firstname != null;
    	String lastname = request.getParameter("lastname");
    	assert lastname != null;
        password = request.getParameter("password");
        assert password != null;
        String email = request.getParameter("email");
        assert email != null;
        String phone = request.getParameter("phone");
        assert phone != null;
    	String zip = request.getParameter("zip");
    	assert zip != null;
    	String city = request.getParameter("city");
    	assert city != null;
    	String street = request.getParameter("street");
    	assert street != null;

    	if(!service.addUser(new User(username,firstname,lastname,password,email,phone,zip.trim().equals("")?0:Integer.parseInt(zip),city,street,0,false))){
    		request.setAttribute("error", "That username has already been taken.");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/signup-form.jsp");
    		dispatcher.forward(request, response);
        }else{
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/forms/reg.jsp");
    		dispatcher.forward(request, response);
        }
	}
	
}
