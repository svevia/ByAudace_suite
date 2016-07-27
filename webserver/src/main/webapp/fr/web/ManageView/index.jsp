<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <script src="/all.js"></script>
      <jsp:include page="/layout/head.jsp"/>
      <script src="/manage.js"></script>
      <meta charset="utf-8">
      <title>Gestion</title>
    </head>
    <body>
      <jsp:include page="/layout/logo.jsp"/>
      
     <c:if test="${it.role eq 'admin' || param.role eq 'root'}">
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it.name}"/>
    	</jsp:include>
    </c:if>


<div class="container">

<form class="form-inline">
  <div class="form-group">
    <label for="dureeDeVie">Durée de vie d'une phrase</label>
    <input type="number" class="form-control" id="dureeDeVie" min="1" step="1" >
  </div>
  <div class="form-group">
    <label for="nbrPhrases">Nombre de phrases maximum</label>
    <input type="number" class="form-control" id="nbrPhrases" min="1" step="1" >
  </div>
  <button id ="submit" type="submit" class="btn btn-primary">Modifier</button>
</form>



</div>
</body>
</html>