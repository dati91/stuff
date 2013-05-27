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
<title>Kollégiumi Nyilvántartás | Admin</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
      <table class="table table-hover table-condensed">
        <tr>
		<thead>
          <th>Log</th>
          <th>Content</th>
          <th>Date</th>
		</thead>
        </tr>
      <c:forEach var="log" items="${logs}">
        <tr>
		  <tbody>
          <td>${log.id}</td>
          <td>${log.content}</td>
          <td>${log.date}</td>
		  </tbody>
        </tr>
      </c:forEach>
    </table>
</body>

</html>

