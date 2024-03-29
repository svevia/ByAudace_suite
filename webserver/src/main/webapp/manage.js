$(document).ready(function(){
	data = getValue(function(nb){
        $('#dureeVie').val(nb.dureeVie);
        $('#nbrPhrases').val(nb.nbrPhrases);
	})
	
	 $("#submit").click(function () {
         vie = $('#dureeVie').val();
         nbr = $('#nbrPhrases').val();
		 postValue(vie, nbr);
		 location.reload();
	 });
})


function getValue(success) {
    return $.ajax({
       type: "GET",
       url: "/v1/phrase/config",
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


function postValue(vie,nbr) {
    return $.ajax({
       type: "POST",
       url: "/v1/phrase/config",
       contentType : 'application/json',
		dataType : "json",
		data : JSON.stringify({
			"dureeVie" : vie,
			"nbrPhrases" : nbr,
		}),
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


function addCategorie(){
	address = '/v1/userdb/newCat/' + $('#categoUsers').val();
	$.ajax({
       type: "GET",
       url: address,
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
			alert('La catégorie "' + $('#categoUsers').val() + '" a bien été créée.');
			location.reload();
       },
       error : function(jqXHR, textStatus, errorThrown) {
       		console.log('error: ' + textStatus);
       	}
     });
	
}


function remCategorie(cat){
	address = '/v1/userdb/delCat/' + cat;
	$.ajax({
       type: "GET",
       url: address,
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
			alert('La catégorie "' + cat + '" a bien été supprimée.');
			location.reload();
       },
       error : function(jqXHR, textStatus, errorThrown) {
       		console.log('error: ' + textStatus);
       	}
     });
	
}