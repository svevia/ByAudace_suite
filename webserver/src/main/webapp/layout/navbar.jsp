<%@ page import="fr.api.User" %>
<nav class="navbar navbar-default"  style="font-size:18px;">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
			<li><a href="/html/userdb">Utilisateurs</a></li>
            <li><a href="/html/insert">Ajouter un utilisateur</a></li>
            <li><a href="/html/phrase">Phrases metier</a></li>
            <li><a href="/html/statistiques">Statistiques</a></li>
            <li><a href="/html/manage">Gestion</a></li>
            <li><a href="/ByAudace.apk">Télécharger application</a></li>
            </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#">logger en : <%= request.getParameter("name") %></a></li>
		</ul>
    </div>
</nav>
