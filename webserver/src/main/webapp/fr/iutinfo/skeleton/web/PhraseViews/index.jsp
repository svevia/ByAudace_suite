<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--cette page affiche la liste des phrases métier, elle permet de faire une recherche par mot clé,--%>
<%--un tri selon la phrase, le besoin ou le mail, afficher les détails d'une phrase ou la supprimer--%>
<!DOCTYPE html>
<html lang="fr">

		<script src="/all.js"></script>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="UTF-8">
	    <title>Phrases Metier</title>
	</head>
	<body>
	
	 
		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp">
	  
	    <jsp:param name="name" value = "${it.name}"/>
		</jsp:include>
	    
	    <div class="container">
	        <div class="row">
	            <div class="col-md-6 col-md-offset-3">
	             <h1>Phrases métier</h1>
	            <div class="panel panel-default">
			<%--barre de recherche pour les phrases métiers--%>
	                <div id="custom-search-input">
	                    <div class="input-group col-md-12">
	                        <input id="search" type="text" class="search-query form-control" placeholder="rechercher ..."/>
	                        <span class="input-group-btn">
	                        <button id="confirm" class="btn btn-danger" type="button">
	                        <span class=" glyphicon glyphicon-search"></span>
	                        </button>
	                        </span>
	                    </div>
	                </div>
			<%--boutons pour trier la liste des phrases, appel à all.js dans le script--%>
	            	<div class="btn-group" role="group" aria-label="...">
					  <button id="phrase" type="button" class="btn btn-default">phrase</button>
					  <button id="besoin" type="button" class="btn btn-default">besoin</button>
					  <button id="mail" type="button" class="btn btn-default">mail</button>
					  <button id="date" type="button" class="btn btn-default">date</button>
					  <button id="signalement" type="button" class="btn btn-default">signalé</button>
					</div>
					<%--affichage de la liste des phrases métier--%>
					<table id="table" class="table">
					<% int cpt =0; %>
					<c:forEach items="${it.phrases}" var="item">
					    <tr>
					    <td id="itphrase_<%=cpt%>" style="max-width:300px;word-wrap: break-word;;background-color:lightsteelblue;">${item.phrase}</td>
					    <td id="td2_<%=cpt%>" style="background-color:lightsteelblue;"><a id="link_<%=cpt%>" href="/html/phrase/${item.id}">détails</a></td>
						<td id="signalement_<%=cpt%>" <c:if test="${item.signalement > 5}"> style="color:red;" </c:if> style="background-color:lightsteelblue;">signalements : ${item.signalement}</td>
						</tr>
						<tr>
					    <td id="itbesoin_<%=cpt%>" style="max-width:300px ;word-wrap: break-word;">${item.besoin}</td>
					  	<td id="td4_<%=cpt%>"><button type="button" id="button_<%=cpt%>" name="${item.id}" class="btn btn-danger" data-toggle="modal" data-target="#myModal">supprimer</button></td>
					    </tr>
					    <%cpt++; %>
					    </c:forEach>
					</table>
				</div>
	            </div>
	        </div>
	    </div>
	    <%--pop-up de confirmation pour la suppression d'une phrase--%>
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
		var saisie = " ";
		<%--attention ! réccupère l'attribut "name" du bouton et non "id", pour le passer en paramètre à la requête delete du serveur--%>
		$("button").click(function () {
		var id = $(this).attr("name");
		console.log(id);
		<%--confirme la suppression d'une phrase, appel direct au serveur--%>
		$("#oui").click(function () {
		 	$.ajax({
	          type: "DELETE",
	          url: "/v1/phrase/" + id,
	          beforeSend : function(req) {
	        	  req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
	        },
	          success: function(msg){
	            location.reload();
	          },
	          error: function(xhr, status, errorThrown){
	            console.log("Error: " + errorThrown);
	            console.log("Status: " + status);
	          },
	          });
			});
		});
		<%--appel à all.js pour trier les phrases par le champ phrase--%>
		$("#phrase").click(function () {
			getPhrase("/v1/phrase/orderphrase");
		});
		<%--appel à all.js pour trier les phrases par le champ besoin--%>
		$("#besoin").click(function () {
			getPhrase("/v1/phrase/orderbesoin");
		});
		<%--appel à all.js pour trier les phrases par le champ mail--%>
		$("#mail").click(function () {
			getPhrase("/v1/phrase/ordermail");
		});
		
		<%--appel à all.js pour trier les phrases par le champ mail--%>
		$("#date").click(function () {
			getPhrase("/v1/phrase/orderdate");
		});
		<%--appel à all.js pour trier les phrases par le champ mail--%>
		$("#signalement").click(function () {
			getPhrase("/v1/phrase/ordersignal");
		});
		
		$('*').keypress(function(e){
		    if( e.which == 13 ){
		    	var search = $("#search").val();
				getSearch("/v1/phrase/search?search="+search);
		    }
		});
		<%--reccupère la valeur contenue dans la search bar puis appel à all.js sur la recherche des phrases métier--%>
		$("#confirm").click(function () {
			var search = $("#search").val();
			getSearch("/v1/phrase/search?search="+search);
		});

		
	});
	</script>
</html>
