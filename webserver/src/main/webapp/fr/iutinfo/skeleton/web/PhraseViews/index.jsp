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

	       			<div class="dropdown">
				  	<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					trier par
				    <span class="caret"></span>
				 	</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
					<li><a href="#">phrase</a></li>
					<li><a href="#">besoin</a></li>
					</ul>
					</div>

					<table class="table">
						<c:forEach items="${it}" var="item">
					    <tr>
					    <td style="background-color:lightsteelblue;">${item.phrase}</td>
					    <td style="background-color:lightsteelblue;"><a href="/html/phrase/${item.phrase}">détails</a></td>
						</tr>
						<tr>
					    <td>${item.besoin}</td>
					  	<td><button type="button" id="${item.phrase}" class="btn btn-danger" data-toggle="modal" data-target="#myModal">supprimer</button></td>
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
	</body>

	<script>

	$(document).ready(function() {
	$("button").click(function () {
		var id= this.id;
		$("#oui").click(function () {
		 	$.ajax({
	          type: "DELETE",
	          url: "/v1/phrase/" + id,
	          success: function(msg){
	            console.log("suppr"+this.id);
	            location.reload();
	          },
	          error: function(xhr, status, errorThrown){
	            console.log("Error: " + errorThrown);
	            console.log("Status: " + status);
	          },
	          });
			});
		});
	});
	</script>
</html>