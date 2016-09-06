<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--cette page affiche les détails d'une phrase métier soit sa phrase,
son besoin, le nombre de consultation, si elle est terminée-->
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>${it.phrases.phrase}</title>
    <jsp:include page="/layout/head.jsp"/>
  </head>
  <body>
    <jsp:include page="/layout/logo.jsp"/>
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it.user.name}"/>
            <jsp:param name="role" value="${it.user.role}"/>
	</jsp:include>

    <div class="container">
      <div class="row">
        <div class="col-md-6 col-md-offset-3">
        <%--retour à phrase métier--%>
          <a href="/html/phrase"><span style="font-size:30px;" class="glyphicon glyphicon-arrow-left"></span></a>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">Détails</h3>
              </div>
              <%--place les attribus de it qui est appelé par la requête /phrase/${it.phrase}--%>
              <div class="panel-body" style="font-size:17px;">
              id : ${it.phrases.id}<br/>
                ${it.phrases.phrase}<br/>
				${it.phrases.besoin}<br/>  
				categorie : ${it.phrases.categorie}<br/>          
                user : ${it.createur.mail}<br/>
                date : ${it.phrases.date}<br/>
                signalement : ${it.phrases.signalement}<br/>
                <%--vérifie l'attribut terminee de la phrase et fait un affichage différent en fonction--%>
                <c:set var="finie" scope="session" value="${it.phrases.terminee}"/>
                <c:if test="${finie == false}"><span style="font-size:25px;" class="glyphicon glyphicon-remove-circle"></span></br></c:if>
                <c:if test="${finie == true}"><span style="font-size:25px;" class="glyphicon glyphicon-ok-circle"></span><br/></c:if>
              </div>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>
