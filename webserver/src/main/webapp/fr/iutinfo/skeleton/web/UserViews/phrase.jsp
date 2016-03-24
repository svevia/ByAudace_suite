<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="fr.iutinfo.skeleton.api.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html lang="fr">
	<head>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="utf-8">
	    <title>Phrases Metier</title>
	</head>
	<body>
		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp"/>
	    <div class="container">
	        <div class="row">
	            <div class="col-md-6 col-md-offset-3">
	                <h1>Phrases metier</h1>
	                <ul class="list-group">

           		    <c:forEach items="${it}" var="item">
                        <li class="list-group-item"><a href="phrase/${item.name}">${item.name}</a></li>
                    </c:forEach>

	                </ul>
	            </div>
	        </div>
	    </div>
	</body>
</html>