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
    <script type="text/javascript">
    <%@include file="validation.js" %>
    </script> 
<title>Kollégiumi Nyilvántartás | Felhasználók</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
  
  <br><br>
	<div class="well">
		<form name="form" class="form-horizontal" method="post" onSubmit="return editUserValidate()">
			<div class="control-group">
				<label class="control-label">Username</label>
				<div class="controls">
					<label>${uid}</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">New Password</label>
				<div class="controls">
					<input type="password" name="password" value="">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">Verify New Password</label>
				<div class="controls">
					<input type="password" name="verify" value="">
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label">First name</label>
				<div class="controls">
					<input type="text" name="firstname" value="${firstname}">
				</div>
			</div>
				
			<div class="control-group">
				<label class="control-label">Last name</label>
				<div class="controls">
					<input type="text" name="lastname" value="${lastname}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Email</label>
				<div class="controls">
					<input type="text" name="email" value="${email}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Phone</label>
				<div class="controls">
					<input type="text" name="phone" value="${phone}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Zip Code</label>
				<div class="controls">
					<input type="text" name="zip" value="${zip}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">City</label>
				<div class="controls">
					<input type="text" name="city" value="${city}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Street</label>
				<div class="controls">
					<input type="text" name="street" value="${street}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Room number</label>
				<div class="controls">
					<input type="text" name="room" value="${room}">
				</div>
			</div>

			<div class="control-group">
				<input type="hidden" name="edit" value="true">
				<input type="hidden" name="username" value="${uid}">
				<div class="controls">
					<input class="btn" type="submit" value="Save">
				</div>
			</div>
        </form>
  </div>
</body>

</html>

