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
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it}"/>
	</jsp:include>

<div class="container">

<h1>Ajout utilisateur</h1>
  <div class="form-group">
    <label for="mail">Adresse mail</label>
    <input type="email" class="form-control" id="mail" placeholder="mail">
  </div>
  <div class="form-group">
    <label for="mot-de-passe">Mot de passe</label>
    <input type="password" class="form-control" id="password" placeholder="Password">
  </div>
  <div class="form-group">
    <label for="nom">Nom</label>
    <input type="text" class="form-control" id="nom" placeholder="nom">
  </div>
  <div class="form-group">
    <label for="prenom">Prenom</label>
    <input type="text" class="form-control" id="prenom" placeholder="prenom">
  </div>
  <div class="form-group">
    <label for="tel">Telephone</label>
    <input type="tel" class="form-control" id="tel" placeholder="tel">
  </div>
  <div class="form-group">
    <label for="mail">Digit</label>
    <input type="text" class="form-control" id="digit" placeholder="digit">
  </div>

  <div class="form-group">
          <label for="roleLabel">Role</label>
          <br/>
<label class="radio-inline">
  <input type = "radio" name = "role" id = "user" value = "user" checked = "checked" />
user</label>

<label class="radio-inline">
<input type = "radio" name = "role" id = "admin" value = "admin" />
admin</label>
  </div>
  <button id="submit" class="btn btn-default">Creer</button>
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
                role = $("input:radio[name ='role']:checked").val();
                postUser(mail,nom,prenom,digit,pass,role,tel)});
        });
  </script>

</body>
</html>
