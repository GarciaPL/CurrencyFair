<%@ include file="includes/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='<spring:url value="/resources/css/bootstrap/bootstrap.css" />' rel='stylesheet' type='text/css'/>
    <script type="text/css">
        .form-signin {
            max-width: 350px;
            padding: 15px;
            margin: 0 auto;
        }

        .form-signin .form-signin-heading, .form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin .checkbox {
            font-weight: normal;
        }

        .form-signin .form-control {
            position: relative;
            font-size: 16px;
            height: auto;
            padding: 10px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }

        .form-signin .form-control:focus {
            z-index: 2;
        }

        .form-signin input[type="text"] {
            margin-bottom: -1px;
            border-bottom-left-radius: 0;
            border-bottom-right-radius: 0;
        }

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }

        .account-wall {
            margin-top: 20px;
            padding: 40px 0px 20px 0px;
            background-color: #f7f7f7;
            -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        }

        .login-title {
            color: #555;
            font-size: 18px;
            font-weight: 400;
            display: block;
        }

        .profile-img {
            width: 96px;
            height: 96px;
            margin: 0 auto 10px;
            display: block;
            -moz-border-radius: 50%;
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .need-help {
            margin-top: 10px;
        }

        .new-account {
            display: block;
            margin-top: 10px;
        }
    </script>
    <script type="text/javascript" src='<spring:url value="/resources/js/jquery-1.11.3.min.js" />'
            type='text/javascript'></script>
    <script type="text/javascript" src='<spring:url value="/resources/js/bootstrap/bootstrap.min.js" />'
            type='text/javascript'></script>
</head>
<body>

<div class="container">
    <div class="row" style="margin-top:80px;">
        <div class="col-md-4 col-md-offset-4">
            <h3 class="sign-up-title" style="text-align: center">
                <div class="box"><a href="https://www.currencyfair.com">
                    <img border="0" alt="currencyFairLogo"
                         src='<spring:url value="/resources/images/cf-logo.png" />'></a>
                </div>
            </h3>
            <form method="POST" action="login" accept-charset="UTF-8" role="form" id="loginform"
                  class="form-signin">
                <fieldset>
                    <hr class="colorgraph">
                    <input class="form-control email-title" placeholder="Username" name="username" type="text">

                    <div style="margin-top:5px"></div>
                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="checkbox" style="width:140px;">
                        <label><input name="remember" type="checkbox" value="Remember Me"> Remember Me</label>
                    </div>
                    <p class="text-info">
                        <small>
                            Login : <strong>cf</strong> | Password : <strong>cf</strong></small>
                    </p>
                    <input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign in">

                    <div style="margin-top:15px"></div>
                    <div style="text-align: center; color: red"><c:out value="${message}"/></div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>
