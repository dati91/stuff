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
<title>Kollégiumi Nyilvántartás | Egyenleg</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
  
  <c:choose>
  <c:when test="${list}">
      <c:if test="${admin}">
      <div>
 	    <a class="btn login-link" href="./editbalance">add balance</a>
 	  </div>
	  <br>
      </c:if>
      <table class="table table-hover table-condensed">
      <tr>
	      <thead>
          <th>id</th>
          <th>username</th>
          <th>amount</th>
          <th>description</th>
          <th>deadline</th>
          <th>payDate</th>
		  <th></th>
		  <c:if test="${admin}">
		  <th></th>
		  <th></th>
		  </c:if>
		  </thead>
      </tr>
      <c:forEach var="balance" items="${balances}">
        <tr>
		  <tbody>
          <td>${balance.id}</td>
          <td>${balance.username}</td>
          <td>${balance.amount}</td>
          <td>${balance.description}</td>
          <td>${balance.deadline}</td>
          <td>${balance.payDate}</td>
          <td>
          <form name="formview${balance.id}" method="post">
            <input type="hidden" name="id" value="${balance.id}">
            <input type="hidden" name="username" value="${balance.username}">
   	        <a class="login-link" href="javascript: document.formview${balance.id}.submit();">view</a>
          </form>
          </td>
          <c:if test="${admin}">
          <td>
          <form name="formedit${balance.id}" method="post" action="./editbalance">
            <input type="hidden" name="id" value="${balance.id}">
            <input type="hidden" name="username" value="${balance.username}">
   	        <a class="login-link" href="javascript: document.formedit${balance.id}.submit();">edit</a>
          </form>
          </td>
          <td>
          <form name="formremove${balance.id}" method="post" action="./removebalance">
            <input type="hidden" name="id" value="${balance.id}">
            <input type="hidden" name="username" value="${balance.username}">
   	        <a class="login-link" href="javascript: document.formremove${balance.id}.submit();">remove</a>
          </form>
          </td>
          </c:if>
		  </tbody>
        </tr>
      </c:forEach>
    </table>
    </c:when>
    <c:otherwise>
    <table class="table table-hover table-condensed">
        <tr>
          <th>Id</th>
          <td>${balance.id}</td>
        </tr>
        
        <tr>
          <th>Username</th>
          <td>${balance.username}</td>
        </tr>

        <tr>
          <th>Amount</th>
          <td>${balance.amount}</td>
        </tr>
        
        <tr>
          <th>Description</th>
          <td>${balance.description}</td>
        </tr>
        
        <tr>
          <th>Paid</th>
          <td>${balance.paid}</td>
        </tr>
        
        <tr>
          <th>Deadline</th>
          <td>${balance.deadline}</td>
        </tr>
        
        <tr>
          <th>Paydate</th>
          <td>${balance.payDate}</td>
        </tr>
    </table>
    </c:otherwise>
    </c:choose>
</body>

</html>

