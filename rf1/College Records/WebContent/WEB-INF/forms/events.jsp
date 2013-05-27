<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<%@include file="../../bootstrap/css/bootstrap.css" %>
</style> 
<style type="text/css">
<%@include file="../../static/main.css" %>
</style>
<title>Kollégiumi Nyilvántartás | Esemény</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
  <div>
	<a class="btn login-link" href="./requestevent">request event</a>
  </div>
  <br>
      <c:forEach var="event" items="${events}">
        <div class="hero-unit">
		    <div>
		      <form name="form${event.id}" method="post" action="./event">
              <input type="hidden" name="id" value="${event.id}">
    	      <a class="main-title" href="javascript: document.form${event.id}.submit();">
			  <h2>${event.title}</h2>
			  </a>
              </form>
		    </div>
		    <p>${event.content}</p>
		    <div class="login-link">${event.date}</div>
		</div>
      </c:forEach>
</body>

</html>

