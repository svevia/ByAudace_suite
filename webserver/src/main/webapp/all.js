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

function postUserBdd(mail,nom,prenom,digit,pass,role,tel) {
    postUserGeneric(mail,nom,prenom,digit,pass,role,tel, "v1/userdb/");
}

function postUserGeneric(mail,nom,prenom,digit,pass,role,tel,url) {
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
			"role" : role,
			"numero" : tel
		}),
    beforeSend : function(req) {
      req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
    },
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

function getTaux(url) {
     $.ajax({
       type: "GET",
       url: url,
       dataType: 'json',
       beforeSend : function(req) {
        req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
       },
       success: function (data){
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);
       	}
     });
}

function getPhrase(url) {
     $.ajax({
       type: "GET",
       url: url,
       dataType: 'json',
       beforeSend : function(req) {
        req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
       },
       success: function (data){
        console.log(data);
        affichePhrase(data);
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);
       	}
     });
}

function affichePhrase(data) {
  	for(var i=0; i< data.length; i++){
        $("#itphrase_"+i).html(data[i].phrase);
        $("#itbesoin_"+i).html(data[i].besoin);
        document.getElementById("link_"+i).href = "/html/phrase/"+data[i].phrase;
        document.getElementById("button_"+i).setAttribute('name', data[i].phrase);

    }
}

function getSearch(url) {
     $.ajax({
       type: "GET",
       url: url,
       dataType: 'json',
       beforeSend : function(req) {
        req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
       },
       success: function (data){
        affichePhrase(data);
       },
       error : function(jqXHR, textStatus, errorThrown) {
            console.log('error: ' + textStatus);
        }
     });
}
