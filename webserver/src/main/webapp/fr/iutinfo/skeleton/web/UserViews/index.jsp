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
    <jsp:include page="/layout/navbar.jsp">
    <jsp:param name="name" value = "${it.name}"/>
	</jsp:include>


    <div class="container">
    

    
    
    
    
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
             <h1>Utilisateurs</h1>
            <div class="panel panel-default">
            
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
            
            
            
                    <table id="table" class="table">
                    <% int cpt =0; %>
                    <c:forEach items="${it.user}" var="item">
                        <tr id = "user_<%=cpt%>">
                        <td id = "mail_<%=cpt%>"><a id="linkMail_<%=cpt%>" href="/html/userdb/${item.id}">${item.mail}</a></td>
                        <td><button id="b_<%=cpt%>" name="${item.id}" type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal">supprimer</button></td>
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
		var id = $(this).attr("name");
		$("#oui").click(function () {
		 	$.ajax({
	          type: "DELETE",
	          url: "/v1/userdb/" + id,
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
		
		$('*').keypress(function(e){
		    if( e.which == 13 ){
		    	var search = $("#search").val();
				getSearch("/v1/phrase/search?search="+search);
		    }
		});
		
		
		<%--reccupère la valeur contenue dans la search bar puis appel à all.js sur la recherche des users--%>
		$("#confirm").click(function () {
			var search = $("#search").val();
			getSearchUser("/v1/userdb/search?search="+search);
		});

	});
	</script>
</html>
