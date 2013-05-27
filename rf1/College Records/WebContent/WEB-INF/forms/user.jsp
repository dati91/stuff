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
  
  <br>
  <c:choose>
  <c:when test="${list}">
	  <div><h4 class="pull-left">Registered Users</h4></div>
      <table class="table table-hover table-condensed">
      <c:forEach var="person" items="${users}">
        <tr>
          <td>${person.username}</td>
          <td>
          <form name="formview${person.username}" method="post">
            <input type="hidden" name="username" value="${person.username}">
   	        <a class="login-link" href="javascript: document.formview${person.username}.submit();">view</a>
          </form>
          </td>
          <td>
          <form name="formedit${person.username}" method="post" action="./edituser">
            <input type="hidden" name="username" value="${person.username}">
   	        <a class="login-link" href="javascript: document.formedit${person.username}.submit();">edit</a>
          </form>
          </td>
          <td>
          <form name="formremove${person.username}" method="post" action="./removeuser">
            <input type="hidden" name="username" value="${person.username}">
   	        <a class="login-link" href="javascript: document.formremove${person.username}.submit();">remove</a>
          </form>
          </td>
		  <td></td>
        </tr>
      </c:forEach>
    </table>
    
	<div><h4 class="pull-left">Pending Users</h4></div>
    <table class="table table-hover table-condensed">
      <c:forEach var="person2" items="${users2}">
        <tr>
          <td>${person2.username}</td>
          <td>
          <form name="formview${person2.username}" method="post">
            <input type="hidden" name="username" value="${person2.username}">
   	        <a class="login-link" href="javascript: document.formview${person2.username}.submit();">view</a>
          </form>
          </td>
          <td>
          <form name="formedit${person2.username}" method="post" action="./edituser">
            <input type="hidden" name="username" value="${person2.username}">
   	        <a class="login-link" href="javascript: document.formedit${person2.username}.submit();">edit</a>
          </form>
          </td>
          <td>
          <form name="formremove${person2.username}" method="post" action="./removeuser">
            <input type="hidden" name="username" value="${person2.username}">
   	        <a class="login-link" href="javascript: document.formremove${person2.username}.submit();">decline</a>
          </form>
          </td>
          <td>
          <form name="formreg${person2.username}" method="post" action="./registration">
            <input type="hidden" name="username" value="${person2.username}">
   	        <a class="login-link" href="javascript: document.formreg${person2.username}.submit();">accept</a>
          </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    </c:when>
    <c:otherwise>
    <table class="table table-hover table-condensed">
        <tr>
          <th>Username</th>
          <td>${user.username}</td>
        </tr>
        <tr>
          <th>First Name</th>
          <td>${user.firstname}</td>
        </tr>
        <tr>
          <th>Last Name</th>
          <td>${user.lastname}</td>
        </tr>
        <tr>
          <th>Password</th>
          <td>${user.password}</td>
        </tr>
        <tr>
          <th>Email</th>
          <td>${user.email}</td>
        </tr>
        <tr>
          <th>Phone</th>
          <td>${user.phone}</td>
        </tr>
        <tr>
          <th>Zip</th>
          <td>${user.zip}</td>
        </tr>
        <tr>
          <th>City</th>
          <td>${user.city}</td>
        </tr>
        <tr>
          <th>Street</th>
          <td>${user.street}</td>
        </tr>
        <tr>
          <th>Room</th>
          <td>${user.room}</td>
        </tr>
		</table>
		<c:if test="${rooms == null}">
			<label>Room Change: You already requested a room change or no room currently available.</label>
		</c:if>
		<c:if test="${rooms != null}">
			<form name="form" class="form-horizontal row" method="post" action="./requestroomchange">
				<div class="span3 control-group">
					<label class="control-label">Room Change</label>
					<div class="controls">
						<select name="room">
							<c:forEach var="room" items="${rooms}">
							  <option  value="${room.number}">${room.number}</option>
							</c:forEach>
						</select> 
					</div>
				</div>
				<div class="span1 control-group">
					<div class="controls">
						<input class="login-link btn" type="submit" value="Request">
					</div>
				</div>
			</form>
		</c:if>
		<div class="btn-group row">
          <form class="span2" name="formedit${user.username}" method="post" action="./edituser">
            <input type="hidden" name="username" value="${user.username}">
   	        <a class="login-link btn" href="javascript: document.formedit${user.username}.submit();">edit</a>
          </form>

          <form class="span2" name="formbal${user.username}" method="post" action="./balance">
            <input type="hidden" name="username" value="${user.username}">
   	        <a class="login-link btn" href="javascript: document.formbal${user.username}.submit();">balance</a>
          </form>
          
          <form class="span2" name="formpay${user.username}" action="./pay">
   	        <a class="login-link btn" href="javascript: document.formpay${user.username}.submit();">pay</a>
          </form>
		</div>

    </c:otherwise>
    </c:choose>
</body>

</html>

