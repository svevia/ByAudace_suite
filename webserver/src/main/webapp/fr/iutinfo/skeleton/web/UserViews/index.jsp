<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>

  	<script src="/all.js"></script>
    <jsp:include page="/layout/head.jsp"/>
    <meta charset="utf-8">
    <title>Utilisateurs</title>
  </head>
  <body>
        <jsp:include page="/layout/logo.jsp"/>
    <jsp:include page="/layout/navbar.jsp"/>

	<input id="userlogin" type="hidden"  value="admin">
	<input id="passwdlogin" type="hidden"  value="admin">

    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
             <h1>Utilisateurs</h1>
            <div class="panel panel-default">

                    <table class="table">
                    <c:forEach items="${it}" var="item">
                        <tr>
                        <td><a href="/html/userdb/${item.mail}">${item.mail}</a></td>
                        <td><button id="${item.mail}" type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal">supprimer</button></td>
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
		        <p>Voulez-vous supprimer cet utilisateur?</p>
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
		var mail= this.id;
		$("#oui").click(function () {
		 	$.ajax({
	          type: "DELETE",
	          url: "/v1/userdb/" + mail,
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

	});
	</script>
</html>
