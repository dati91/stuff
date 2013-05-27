<div class="page-header">
<div class="row">
	<div class="span8">
		<a href="./" class="main-title">
			<h1>Kollégiumi Nyilvántartás<br/><small>Rendszerfejlesztés I. project</small></h1>
		</a>
	</div>
    <div class="span2 login-area">
		<c:choose>
		<c:when test="${login}">
		<form name="user" method="post" action="./user">
			<input type="hidden" name="username" value="${username}">
			<div class="btn-group">
			<a class="login-link btn" href="javascript: document.user.submit();">${username}</a>
			<a class="login-link btn" href="./logout">logout</a>
			</div>
		</form>
		</c:when>
		<c:otherwise>
		<div class="btn-group">
		  <a class="login-link btn" href="./login">login</a>
		  <a class="login-link btn" href="./signup">signup</a>
		</div>
		</c:otherwise>
		</c:choose>
	</div>
</div>
</div>