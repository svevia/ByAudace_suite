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
      
    <c:if test="${it.user.role eq 'admin' || it.user.role eq 'root'}">
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it.user.name}"/>
    </jsp:include>
    </c:if>


<div class="container">

<form class="form-inline">
  <div class="form-group">
    <label for="dureeVie">Durée de vie d'une phrase</label>
    <input type="number" class="form-control" id="dureeVie" min="1" step="1" >
  </div>
  <br>
  <div class="form-group">
    <label for="nbrPhrases">Nombre de phrases maximum</label>
    <input type="number" class="form-control" id="nbrPhrases" min="1" step="1" >
  </div>
  <button id ="submit" type="submit" class="btn btn-primary">Modifier</button>
 </form> 
 
 <hr>
  
  <div class="form-group">
    <label for="categoUsers">Ajouter une catégorie d'utilisateur</label>
    <input type="text" class="form-control" id="categoUsers">
  </div>
  <button onClick="addCategorie()" class="btn btn-primary">Ajouter</button>
  
  <hr>
  
    <table id="table" class="table">
		<% int cpt =0; %>
		<c:forEach items="${it.list}" var="item">
			<tr id = "cat_<%=cpt%>">
			<td id = "name_<%=cpt%>">${item}</td>
			<td><button onClick="remCategorie('${item}')" id="del_<%=cpt%>" name="${item}" type="button" class="btn btn-danger">Supprimer</button></td>
			</tr>
			<%cpt++; %>
		</c:forEach>
	</table>
  



</div>
</body>
</html>