function getUserBdd(name) {
	getUserGeneric(name, "v1/userdb/");
}

function getUserGeneric(name, url) {
	$.getJSON(url + name, function(data) {
		afficheUser(data);
	});
}

function getByAnnotation() {
	getSecure("v1/secure/byannotation");
}

 function getSecure(url) {
 if($("#userlogin").val() != "") {
     $.ajax
     ({
       type: "GET",
       url: url,
       dataType: 'json',
       beforeSend : function(req) {
        req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
       },
       success: function (data){
        afficheUser(data)
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			alert('error: ' + textStatus);
       		}
     });
     } else {
     $.getJSON(url, function(data) {
     	    afficheUser(data);
        });
     }
 }

function postUserBdd(mail,pass,nom,prenom,tel,digit) {
    postUserGeneric(mail,pass,nom,prenom,tel,digit, "v1/userdb/");
}

function postUserGeneric(mail,pass,nom,prenom,tel,digit,url) {
	console.log(url);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : "json",
		data : JSON.stringify({
			"mail" : mail,
			"nom" : nom,
			"prenom" : prenom,
			"digit" : digit,
			"mot_de_passe" : pass,
			"role" : "user"
		}),
		success : function(data, textStatus, jqXHR) {
			afficheUser(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}

function listUsersGeneric(url) {
	$.getJSON(url, function(data) {
		afficheListUsers(data)
	});
}

function afficheUser(data) {
	console.log(data);
	$("#reponse").html(data.id + " : <b>" + data.alias + "</b> (" + data.name + ")");
	window.location.replace("/html/userdb");
}

function afficheListUsers(data) {
	var html = '<ul>';
	var index = 0;
	for (index = 0; index < data.length; ++index) {
		html = html + "<li>"+ data[index].name + "</li>";
	}
	html = html + "</ul>";
	$("#reponse").html(html);
}

 	function deletePhrase(url,id) {
 	if (id != "") {
     	$.ajax
     	({
	       type: "DELETE",
	       url: url+id,
	       dataType: 'json',
	       beforeSend : function(req) {
	       req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
	       },
	       success: function (data){
	       console.log("delete" + id);
	       },
	       error : function(jqXHR, textStatus, errorThrown) {
	       alert('error: ' + textStatus);
	       }
   	    });
 	}}
