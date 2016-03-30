<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
	<head>

		<script src="all.js"></script>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="UTF-8">
	    <title>Stats</title>
	</head>
	<body>

		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp">
    	<jsp:param name="name" value = "${it}"/>
		</jsp:include>

	    <input id="userlogin" type="hidden"  value="admin">
	    <input id="passwdlogin" type="hidden"  value="admin">

	    <div class="container">
	        <div class="row">
	        <div class="col-md-6 col-md-offset-3">
	        	<c:set var="taux" scope="session" value="20"/>
	        	<p>percent :</p>
	            <h1>Phrases métier non résolues</h1>
				<div class="progress">
					<div id="bar" class="progress-bar" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${taux}%;">
					</div>
				</div>
                <c:if test="${taux <= 30}">
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<strong>Attention!</strong> Vérifiez les phrases métier.
				</div>
				</c:if>

				<br>
			</div>
	        </div>
	    </div>

	    <script>
		$(document).ready(function(){
			var json = JSON.parse(getTaux("/v1/phrase/pourcentage"));
			
		});
		</script>

	</body>
</html>