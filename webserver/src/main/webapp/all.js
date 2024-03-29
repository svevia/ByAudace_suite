function getUserBdd(name) {
	getUserGeneric(name, "v1/userdb/");
}

function getUserGeneric(name, url) {
	$.getJSON(url + name, function(data) {
		afficheUser(data);
	});
}

function getCookie(name) {
	  var value = "; " + document.cookie;
	  var parts = value.split("; " + name + "=");
	  if (parts.length == 2) return parts.pop().split(";").shift();
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
        req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        afficheUser(data)
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			alert('error: ' + textStatus + '\n' + errorThrown);
       		}
     });
     } else {
     $.getJSON(url, function(data) {
     	    afficheUser(data);
        });
     }
 }

function postUser(mail,name,prenom,categorie,role,tel) {
	url = "/v1/userdb/";
	if(role == "admin"){
		url += "admin/";
	}
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : "json",
		data : JSON.stringify({
			"mail" : mail,
			"name" : name,
			"prenom" : prenom,
			"categorie" : categorie,
			"role" : role,
			"numero" : tel
		}),
    beforeSend : function(req) {
    	req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
    	},
	success : function(data, textStatus, jqXHR) {
			if(data == null){
				alert("email dejà utilisé");
			}
			afficheUser(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
				alert('error: ' + errorThrown);
		}
	});
}


function postUserBeta(mail,name,prenom,categorie,role,tel) {
	if(mail.indexOf('@') == -1){
		alert("email incorrect !");
		return;
	}
	url = "/v1/userdb/beta/";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : "json",
		data : JSON.stringify({
			"mail" : mail,
			"name" : name,
			"prenom" : prenom,
			"categorie" : categorie,
			"role" : role,
			"numero" : tel
		}),
	success : function(data, textStatus, jqXHR) {
			if(data == null){
				alert("email dejà utilisé");
			}
			else{
				window.alert("Un mail avec votre mot de passe vous a été envoyé\n Merci de participer à la beta ByAudace !");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
				alert('error: ' + errorThrown);
		}
	});
}



function editUser(mail,name,prenom,categorie,pass,tel,role) {
	url = "/v1/userdb/edit/";
	if(role == "admin" || role == "root"){
		url += "admin/";
	}
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : "json",
		data : JSON.stringify({
			"mail" : mail,
			"name" : name,
			"prenom" : prenom,
			"categorie" : categorie,
			"mot_de_passe" : pass,
			"numero" : tel,
			"role" : role,
			"id" : id
		}),
    beforeSend : function(req) {
    	req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
    	},
		success : function(data, textStatus, jqXHR) {
			if(data == null){
				alert("email dejà utilisé");
			}
			afficheList(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
				alert('error: ' + errorThrown);
		}
	});
}

function deleteUser(id,role){
	url = "/v1/userdb/";
	if(role == "admin"){
		url += "admin/";
	}
 	$.ajax({
        type: "DELETE",
        url: url + id,
        beforeSend : function(req) {
      	  req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
 		  },
        success: function(msg){
          location.reload();
        },
        error: function(xhr, status, errorThrown){
          console.log("Error: " + errorThrown);
          console.log("Status: " + status);
          alert('error: ' + errorThrown);
        },
        });
}

function listUsersGeneric(url) {
	$.getJSON(url, function(data) {
		afficheListUsers(data)
	});
}

function afficheUser(data) {
	window.location.replace("/html/insert");
}

function afficheList(data) {
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
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			console.log('error: ' + textStatus);
       	}
     });
}

function getPhrase(url) {
	  $("td").show();
     $.ajax({
       type: "GET",
       url: url,
       dataType: 'json',
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
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
        $("#signalement_"+i).html("signalement : " + data[i].signalement);
        document.getElementById("link_"+i).href = "/html/phrase/"+data[i].id;
        document.getElementById("button_"+i).setAttribute('name', data[i].id);

    }
}

function getSearch(url) {

     $.ajax({
       type: "GET",
       url: url,
       dataType: 'json',
       beforeSend : function(req) {
           req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
       },
       success: function (data){
        afficheSearch(data);
       },
       error : function(jqXHR, textStatus, errorThrown) {
        console.log('error: ' + textStatus);
        }
     });
}

function getSearchUser(url) {

    $.ajax({
      type: "GET",
      url: url,
      dataType: 'json',
      beforeSend : function(req) {
          req.setRequestHeader("Authorization", "Basic " + btoa(getCookie("user")));
      },
      success: function (data){
       afficheSearchUser(data);
      },
      error : function(jqXHR, textStatus, errorThrown) {
       console.log('error: ' + textStatus);
       }
    });
}

function afficheSearchUser(data) {
	  var rows = document.getElementById('table').getElementsByTagName('tr');
	  var count = rows.length;

	  $("tr").show();
	  for(j=0; j<count; j++){
	    if(j<data.length){
	      $("#linkMail_"+j).html(data[j].mail);
	      document.getElementById("linkMail_"+j).href = "/html/userdb/"+data[j].id;
	      document.getElementById("b_"+j).setAttribute('name', data[j].id);
	    }
	    else{
	      $("#user_"+j).hide();
	    }
	  }
}


function afficheSearch(data) {
  var rows = document.getElementById('table').getElementsByTagName('tr');
  var count = (rows.length)/2;

  $("td").show();
  for(j=0; j<count; j++){
    if(j<data.length){
      $("#itphrase_"+j).html(data[j].phrase);
      $("#itbesoin_"+j).html(data[j].besoin);
      $("#signalement_"+j).html("signalement : " + data[j].signalement);
      document.getElementById("link_"+j).href = "/html/phrase/"+data[j].id;
      document.getElementById("button_"+j).setAttribute('name', data[j].id);
    }
    else{
      $("#itphrase_"+j).hide();
      $("#itbesoin_"+j).hide();
      $("#signalement_"+j).hide();
      $("#td2_"+j).hide();
      $("#td4_"+j).hide();
    }
  }
}