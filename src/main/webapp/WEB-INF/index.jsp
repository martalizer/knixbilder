<%--
  Created by IntelliJ IDEA.
  User: hedenberg
  Date: 17/01/15
  Time: 23:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Knixbilder</title>


    <!-- Bootstrap -->
    <link href="/knixbilder/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/knixbilder/cover.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<div class="site-wrapper">

    <div class="site-wrapper-inner">
        <div class="top"><h1>Välkommen till knixbilder.se</h1></div>
        <div class="cover-container">

            <p>${location}</p>

            <c:forEach items="${folders}" var="folder">
                <p><a href="${folder.absoluteFile}">${folder.file}</a></p>
            </c:forEach>

            <c:forEach items="${images}" var="image">
                <div class="image">
                    <div class="imagepadding">
                        <div class="canvas">
                            <img src="${image.absoluteFile}" width="200px">
                        </div>
                    </div>
                </div>
            </c:forEach>

            <div class="mastfoot">
                <div class="inner">
                    <p><a href="http://localhost:8080/knixbilder/upload.html">Upload Page</a></p>
                    <p>knixbilder.se</p>
                </div>
            </div>

        </div>

    </div>

</div>

<!-- Bootstrap core JavaScript================ -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="/knixbilder/dist/js/bootstrap.min.js"></script>
<script src="/knixbilder/dist/js/docs.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/knixbilder/dist/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
