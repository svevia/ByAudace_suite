<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
	<head>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="UTF-8">
	    <title>Phrases Metier</title>
	</head>
	<body>
		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp"/>
	    <div class="container">
	        <div class="row">
	            <div class="col-md-6 col-md-offset-3">
	                <h1>Phrases métier</h1>
	                <div class="panel panel-default">
					  <table class="table">
					  	<c:forEach items="${it}" var="item">
					    <tr>
					    <td style="background-color:lightsteelblue;">${item.phrase}</td>
					    <td style="background-color:lightsteelblue;">${item.mail}</td>
						</tr>
						<tr>
					    <td>${item.besoin}</td>
					    <td><button type="button" class="btn btn-danger">supprimer</button></td>
					    </tr>
					    </c:forEach>
					  </table>
					</div>
	            </div>
	        </div>
	    </div>
	</body>
</html>