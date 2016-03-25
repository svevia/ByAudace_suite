<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
	<head>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="UTF-8">
	    <title>Phrases Metier</title>
	</head>
	<body>
		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp"/>
	    <div class="container">
	        <div class="row">
	            <div class="col-md-6 col-md-offset-3">
	                <h1>Phrases métier</h1>
	                <div class="panel panel-default">
					  <table class="table">
					  	<c:forEach items="${it}" var="item">
					    <tr>
					    <td style="background-color:lightsteelblue;">${item.phrase}</td>
					    <td style="background-color:lightsteelblue;"><a href="/html/phrase/${item.phrase}">détails</a></td>
						</tr>
						<tr>
					    <td>${item.besoin}</td>
					    <td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal">supprimer</button></td>
					    </tr>
					    </c:forEach>
					  </table>
					</div>
	            </div>
	        </div>
	    </div>

		<div  id="myModal" class="modal fade" tabindex="-1" role="dialog">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		      </div>
		      <div class="modal-body">
		        <p>Voulez-vous supprimer cette phrase métier?</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Non</button>
		        <button id="oui" type="button" class="btn btn-primary">Oui</button>
		      </div>
		    </div>
		  </div>
		</div>

	    <script type="text/javascript">
			$(document).ready(function() {
				$("#oui").click(function () {
					console.log("suppr");
				});
			});
   		</script>
	</body>
</html>