<head>
<link rel="stylesheet" type="text/css" href="style.css">
 <script src="/all.js"></script>
       <jsp:include page="/layout/head.jsp"/>
</head>

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Sign in to continue</h1>
            <div class="account-wall">
                <img class="profile-img" src="/ByAudace1.jpg"
                    alt="">
                <input id="mail" type="text" class="form-control" placeholder="Email" required autofocus>
                <input id = "password" type="password" class="form-control" placeholder="Password" required>
                <button id = "submit" class="btn btn-lg btn-primary btn-block" >Sign in</button>
            </div>
        </div>
    </div>
</div>

  <script type="text/javascript">
        $(document).ready(function() {
            $("#submit").click(function () {
                mail = $('#mail').val();
                pass = $('#password').val();
                console.log(mail + " - " + pass);
                log(mail,pass)});
        });
        
      </script>