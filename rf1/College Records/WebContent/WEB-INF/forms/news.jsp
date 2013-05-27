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
<title>Kollégiumi Nyilvántartás | Hírek</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
    <c:choose>
  	<c:when test="${list}">
	<div>
	<a class="btn login-link" href="./editnews">add news</a>
	</div>
	<br>
  	<table class="table table-hover table-condensed">
	<tr>
	  <thead>
	  <th>Id</th>
	  <th>Title</th>
	  <th></th>
	  <th></th>
	  <th></th>
	  </thead>
      </tr>
      <c:forEach var="n" items="${news}">
        <tr>
		  <tbody>
          <td>${n.id}</td>
          <td>${n.title}</td>
          <td>
          <form name="formview${n.id}" method="post">
            <input type="hidden" name="id" value="${n.id}">
    	    <a class="login-link" href="javascript: document.formview${n.id}.submit();">view</a>
          </form>
          </td>
          <td>
          <form name="formedit${n.id}" method="post" action="./editnews">
            <input type="hidden" name="id" value="${n.id}">
    	    <a class="login-link" href="javascript: document.formedit${n.id}.submit();">edit</a>
          </form>
          </td>
          <td>
          <form name="formremove${n.id}" method="post" action="./removenews">
            <input type="hidden" name="id" value="${n.id}">
    	    <a class="login-link" href="javascript: document.formremove${n.id}.submit();">remove</a>
          </form>
          </td>
		</tbody>
        </tr>
      </c:forEach>
    </table>
    </c:when>
    <c:otherwise>
	<div class="hero-unit">
	  <h2 class="btn-link">${news.title}</h2>
	  <p>${news.content}</p>
      <div class="login-link">${news.date}</div>
	</div>
    </c:otherwise>
    </c:choose>

</body>
</html>