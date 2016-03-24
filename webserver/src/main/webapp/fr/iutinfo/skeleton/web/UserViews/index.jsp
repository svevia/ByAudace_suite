<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <jsp:include page="/layout/head.jsp"/>
    <meta charset="utf-8">
    <title>Liste des "User"</title>
  </head>
  <body>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h1>Liste des "User"</h1>
                <ul class="list-group">


                    <c:forEach items="${it}" var="item">
                        <li class="list-group-item"><a href="userdb/${item.mail}">${item.mail}</a></li>
                    </c:forEach>

                </ul>
            </div>
        </div>
    </div>
  </body>
</html>
