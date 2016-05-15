$(document).ready(function(){
	getNombreUser(function(nb){
		$('#usersInscrits').text(nb);
	});
	getNombrePhrase(function(nb){
		var nbrPhrase = nb;
		$('#phrasesTotales').text(nb);
		getActivite(function(nb2 , nbrPhrase){
			var moyenne = nb2/nbrPhrase;
			$('#moyenne').text(nb2/nbrPhrase)
		},nbrPhrase);
	});
	getNombreFinit(function(nb){
		$('#phrasesFinit').text(nb);
	});
	
	 $("#sendMail").click(function () {
		 sendMail($('#message').val());
 });	
	
	
});

function getNombreUser(success) {
    return $.ajax({
       type: "GET",
       url: "/v1/userdb/nbr",
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        success(data);
        
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);

       	}
     });
}

function sendMail(data) {
    return $.ajax({
       type: "POST",
       url: "/v1/userdb/mail",
       contentType : 'application/json',
		data : data,
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        alert("mail envoyé");
        
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);
       			alert("erreur d'envoi, contacter l'administrateur système");

       	}
     });
}

function getNombreFinit(success) {
    return $.ajax({
       type: "GET",
       url: "/v1/phrase/finit",
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        success(data);
        
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);

       	}
     });
}

function getActivite(success,nbrPhrase) {
    return $.ajax({
       type: "GET",
       url: "/v1/phrase/activite",
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        success(data,nbrPhrase);
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);

       	}
     });
}

function getNombrePhrase(success) {
    return $.ajax({
       type: "GET",
       url: "/v1/phrase/nbr",
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        success(data);
        
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);

       	}
     });
}