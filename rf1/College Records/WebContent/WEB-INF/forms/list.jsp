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
<title>Kollégiumi Nyilvántartás | Felhasználók</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
      <table class="table table-hover table-condensed">
        <tr>
		<thead>
          <th>Username</th>
          <th>Firstname</th>
          <th>Lastname</th>
          <c:if test="${admin}">
          <th>Password</th>
          </c:if>
          <c:if test="${login}">
          <th>Email</th>
          <th>Phone</th>
          <th>Zip</th>
          <th>City</th>
          <th>Street</th>
          </c:if>
          <th>Room</th>
		  <th></th>
		</thead>
        </tr>
      <c:forEach var="person" items="${users}">
        <tr>
		  <tbody>
          <td>${person.username}</td>
          <td>${person.firstname}</td>
          <td>${person.lastname}</td>
          <c:if test="${admin}">
          <td>${person.password}</td>
          </c:if>
          <c:if test="${login}">
          <td>${person.email}</td>
          <td>${person.phone}</td>
          <td>${person.zip}</td>
          <td>${person.city}</td>
          <td>${person.street}</td>
          </c:if>
          <td>${person.room}</td>
		  <td>
		  <!--  ezt igy elcsusztat egy sort lehet valamilyen szinezessel jobb lenne -->
          <c:if test="${!person.active}">
          Not registered user
          </c:if>
		  </td>
		  </tbody>
        </tr>
      </c:forEach>
    </table>
</body>

</html>

