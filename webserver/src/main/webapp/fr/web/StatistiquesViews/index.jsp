<%@page contentType="text/html"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--cette page affiche le taux de phrase non résolues et créée une alerte si celui-ci descend en-dessous de 30%-->
<!DOCTYPE html>
<html lang="fr">
	<head>
		<style>
		
		.borderLine { margin-left:20px; margin-right:20px; }
		
		
		
		
		</style>

	    <jsp:include page="/layout/head.jsp"/>
	     <script src="/all.js"></script>
	    <script src="/stats.js"></script>
	    <meta charset="UTF-8">
	    <title>Stats</title>
	</head>
	<body>

		<jsp:include page="/layout/logo.jsp"/>
	    <jsp:include page="/layout/navbar.jsp">
    	<jsp:param name="name" value = "${it.user.name}"/>
    	        <jsp:param name="role" value="${it.user.role}"/>
		</jsp:include>



	    <div class="container">
	    
	    
	    				<div class="row borderLine">
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
										<i class="fa fa-user fa-5x" aria-hidden="true"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div id="usersInscrits" class="huge">0</div>
                                        <div>Utilisateurs inscrits</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-check fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div id="phrasesFinit" class="huge">0</div>
                                        <div>Phrases résolues</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-bars fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div id="phrasesTotales" class="huge">0</div>
                                        <div>Phrases totales</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    
                    
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-comments fa-5x"> </i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div id="moyenne" class="huge">0</div>
                                        <div>Moyenne de réponses par phrases</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
				</div>
				
				<h1>Contacter la communauté :</h1>
				<div id="formMail">
				<div class="col-lg-8 col-md-6">
					<input type="text" id="titre" class="form-control" placeholder="Sujet du message"><br>
					<textarea class="form-control" id="message" placeholder="Texte du message" rows="10"></textarea><br>
					
					<c:forEach items="${it.list}" var="item">
						<input type="checkbox" checked="true" id="${item}">${item}&emsp;
					</c:forEach>
					<!--
					<input type="checkbox" checked="true" id="cbox">Militaire&emsp;
					<input type="checkbox" checked="true" id="cbox2">Chômeur&emsp;
					<input type="checkbox" checked="true" id="cbox3">Etudiant
					-->
					
					<hr/>
					<div class="form-group">
			   		<div class="col-sm-offset-2 col-sm-10">
			      <button id="sendMail" type="submit" class="btn btn-default">Envoyer</button>
			    </div>
			  </div>
				
				</div>
				</div>
				
				

	    
	    </div>

	</body>
</html>
