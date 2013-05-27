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
<title>Kollégiumi Nyilvántartás | Szobák</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
  
  <br>
  <c:choose>
  <c:when test="${list}">
  	  <div><h4 class="pull-left">Roomless Users</h4></div>
      <table class="table table-hover table-condensed">
      <tr>
		  <td>${room0.curCapacity}</td>
          <td>
          <form name="formview${room0.number}" method="post">
            <input type="hidden" name="number" value="${room0.number}">
   	        <a class="login-link" href="javascript: document.formview${room0.number}.submit();">view</a>
          </form>
          </td>
        </tr>
      </table>
	  <div><h4 class="pull-left">Full Rooms</h4></div>
      <table class="table table-hover table-condensed">
      <c:forEach var="room" items="${rooms}">
        <tr>
          <td>${room.number}</td>
		  <td>${room.curCapacity}</td>
		  <td>${room.maxCapacity}</td>
          <td>
          <form name="formview${room.number}" method="post">
            <input type="hidden" name="number" value="${room.number}">
   	        <a class="login-link" href="javascript: document.formview${room.number}.submit();">view</a>
          </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    
	<div><h4 class="pull-left">Not Full Rooms</h4></div>
    <table class="table table-hover table-condensed">
      <c:forEach var="room2" items="${rooms2}">
        <tr>
          <td>${room2.number}</td>
		  <td>${room2.curCapacity}</td>
		  <td>${room2.maxCapacity}</td>
          <td>
          <form name="formview${room2.number}" method="post">
            <input type="hidden" name="number" value="${room2.number}">
   	        <a class="login-link" href="javascript: document.formview${room2.number}.submit();">view</a>
          </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    </c:when>
    <c:otherwise>
    <div><h4 class="pull-left">Room ${room}</h4></div>
    <table class="table table-hover table-condensed">
      <c:forEach var="user" items="${users}">
        <tr>
          <td>${user.username}</td>
          <td>
          <form name="formview${user.username}" method="post" action="./user">
            <input type="hidden" name="username" value="${user.username}">
   	        <a class="login-link" href="javascript: document.formview${user.username}.submit();">view</a>
          </form>
          </td>
        </tr>
      </c:forEach>
	</table>

    </c:otherwise>
    </c:choose>
</body>

</html>

