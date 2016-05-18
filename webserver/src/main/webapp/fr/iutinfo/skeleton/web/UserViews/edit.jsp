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
    <jsp:param name="name" value = "${it.name}"/>
	</jsp:include>

<div class="container">

<h1>Ajout utilisateur</h1>
  <div class="form-group">
    <label for="mail">Adresse mail</label>
    <input type="email" class="form-control" id="mail" value="${it.user.mail}">
  </div>
  <div class="form-group">
    <label for="mot-de-passe">Mot de passe</label>
    <input type="password" class="form-control" id="password"  value="${it.user.mot_de_passe}">
  </div>
  <div class="form-group">
    <label for="nom">Nom</label>
    <input type="text" class="form-control" id="nom"  value="${it.user.nom}">
  </div>
  <div class="form-group">
    <label for="prenom">Prenom</label>
    <input type="text" class="form-control" id="prenom" value="${it.user.prenom}">
  </div>
  <div class="form-group">
    <label for="tel">Telephone</label>
    <input type="tel" class="form-control" id="tel" value="${it.user.numero}">
  </div>
  <div class="form-group">
    <label for="mail">Digit</label>
    <input type="text" class="form-control" id="digit" value="${it.user.digit}">
  </div>

<input type="hidden" class="form-control" id="role" value="${it.user.role}">
<input type="hidden" class="form-control" id="id" value="${it.user.id}">
  <button id="submit" class="btn btn-default">Editer</button>
  </div>

  <script type="text/javascript">
        $(document).ready(function() {
            $("#submit").click(function () {
                mail = $('#mail').val();
                pass = $('#password').val();
                nom = $('#nom').val();
                prenom = $('#prenom').val();
                tel = $('#tel').val();
                digit = $('#digit').val();
                role = $("#role").val();
                id = $('#id').val();
                editUser(mail,nom,prenom,digit,pass,tel,role,id)});
        });
  </script>

</body>
</html>
