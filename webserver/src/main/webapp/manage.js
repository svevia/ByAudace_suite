$(document).ready(function(){
	data = getValue(function(nb){
        vie = $('#dureeDeVie').val(nb.dureeVie);
        nbr = $('#nbrPhrases').val(nb.nbrPhrases);
	})
	
	 $("#submit").click(function () {
         vie = $('#dureeDeVie').val();
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