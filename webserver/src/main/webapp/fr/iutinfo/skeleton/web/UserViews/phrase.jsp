<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
	<head>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="utf-8">
	    <title>Phrases Métier</title>
	</head>
	<body>
	    <jsp:include page="/layout/navbar.jsp"/>
	    <div class="container">
	        <div class="row">
	            <div class="col-md-6 col-md-offset-3">
	                <h1>Liste des "User"</h1>
	                <ul class="list-group">
	                <c:forEach var="phrase" items="${it}">
	                    <li class="list-group-item">${phrase.phrase} : <button type="button" class="btn btn-default">supprimer</button>
	                </c:forEach>
	                </ul>
	            </div>
	        </div>
	    </div>
	</body>
</html>