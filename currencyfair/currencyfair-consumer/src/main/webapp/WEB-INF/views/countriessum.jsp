<%@ include file="includes/taglib.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-us" lang="en-us">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link href='<spring:url value="/resources/css/bootstrap.css" />' rel='stylesheet' type='text/css'/>
    <title>Countries Sum Graph</title>
    <script type="text/javascript" src='<spring:url value="/resources/js/jquery-1.11.3.min.js" />'></script>
    <script type="text/javascript" src='<spring:url value="/resources/js/bootstrap/bootstrap.min.js" />'></script>
      <script>
          function goBack() {
              window.history.back();
          }
      </script>
  </head>
  <body>
  <div class="container">
      <h1 class="page-header text-center">Countries Sum Graph</h1>
        <div id="countriesChart"></div>
      <div style="margin-top:30px"></div>
      <p class="text-info text-center">
          <small>
              <strong>Graph presents summarize volume of processed financial data for appropriate countries</strong>
          </small>
      </p>
      <div style="margin-top:30px"></div>
        <div class="row text-center">
            <a onclick="goBack()" class="btn btn-primary btn-lg text-center">
                Back
            </a>
        </div>
    </div>
  </body>
  <script type="text/javascript" src='<spring:url value="/resources/js/websocket/sockjs.js" />'></script>
  <script type="text/javascript" src='<spring:url value="/resources/js/highcharts/highcharts.js" />'></script>
  <script type="text/javascript" src='<spring:url value="/resources/js/websocket/stomp.js" />'></script>
  <script type="text/javascript" src='<spring:url value="/resources/js/websocket/countriesSum.js" />'></script>

</html>