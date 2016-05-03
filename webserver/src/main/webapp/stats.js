$(document).ready(function(){
	getNombreUser(function(nb){
		$('#usersInscrits').text(nb);
	});
	getNombrePhrase(function(nb){
		nbrPhrase = nb;
		$('#phrasesTotales').text(nb);
		getActivite(function(nb2,nbrPhrase){
			$('#moyenne').text(nb2/nbrPhrase)
		});
	});
	getNombreFinit(function(nb){
		$('#phrasesFinit').text(nb);
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