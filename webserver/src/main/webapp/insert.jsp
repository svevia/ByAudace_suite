<!DOCTYPE html>
<html lang="fr">
    <head>
        <script src="all.js"></script>
      <jsp:include page="/layout/head.jsp"/>
      <meta charset="utf-8">
      <title>Ajout User</title>
    </head>
    <body>
        <jsp:include page="/layout/logo.jsp"/>
      <jsp:include page="/layout/navbar.jsp"/>

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
User</label>

<label class="radio-inline">
<input type = "radio" name = "role" id = "admin" value = "amin" />
Admin</label>
  </div>
  <button id="submit" class="btn btn-default">Creer</button>
</form>
  </div>

  <script type="text/javascript">
        $(document).ready(function() {
            $("#submit").click(function () {
                mail = $('#mail').val();
                pass = $('#password').val();
                nom = $('#nom').val();
                prenom = $('#prenom').val();
                tel = $('#tel').val();
                digit = $('#prenom').val();
                postUserBdd(mail,pass,nom,prenom,tel,digit)});
        });
  </script>

</body>
</html>
