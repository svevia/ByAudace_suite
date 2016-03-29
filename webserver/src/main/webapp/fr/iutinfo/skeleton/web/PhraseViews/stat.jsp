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
					</div>
	            </div>
	        </div>
	    </div>
	</body>
</html>