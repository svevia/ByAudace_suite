<%@ page import="fr.api.User" %>
<nav class="navbar navbar-default"  style="font-size:18px;">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

        <ul class="nav navbar-nav navbar-right">
            <li><a href="#">logger en : <%= request.getParameter("name") %></a></li>
		</ul>
    </div>
</nav>
