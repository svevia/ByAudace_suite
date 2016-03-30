<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>User : ${it.name}</title>
    <jsp:include page="/layout/head.jsp"/>
  </head>
  <body>
        <jsp:include page="/layout/logo.jsp"/>
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it.name}"/>
	</jsp:include>


    <div class="container">
      <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <a href="/html/userdb"><span style="font-size:30px;" class="glyphicon glyphicon-arrow-left"></span></a>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">Détails</h3>
              </div>
              <div class="panel-body">
                Nom : ${it.user.nom}<br/>
				Prenom : ${it.user.prenom} <br/>
                Mot de passe : ${it.user.mot_de_passe} <br/>
                Mail : ${it.user.mail} <br/>
                Role : ${it.user.role} <br/>
                Telephone : ${it.user.numero} <br/>
                Digit : ${it.user.digit} <br/>
              </div>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>
