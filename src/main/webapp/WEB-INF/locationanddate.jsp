<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Knixbilder</title>
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/knixbilder/css/style.css">
</head>
<body>
<p>Välkommen till knixbilder.se</p>

<p>${location}${time}</p>

<c:forEach items="${images}" var="image">
    <div class="image">
        <div class="imagepadding">
            <div class="canvas">
                <a href="${image.url}"><img src=/knixbilder/${image.url}></a>
            </div>
            <div class="caption">
                    16 kr
            </div>
        </div>
    </div>
</c:forEach>

<!-- Visa locations -->

</body>
</html>