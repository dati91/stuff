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
<title>Kollégiumi Nyilvántartás | Szobacsere</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
    <c:choose>
  	<c:when test="${list}">
	<br>
  	<table class="table table-hover table-condensed">
	<tr>
	  <thead>
	  <th>Id</th>
	  <th>User</th>
	  <th>Room</th>
	  <th></th>
	  <th></th>
	  <th></th>
	  </thead>
      </tr>
      <c:forEach var="rc" items="${roomchanges}">
        <tr>
		  <tbody>
          <td>${rc.id}</td>
          <td>${rc.username}</td>
          <td>${rc.room}</td>
          <td>
          <form name="formview${rc.id}" method="post">
            <input type="hidden" name="id" value="${rc.id}">
    	    <a class="login-link" href="javascript: document.formview${rc.id}.submit();">view</a>
          </form>
          </td>
          <td>
          <td>
          <form name="formremove${rc.id}" method="post" action="./removeroomchange">
            <input type="hidden" name="id" value="${rc.id}">
    	    <a class="login-link" href="javascript: document.formremove${rc.id}.submit();">remove</a>
          </form>
          </td>
		</tbody>
        </tr>
      </c:forEach>
    </table>
    </c:when>
    <c:otherwise>
	  <h2 class="btn-link">Room Change</h2>
	  <table class="table table-condensed">
	  <tr>
	  <td>${rc.username }</td>
	  <c:forEach var="user" items="${users}">
	  <td>${user.username}</td>
	  </c:forEach>
	  <c:if test="${emptyspace}">
	  <td>
	  	Empty Space
	  </td>
	  </c:if>
	  </tr>
	  <tr>
	  <td></td>
	  <c:forEach var="user" items="${users}">
	  <td>
	  <form name="formswapwith${user.username}" method="post">
        <input type="hidden" name="id" value="${rc.id}">
        <input type="hidden" name="type" value="swap">
        <input type="hidden" name="with" value="${user.username}">
	    <a class="login-link" href="javascript: document.formswapwith${user.username}.submit();">swap</a>
      </form>
	  </td>
	  </c:forEach>
	  <c:if test="${emptyspace}">
	  <td>
	  <form name="formmove${rc.id}" method="post">
        <input type="hidden" name="id" value="${rc.id}">
        <input type="hidden" name="type" value="move">
	    <a class="login-link" href="javascript: document.formmove${rc.id}.submit();">move</a>
      </form>
	  </td>
	  </c:if>
	  </tr>
	  </table>
    </c:otherwise>
    </c:choose>

</body>
</html>