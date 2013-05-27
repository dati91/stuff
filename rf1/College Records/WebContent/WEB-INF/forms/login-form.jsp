<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
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
    <h2>Login</h2>
    <form name="form" class="form-horizontal" method="post" onSubmit="return loginValidate()">
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
      <div class="alert-error">
        ${error}
      </div>

      <input class="btn" type="submit" value="Log In">
    </form>
  <a href="./">main</a>
  </div>
  </body>

</html>
