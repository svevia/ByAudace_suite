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
	            <h1>Phrases métier non résolues</h1>
				<div class="progress">
					<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:60%;">
				    60%
					</div>
				</div>
				<br>
                <form action="" class="search-form">
                    <div class="form-group has-feedback">
                    <input type="text" class="form-control" name="search" id="search" placeholder="rechercher une phrase metier">
                    <span class="glyphicon glyphicon-search form-control-feedback"></span>
                    </div>
                </form>

				<div class="btn-group" role="group" aria-label="...">
					<h4>Trier par<h4>
					<button id="consultee" type="button" class="btn btn-default">nombre de consultations</button>
					<button id="phrase" type="button" class="btn btn-default">phrase</button>
					<button id="besoin" type="button" class="btn btn-default">besoin</button>
					<button id="mail" type="button" class="btn btn-default">mail</button>
				</div>

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
	</body>
</html>