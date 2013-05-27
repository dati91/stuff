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
<title>Kollégiumi Nyilvántartás | Főoldal</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>  
      <c:forEach var="post" items="${news}">
        <div class="hero-unit">
		    <div>
		      <form name="form${post.id}" method="post" action="./news">
              <input type="hidden" name="id" value="${post.id}">
    	      <a class="main-title" href="javascript: document.form${post.id}.submit();">
			  <h2>${post.title}</h2>
			  </a>
              </form>
		    </div>
		    <p>${post.content}</p>
		    <div class="login-link">${post.date}</div>
		</div>
      </c:forEach>
</body>

</html>

