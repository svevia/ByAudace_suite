<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
	<head>

		<script src="all.js"></script>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="UTF-8">
	    <title>Phrases Metier</title>
	</head>
	<body>

		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp"/>

	    <input id="userlogin" type="hidden"  value="admin">
	    <input id="passwdlogin" type="hidden"  value="admin">

	    <div class="container">
	        <div class="row">
	        <div class="col-md-6 col-md-offset-3">
	            <h1>Phrases métier non résolues</h1>
				<div class="progress">
					<div id="bar" class="progress-bar" role="progressbar" aria-valuenow="${it}" aria-valuemin="0" aria-valuemax="100" style="width:30%;">
				    ${it}
					</div>
				</div>
 				<c:set var="taux" scope="session" value="30"/>
                <c:if test="${taux <= 30}">
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<strong>Attention!</strong> Vérifiez les phrases métier.
				</div>
				</c:if>

				<br>
                <form action="" class="search-form">
                    <div class="form-group has-feedback">
                    <input type="text" class="form-control" name="search" id="search" placeholder="rechercher une phrase metier">
                    <span class="glyphicon glyphicon-search form-control-feedback"></span>
                    </div>
                </form>

				<table class="table">
					<c:forEach items="${it}" var="item">
					<tr>
					<td style="background-color:lightsteelblue;">${item.phrase}</td>
					</tr>
					<tr>
					<td>${item.besoin}</td>
					</tr>
					<tr>
					<td style="background-color:lightsteelblue;">${item.mail}</td>
					</tr>
					<tr>
					<td>consultée ${item.consultee} fois</td>
					</tr>
					</c:forEach>
			  	</table>

			</div>
	        </div>
	    </div>

	    <script type="text/javascript">
		$(document).ready(function(){
			getTaux("/v1/phrase/pourcentage");
		});
		</script>

	</body>
</html>