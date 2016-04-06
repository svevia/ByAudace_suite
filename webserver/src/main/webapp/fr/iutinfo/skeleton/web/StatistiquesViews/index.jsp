<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--cette page affiche le taux de phrase non résolues et créée une alerte si celui-ci descend en-dessous de 30%-->
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

	<%--affiche une barre de porgression avec le taux de phrases non résolues généré par le script--%>
	    <div class="container">
	        <div class="row">
	        <div class="col-md-6 col-md-offset-3">
	        <%--remplacer la valeur 20 par it.percent--%>
	        	<c:set var="taux" scope="session" value="20"/>
	        	<p>percent :</p>
	            <h1>Phrases métier non résolues</h1>
				<div class="progress">
					<div id="bar" class="progress-bar" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${taux}%;">
					</div>
				</div>
				<%--affiche une alerte si le taux de phrases non résolues se situe en dessous de 30%--%>
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
	    <%--chargmeent du taux de phrases non résolues--%>
	    <%--nombre de phrases non temrinées/nombre de phrases totales) --%>
	    <script>
		$(document).ready(function(){
			var json = JSON.parse(getTaux("/v1/phrase/pourcentage"));
			
		});
		</script>

	</body>
</html>
