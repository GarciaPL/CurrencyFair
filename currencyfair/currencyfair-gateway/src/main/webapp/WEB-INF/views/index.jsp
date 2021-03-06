<%@ include file="includes/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>CurrencyFair</title>
    <style type="text/css">
        .boxer {
            width: 300px;
            height: 300px;
            display: inline-block;
            vertical-align: middle;
            text-align: center;
            display: table;
            border-collapse: collapse;
        }

        .boxer .box-row {
            margin-top: 20px;
            display: table-row;
        }

        .boxer .box {
            display: table-cell;
            text-align: center;
            vertical-align: middle;
        }

        .btn {
            background: #3498db;
            background-image: -webkit-linear-gradient(top, #3498db, #2980b9);
            background-image: -moz-linear-gradient(top, #3498db, #2980b9);
            background-image: -ms-linear-gradient(top, #3498db, #2980b9);
            background-image: -o-linear-gradient(top, #3498db, #2980b9);
            background-image: linear-gradient(to bottom, #3498db, #2980b9);
            -webkit-border-radius: 28;
            -moz-border-radius: 28;
            border-radius: 28px;
            font-family: Arial;
            color: #ffffff;
            font-size: 20px;
            padding: 10px 20px 10px 20px;
            text-decoration: none;
        }

        .btn:hover {
            background: #3cb0fd;
            background-image: -webkit-linear-gradient(top, #3cb0fd, #3498db);
            background-image: -moz-linear-gradient(top, #3cb0fd, #3498db);
            background-image: -ms-linear-gradient(top, #3cb0fd, #3498db);
            background-image: -o-linear-gradient(top, #3cb0fd, #3498db);
            background-image: linear-gradient(to bottom, #3cb0fd, #3498db);
            text-decoration: none;
        }

        body {
            margin-top: 15%;
        }
    </style>
</head>
<body>
<div align="center">
    <sec:authorize access="isAuthenticated()">
        <div class="boxer">
            <div class="box-row">
                <div class="box"><a href="https://www.currencyfair.com"><img border="0" alt="currencyFair"
                                                                             src='<spring:url value="/resources/images/cf-logo.png" />'></a>
                </div>
            </div>
            <div class="box-row">
                <div class="box"><a class="btn" href="swagger">Swagger Docs</a></div>
            </div>
            <div class="box-row">
                <div class="box"><a class="btn" href="http://localhost:15672" target="_blank">RabbitMQ</a></div>
            </div>
            <div class="box-row">
                <div class="box"><a class="btn" href="https://github.com/GarciaPL/CurrencyFair"
                                    target="_blank">GitHub</a></div>
            </div>
        </div>
    </sec:authorize>
</div>

</body>
</html>