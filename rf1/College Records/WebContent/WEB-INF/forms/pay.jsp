<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>Kollégiumi Nyilvántartás | Fizetés</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
  	  <br>
  	  <form name="form" method="post">
      <table class="table table-hover table-condensed">
      <tr>
	      <thead>
	      <th></th>
          <th>id</th>
          <th>username</th>
          <th>amount</th>
          <th>description</th>
          <th>deadline</th>
		  </thead>
      </tr>
      <c:forEach var="balance" items="${balances}">
        <tr>
		  <tbody>
		  <td><input type=checkbox name="balance" value="${balance.id}"></td>
          <td>${balance.id}</td>
          <td>${balance.username}</td>
          <td>${balance.amount}</td>
          <td>${balance.description}</td>
          <td>${balance.deadline}</td>
		  </tbody>
        </tr>
      </c:forEach>
    </table>
    <input class="btn pull-right" type="submit" name="submit" value="Pay" />
    </form>
</body>

</html>

