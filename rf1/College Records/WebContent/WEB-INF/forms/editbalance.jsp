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
    <script type="text/javascript; charset=UTF-8">
    <%@include file="validation.js" %>
    </script> 
<title>Kollégiumi Nyilvántartás | Egyenleg</title>
</head>
<body>
  <%@include file="status_header.jsp" %>
  <%@include file="menu_header.jsp" %>
  
  <br>
  <div class="well">
		<form name="form" class="form-horizontal" method="post" onSubmit="return editBalanceValidate()">
		    <div class="control-group">
				<label class="control-label">username</label>
				<div class="controls">
					<c:if test="${users == null}">
					<input type="text" name="username" value="${uid}">
					</c:if>
					<c:if test="${users != null}">
					<select name="username">
						<c:forEach var="person" items="${users}">
						  <option  value="${person.username}">${person.username}</option>
						</c:forEach>
					</select> 
					</c:if>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="a">amount</label>
				<div class="controls">
					<input type="text" id="a" name="amount" value="${amount}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">description</label>
				<div class="controls">
					<input type="text" name="description" value="${description}">
				</div>
			</div>
            <div class="control-group">
				<label class="control-label">deadline</label>
				<div class="controls">
					<input type="text" name="deadline" value="${deadline}">
				</div>
			</div>
            <div class="control-group">
				<label class="control-label">paydate</label>
				<div class="controls">
					<input type="text" name="paydate" value="${paydate}">
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

