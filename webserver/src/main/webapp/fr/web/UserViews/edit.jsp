<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <script src="/all.js"></script>
      <jsp:include page="/layout/head.jsp"/>
      <meta charset="utf-8">
      <title>Edit User</title>
    </head>
    <body>
      <jsp:include page="/layout/logo.jsp"/>
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it.user.name}"/>
            <jsp:param name="role" value="${it.user.role}"/>
	</jsp:include>

<div class="container">

<h1>Editer utilisateur</h1>
  <div class="form-group">
    <label for="mail">ID : ${it.userDetail.id}</label>
  </div>
    <div class="form-group">
<label for="mail">Role : ${it.userDetail.role}</label>
  </div>
  <div class="form-group">
    <label for="mail">Adresse mail</label>
    <input type="email" class="form-control" id="mail" value="${it.userDetail.mail}">
  </div>
  <div class="form-group">
    <label for="mot-de-passe">Mot de passe</label>
    <input type="password" class="form-control" id="password"  value="${it.userDetail.mot_de_passe}">
  </div>
  <div class="form-group">
    <label for="name">Nom</label>
    <input type="text" class="form-control" id="name"  value="${it.userDetail.name}">
  </div>
  <div class="form-group">
    <label for="prenom">Prenom</label>
    <input type="text" class="form-control" id="prenom" value="${it.userDetail.prenom}">
  </div>
  <div class="form-group">
    <label for="tel">Telephone</label>
    <input type="tel" class="form-control" id="tel" value="${it.userDetail.numero}">
  </div>
  
  <!--
  <div class="form-group">
    <label for="mail">Categorie</label>
    <input type="text" class="form-control" id="digit" value="${it.userDetail.categorie}">
  </div>
  -->
  <div class="form-group">
	  <label for="number">Catégorie de l'utilisateur</label><br/>
	  <select id="catego" name="Catégorie de l'utilisateur" style="width:100%;height:30px">
	  
		<% int cpt =0; %>
		<c:forEach items="${it.list}" var="item">
			
			<option id="cat_${item}" value="${item}">${item}</option>
			<script>
				if('${item}' == '${it.userDetail.categorie}')
					$('#cat_${item}').attr('selected','selected');
			</script>
			
			<%cpt++; %>
		</c:forEach>

	  </select>
  </div>
  

<input type="hidden" class="form-control" id="role" value="${it.userDetail.role}">
<input type="hidden" class="form-control" id="id" value="${it.userDetail.id}">
  <button id="submit" class="btn btn-default">Editer</button>
  </div>

  <script type="text/javascript">
        $(document).ready(function() {
            $("#submit").click(function () {
                mail = $('#mail').val();
                pass = $('#password').val();
                name = $('#name').val();
                prenom = $('#prenom').val();
                tel = $('#tel').val();
                categorie = $('#catego').val();
                role = $("#role").val();
                id = $('#id').val();
                editUser(mail,name,prenom,categorie,pass,tel,role,id)});
        });
  </script>

</body>
</html>
