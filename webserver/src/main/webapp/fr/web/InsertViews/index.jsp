<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <script src="/all.js"></script>
      <jsp:include page="/layout/head.jsp"/>
      <meta charset="utf-8">
      <title>Ajout User</title>
    </head>
    <body>
      <jsp:include page="/layout/logo.jsp"/>
      
     <c:if test="${it.user.role eq 'admin' || it.user.role eq 'root'}">
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it.user.name}"/>
    	</jsp:include>
    </c:if>

<div class="container">

<h1>Ajout utilisateur</h1>
  <div class="form-group">
    <label for="mail">Adresse mail</label>
    <input type="email" class="form-control" id="mail" placeholder="Adresse mail">
  </div>
  <div class="form-group">
    <label for="name">Nom</label>
    <input type="text" class="form-control" id="name" placeholder="Nom">
  </div>
  <div class="form-group">
    <label for="prenom">Prénom</label>
    <input type="text" class="form-control" id="prenom" placeholder="Prénom">
  </div>
  <div class="form-group">
    <label for="tel">Téléphone</label>
    <input type="tel" class="form-control" id="tel" placeholder="Téléphone">
  </div>
  
  <hr>
  
  <div class="form-group">
	  <label for="number">Catégorie de l'utilisateur</label><br/>
	  <select id="catego" name="Catégorie de l'utilisateur" style="width:100%;height:30px">
	  
	  
		<c:forEach items="${it.list}" var="item">
			<option value="${item}">${item}</option>
		</c:forEach>
	
	  </select>
  </div>
  
  <!--
  <div class="form-group">
    <label for="number">Année de formation</label>
    <input type="number" class="form-control" id="annee" placeholder="Année de formation">
  </div>  
  
  <div class="form-group">
    <label for="number">Numéro de formation</label>
    <input type="number" class="form-control" id="numform" placeholder="Numéro de formation">
  </div>  
  
  <div class="form-group">
	  <label id="type" for="number">Type de formation</label><br/>
	  <select name="Type de formation" style="width:100%;height:30px">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
	  </select>
  </div>
  -->
  
  <!--
  <div class="form-group">
    <label for="mail">Digit</label>
    <input type="text" class="form-control" id="digit" placeholder="digit" readonly>
  </div>
  -->
  
  <hr>
  
   <c:if test="${it.user.role eq 'admin' || it.user.role eq 'root'}">
  <div class="form-group">
          <label for="roleLabel">Role</label>
          <br/>
<label class="radio-inline">
  <input type = "radio" name = "role" id = "user" value = "user" checked = "checked" />
user</label>

<label class="radio-inline">
<input type = "radio" name = "role" id = "admin" value = "admin" />
admin</label>

<label class="radio-inline">
<input type = "radio" name = "role" id = "animateur" value = "animateur" />
animateur</label>
  </div>
  </c:if>
  <button id="submit" class="btn btn-default">Creer</button>
  </div>

  <hr>

  <script type="text/javascript">	
        $(document).ready(function() {						
            $("#submit").click(function () {				
                mail = $('#mail').val();
                name = $('#name').val();
                prenom = $('#prenom').val();
                tel = $('#tel').val();
                categorie =  $('#catego').find(":selected").text();
                role = $("input:radio[name ='role']:checked").val();
                if(role==null){role = "user"}
                postUser(mail,name,prenom,categorie,role,tel)});
        });
  </script>

</body>
</html>
