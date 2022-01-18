<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${language}">

<head>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.1/css/all.css" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Error</title>
</head>

<body>
    <jsp:include page="_menu.jsp"></jsp:include>

    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="resources.text" />

    <div class="container-fluid text-center">
        <div class="row content">
            <div class="col-sm-6 text-center">
                <span>
                    <h1>Something went wrong</h1>
                </span>
                <span>
                    <jsp:include page="_error.jsp"></jsp:include>
                </span>
            </div>
        </div>
    </div>
</body>
</html>