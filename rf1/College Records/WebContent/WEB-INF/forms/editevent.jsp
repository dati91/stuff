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
    <script type="text/javascript">
    <%@include file="validation.js" %>
    </script> 
<title>Kollégiumi Nyilvántartás | Esemény</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
  <br>
	<div class="well">
		<form name="form" class="form-horizontal" method="post" onSubmit="return editEventValidate()">
			<div class="control-group">
				<label class="control-label">title</label>
				<div class="controls">
					<input type="text" name="title" value="${title}">
				</div>
			</div>
            
            <div class="control-group">
				<label class="control-label">content</label>
				<div class="controls">
					<textarea name="content">${content}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">date</label>
				<div class="controls">
					<input type="text" name="date" value="${date}">
				</div>
			</div>
			<div class="control-group">
				<input type="hidden" name="edit" value="true">
				<input type="hidden" name="id" value="${id}">
				<div class="controls">
					<input class="btn" type="submit" value="Save">
				</div>
			</div>
        </form>
  </div>
</body>

</html>

