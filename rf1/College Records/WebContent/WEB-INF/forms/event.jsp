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
    <c:choose>
  	<c:when test="${list}">
	<div>
	<a class="btn login-link" href="./editevent">add event</a>
	</div>
	<br>
  	<table class="table table-hover table-condensed">
	<tr>
	  <thead>
	  <th>Id</th>
	  <th>Title</th>
	  <th>Date</th>
	  <th></th>
	  <th></th>
	  <th></th>
	  <th></th>
	  </thead>
      </tr>
      <c:forEach var="e" items="${events}">
        <tr>
		  <tbody>
          <td>${e.id}</td>
          <td>${e.title}</td>
          <td>${e.date}</td>
          <td>
          <form name="formview${e.id}" method="post">
            <input type="hidden" name="id" value="${e.id}">
    	    <a class="login-link" href="javascript: document.formview${e.id}.submit();">view</a>
          </form>
          </td>
          <td>
          <form name="formedit${e.id}" method="post" action="./editevent">
            <input type="hidden" name="id" value="${e.id}">
    	    <a class="login-link" href="javascript: document.formedit${e.id}.submit();">edit</a>
          </form>
          </td>
          <td>
          <form name="formremove${e.id}" method="post" action="./removeevent">
            <input type="hidden" name="id" value="${e.id}">
    	    <a class="login-link" href="javascript: document.formremove${e.id}.submit();">remove</a>
          </form>
          <td>
          <c:if test="${!e.active}">
          <form name="formactivate${e.id}" method="post" action="./activateevent">
            <input type="hidden" name="id" value="${e.id}">
    	    <a class="login-link" href="javascript: document.formactivate${e.id}.submit();">activate</a>
          </form>
          </c:if>
          </td>
          </td>
		</tbody>
        </tr>
      </c:forEach>
    </table>
    </c:when>
    <c:otherwise>
    <div class="row">
    <div class="span8">
	<div class="hero-unit">
	  <h2 class="btn-link">${event.title}</h2>
	  <p>${event.content}</p>
      <div class="login-link">${event.date}</div>
	</div>
	<c:forEach var="c" items="${comments}">
	  <div class="well">
	  <p>${c.username}: ${c.text}</p>
      <div class="login-link">${c.date}
      <c:if test="${c.username == username || admin}">
      <form name="formremove${c.id}" method="post" action="./removecomment">
        <input type="hidden" name="id" value="${c.id}">
        <input type="hidden" name="event" value="${event.id}">
	    <a class="login-link" href="javascript: document.formremove${c.id}.submit();"> remove</a>
      </form>
      </c:if>
      </div>
	</div>
	</c:forEach>
	<form class="well" name="form" method="post" action="./comment" onSubmit="return CommentValidate()">
		<p>${username}
		<input type="text" name="text" value="${text}">
		<input type="hidden" name="event" value="${event.id}">
		<input class="btn" type="submit" name="submit" value="Comment" />
		</p>
    </form>
    </div>
    <div class="span2">
    <div class="well">
    <c:choose>
    <c:when test="${attend == null}">
	<form name="formattend${event.id}" method="post" action="./attend">
	  <input type="hidden" name="event" value="${event.id}">
	  <a class="btn login-link" href="javascript: document.formattend${event.id}.submit();">Ott leszek</a>
	</form>
	</c:when>
	<c:otherwise>
	<form name="formremoveattend${attend.id}" method="post" action="./removeattend">
	  <input type="hidden" name="event" value="${attend.eventid}">
	  <input type="hidden" name="id" value="${attend.id}">
	  <a class="btn login-link" href="javascript: document.formremoveattend${attend.id}.submit();">Nem leszek ott</a>
	</form>
	</c:otherwise>
	</c:choose>
	<table>
	<c:forEach var="a" items="${attends}">
	<tr><td>${a.username}</td></tr>
	</c:forEach>
	</table>
    </div>
    </div>
    </div>
    </c:otherwise>
    </c:choose>

</body>
</html>