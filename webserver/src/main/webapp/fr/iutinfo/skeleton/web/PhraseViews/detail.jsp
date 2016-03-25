<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>${it.phrase}</title>
    <jsp:include page="/layout/head.jsp"/>
  </head>
  <body>
    <jsp:include page="/layout/logo.jsp"/>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
      <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">Détails</h3>
              </div>
              <div class="panel-body">
                ${it.phrase}<br/>
				        ${it.besoin} <br/>                
                ${it.mail} <br/>
                <c:set var="finie" scope="session" value="${it.terminee}"/>
                <c:if test="${finie === false}">résolue : non</br></c:if>
                <c:if test="${finie === true}">résolue : oui<br/></c:if>
                nombre de consultations : ${it.consultee}<br/>
              </div>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>
