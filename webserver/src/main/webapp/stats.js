$(document).ready(function(){
	getNombreUser(function(nb){
		$('#usersInscrits').text(nb);
	});
	getNombrePhrase(function(nb){
		nbrPhrase = nb;
		$('#phrasesTotales').text(nb);
	});
	getNombreFinit(function(nb){
		$('#phrasesFinit').text(nb);
	});
	getActivite(function(nb){
		$('#moyenne').text(nb/nbrPhrase)
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

function getActivite(success) {
    return $.ajax({
       type: "GET",
       url: "/v1/phrase/activite",
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        console.log(data);
        success(data);
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