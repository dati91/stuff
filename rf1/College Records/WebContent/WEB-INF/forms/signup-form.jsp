<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Kollégiumi Nyilvántartás | Jelentkezés</title>
    <style type="text/css">
      .label {text-align: right}
      .error {color: red}
    </style>
	<style type="text/css">
    <%@include file="../../bootstrap/css/bootstrap.css" %>
    </style> 
	<style type="text/css">
    <%@include file="../../static/main.css" %>
    </style>   
    <script type="text/javascript">
    <%@include file="validation.js" %>
    </script> 

</head>
<body>
	<div class="well">
    <h2>Signup</h2>
    <form class="form-horizontal" name="form" method="post" onSubmit="return signupValidate()">
      <div class="alert-error">
        	${error}
      </div>
      <div class="control-group">
				<label class="control-label">Username</label>
				<div class="controls">
					<input type="text" name="username" value="${username}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Password</label>
				<div class="controls">
					<input type="password" name="password" value="">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">Verify Password</label>
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
				<div class="controls">
					<input class="btn" type="submit" name="submit" value="Signup" />
				</div>
			</div>
    </form>
  <a href="./">main</a>
  </div>
  </body>
</html>