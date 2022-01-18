<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${language}">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
    <jsp:include page="_menu.jsp"></jsp:include>
    <jsp:include page="_error.jsp"></jsp:include>

    <fmt:setLocale value="${lng}" />
    <fmt:setBundle basename="resources.text" />

    <title>
        <fmt:message key="home.appName" />
    </title>

    <div class="container-fluid text-center">
        <div class="row content">
            <div class="col-sm-2 sidenav">
            </div>
            <div class="col-sm-8 text-center">
                <div class="panel-group">

                    <div class="panel panel-primary">

                        <div class="panel-heading">
                            <h3>
                                <fmt:message key="home.toStart" />
                            </h3>
                        </div>
                        <div class="panel-body">
                            <h2>
                                <a href="${pageContext.request.contextPath}/login">
                                    <fmt:message key="home.login" />
                                </a>
                            </h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2 sidenav">
            </div>
        </div>
    </div>
</body>

</html>