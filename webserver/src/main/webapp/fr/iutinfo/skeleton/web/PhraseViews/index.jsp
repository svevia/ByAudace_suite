<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
	<head>

		<script src="/all.js"></script>
	    <jsp:include page="/layout/head.jsp"/>
	    <meta charset="UTF-8">
	    <title>Phrases Metier</title>
	</head>
	<body>
		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp"/>

	    <input id="userlogin" type="hidden"  value="admin">
	    <input id="passwdlogin" type="hidden"  value="admin">
	    
	    <div class="container">
	        <div class="row">
	            <div class="col-md-6 col-md-offset-3">
	             <h1>Phrases métier</h1>
	            <div class="panel panel-default">

	                <div id="custom-search-input">
	                    <div class="input-group col-md-6">
	                        <input id="search" type="text" class="search-query form-control" placeholder="rechercher une phrase metier"/>
	                        <span class="input-group-btn">
	                        <button id="confirm" class="btn btn-danger" type="button">
	                        <span class=" glyphicon glyphicon-search"></span>
	                        </button>
	                        </span>
	                    </div>
	                </div>

	            	<div class="btn-group" role="group" aria-label="...">
					  <button id="phrase" type="button" class="btn btn-default">phrase</button>
					  <button id="besoin" type="button" class="btn btn-default">besoin</button>
					  <button id="mail" type="button" class="btn btn-default">mail</button>
					</div>

					<table id="table" class="table">
					<% int cpt =0; %>
					<c:forEach items="${it}" var="item">
					    <tr>
					    <td id="itphrase_<%=cpt%>" style="background-color:lightsteelblue;">${item.phrase}</td>
					    <td id="td2_<%=cpt%>" style="background-color:lightsteelblue;"><a id="link_<%=cpt%>" href="/html/phrase/${item.phrase}">détails</a></td>
						</tr>
						<tr>
					    <td id="itbesoin_<%=cpt%>">${item.besoin}</td>
					  	<td id="td4_<%=cpt%>"><button type="button" id="button_<%=cpt%>" name="${item.phrase}" class="btn btn-danger" data-toggle="modal" data-target="#myModal">supprimer</button></td>
					    </tr>
					    <%cpt++; %>
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
		var saisie = " ";
		$("button").click(function () {
		var id = $(this).attr("name");
		$("#oui").click(function () {
		 	$.ajax({
	          type: "DELETE",
	          url: "/v1/phrase/" + id,
	          beforeSend : function(req) {
       			req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
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

		$("#phrase").click(function () {
			getPhrase("/v1/phrase/orderphrase");
		});
		$("#besoin").click(function () {
			getPhrase("/v1/phrase/orderbesoin");
		});
		$("#mail").click(function () {
			getPhrase("/v1/phrase/ordermail");
		});
		$("#confirm").click(function () {
			var search = $("#search").val();
			getSearch("/v1/phrase/search?search="+search);
		});
	});
	</script>
</html>