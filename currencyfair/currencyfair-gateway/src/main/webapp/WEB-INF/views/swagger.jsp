<%@ include file="includes/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Swagger UI</title>
    <link rel="icon" type="image/png" href='<spring:url value="/resources/images/favicon-32x32.png" />'
          sizes="32x32"/>
    <link rel="icon" type="image/png" href='<spring:url value="/resources/images/favicon-16x16.png" />'
          sizes="16x16"/>
    <link href='<spring:url value="/resources/css/typography.css" />' media='screen' rel='stylesheet' type='text/css'/>
    <link href='<spring:url value="/resources/css/reset.css" />' media='screen' rel='stylesheet' type='text/css'/>
    <link href='<spring:url value="/resources/css/screen.css" />' media='screen' rel='stylesheet' type='text/css'/>
    <link href='<spring:url value="/resources/css/reset.css" />' media='print' rel='stylesheet' type='text/css'/>
    <link href='<spring:url value="/resources/css/print.css" />' media='print' rel='stylesheet' type='text/css'/>
    <script src='<spring:url value="/resources/js/jquery-1.11.3.min.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/jquery.slideto.min.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/jquery.wiggle.min.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/jquery.ba-bbq.min.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/handlebars-2.0.0.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/underscore-min.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/backbone-min.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/swagger-ui.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/highlight.7.3.pack.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/marked.js" />' type='text/javascript'></script>
    <script src='<spring:url value="/resources/js/swagger/swagger-oauth.js" />' type='text/javascript'></script>

    <script type="text/javascript">
        $(function () {
            var apiUrl = window.location.protocol + "//" + window.location.host + "/";
            apiUrl += window.location.pathname.split("/")[1];
            apiUrl += "/api-docs/";
            if (apiUrl && apiUrl.length > 1) {
                apiUrl = decodeURIComponent(apiUrl);
            } else {
                apiUrl = "http://petstore.swagger.io/v2/swagger.json";
            }
            window.swaggerUi = new SwaggerUi({
                url: apiUrl,
                dom_id: "swagger-ui-container",
                supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
                onComplete: function (swaggerApi, swaggerUi) {
                    if (typeof initOAuth == "function") {
                        initOAuth({
                            clientId: "your-client-id",
                            realm: "your-realms",
                            appName: "your-app-name"
                        });
                    }

                    $('pre code').each(function (i, e) {
                        hljs.highlightBlock(e)
                    });

                    addApiKeyAuthorization();
                },
                onFailure: function (data) {
                    log("Unable to Load SwaggerUI");
                },
                docExpansion: "none",
                apisSorter: "alpha"
            });

            function addApiKeyAuthorization() {
                var key = encodeURIComponent($('#input_apiKey')[0].value);
                if (key && key.trim() != "") {
                    var apiKeyAuth = new SwaggerClient.ApiKeyAuthorization("api_key", key, "query");
                    window.swaggerUi.api.clientAuthorizations.add("api_key", apiKeyAuth);
                    log("added key " + key);
                }
            }

            $('#input_apiKey').change(addApiKeyAuthorization);

            // if you have an apiKey you would like to pre-populate on the page for demonstration purposes...
            /*
             var apiKey = "myApiKeyXXXX123456789";
             $('#input_apiKey').val(apiKey);
             */

            window.swaggerUi.load();

            function log() {
                if ('console' in window) {
                    console.log.apply(console, arguments);
                }
            }
        });
    </script>
</head>

<body class="swagger-section">
<sec:authorize access="isAuthenticated()">
    <div id='header'>
        <div class="swagger-ui-wrap">
            <a id="logo" href="http://swagger.io">swagger</a>

            <form id='api_selector'>
                <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl"
                                          type="text"/></div>
                <div class='input'><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/></div>
                <div class='input'><a id="explore" href="#">Explore</a></div>
            </form>
        </div>
    </div>

    <div id="message-bar" class="swagger-ui-wrap">&nbsp;</div>
    <div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</sec:authorize>
</body>
</html>
