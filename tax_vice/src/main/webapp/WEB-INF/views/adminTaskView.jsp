<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${language}">

<head>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.1/css/all.css"
        crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Admin Task</title>
</head>

<body>
    <jsp:include page="_menu.jsp"></jsp:include>
    <jsp:include page="_error.jsp"></jsp:include>

    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="resources.text" />

    <div class="container-fluid text-center">
        <div class="row content">
            <div class="col-sm-2 sidenav">


            </div>

            <div class="col-sm-8 text-left">
                <div id="history" class="panel-group">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3>Orders History</h3>
                        </div>
                        <div class="panel-body">
                            <form>
                                <table class="table">
                                    <tr>
                                        <th></th>
                                        <th></th>
                                        <th>
                                            <input id="filterStartAt" type="datetime-local"
                                                name="filterBeginStartAt" value="${filterBeginStartAt}"
                                                onchange="submit();">
                                            /
                                            <input id="filterStartAt" type="datetime-local"
                                                name="filterEndStartAt" value="${filterEndStartAt}"
                                                onchange="submit();">
                                        </th>
                                        <th>
                                            <input type="text" name="filterUserLogin"
                                                value="${filterUserLogin}" onchange="submit();">
                                        </th>
                                    </tr>

                                    <tr>
                                        <c:if test="${not empty adminSort}">
                                            <th style="width: 40%">
                                                <c:choose>
                                                    <c:when test="${adminSort.fieldName == 'routes'}">
                                                        <button name="adminSort" value="routes"
                                                            onclick="submit();">
                                                            route ${adminSort.chCode}
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button name="adminSort" value="routes"
                                                            onclick="submit();">
                                                            route &#x25b4 &#x25be
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                            <th style="width: 20%">
                                                <c:choose>
                                                    <c:when test="${adminSort.fieldName == 'price'}">
                                                        <button name="adminSort" value="price"
                                                            onclick="submit();">
                                                            price ${adminSort.chCode}
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button name="adminSort" value="price"
                                                            onclick="submit();">
                                                            price &#x25b4 &#x25be
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                            <th style="width: 20%">
                                                <c:choose>
                                                    <c:when test="${adminSort.fieldName == 'start_at'}">
                                                        <button name="adminSort" value="start_at"
                                                            onclick="submit();">
                                                            start at ${adminSort.chCode}
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button name="adminSort" value="start_at"
                                                            onclick="submit();">
                                                            start at &#x25b4 &#x25be
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                            <th style="width: 20%">
                                                <c:choose>
                                                    <c:when test="${adminSort.fieldName == 'login'}">
                                                        <button name="adminSort" value="login"
                                                            onclick="submit();">
                                                            customer ${adminSort.chCode}
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button name="adminSort" value="login"
                                                            onclick="submit();">
                                                            customer &#x25b4 &#x25be
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                        </c:if>
                                        <c:if test="${empty adminSort}">
                                            <th>
                                                <button name="adminSort" value="routes" onclick="submit();">
                                                    route <em class="fa fa-sort"></em>
                                                </button>
                                            </th>
                                            <th>
                                                <button name="adminSort" value="price" onclick="submit();">
                                                    price <em class="fa fa-sort"></em>
                                                </button>
                                            </th>
                                            <th>
                                                <button name="adminSort" value="start_at"
                                                    onclick="submit();">
                                                    started at <em class="fa fa-sort"></em>
                                                </button>
                                            </th>
                                            <th>
                                                <button name="adminSort" value="login" onclick="submit();">
                                                    customer <em class="fa fa-sort"></em>
                                                </button>
                                            </th>
                                        </c:if>
                                        <th id="btn"></th>
                                    </tr>
                                    <c:forEach items="${adminOrders}" var="o">
                                        <tr>
                                            <td>${o.routes}</td>
                                            <td>${o.price}</td>
                                            <td>${o.startAt}</td>
                                            <td>${o.login}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <c:if test="${ordersByAdminPagesCount > 0}">
                                    <c:forEach var="i" begin="0" end="${ordersByAdminPagesCount - 1}"
                                        step="1">
                                        <button name="ordersByAdminPageNo" value="${i}"
                                            onclick="submit();">${i
                                            +
                                            1}</button>
                                    </c:forEach>
                                </c:if>
                            </form>
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