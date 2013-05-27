<div class="navbar">
	<div class="navbar-inner">
	  <ul class="nav">
      <li class="menu-link"><a href="./list">list</a></li>
      <c:if test="${login}">
      <li class="menu-link"><a href="./events">events</a></li>
      </c:if>
      <c:if test="${admin}">
      <li class="menu-link"><a href="./news">news</a></li>
      <li class="menu-link"><a href="./user">user</a></li>
      <li class="menu-link"><a href="./balance">balance</a></li>
      <li class="menu-link"><a href="./event">event</a></li>
      <li class="menu-link"><a href="./room">room</a></li>
      <li class="menu-link"><a href="./roomchange">roomchange</a></li>
      <li class="menu-link"><a href="./admin">admin</a></li>
      </c:if>
	  </ul>
	</div>
</div>