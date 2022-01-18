<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${language}">

<head>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.1/css/all.css"
        crossorigin="anonymous">
    <script>
        function addNewPoint() {
            var tbl = document.getElementById("tblPoints");
            var newRow = tbl.rows.item(0).cloneNode(true);
            newRow.cells[0].children[0].value = "";
            newRow.cells[0].children[0].placeholder = "EndPoint";
            tbl.appendChild(newRow);
        }
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
    <jsp:include page="_menu.jsp"></jsp:include>
    <jsp:include page="_error.jsp"></jsp:include>

    <fmt:setLocale value="${lng}" />
    <fmt:setBundle basename="resources.text" />

    <title>
        <fmt:message key="customer.task" />
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
                                <fmt:message key="customer.makeOrder" />
                            </h3>
                        </div>
                        <div class="panel-body">
                            <form method="POST" action="customerTask" accept-charset="utf-8">
                                <div class="col-sm-4">
                                    <label for="qty">
                                        <fmt:message key="customer.passengers" />:
                                    </label>
                                    <input class="form-control" type="number" name="qty" id="qty" pattern="^[0-9]{1,2}$" value="1"
                                        min="1" max="99">
                                </div>
                                <br>
                                <c:set var="theFirst" value="true" />
                                <c:forEach items="${carClasses}" var="cc">
                                    <c:choose>
                                        <c:when test="${theFirst == 'true'}">
                                            <input type="radio" name="carClass" value="${cc.id}"
                                                title="${cc.description}" checked="true">
                                            <c:set var="theFirst" value="false" />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="radio" name="carClass" value="${cc.id}"
                                                title="${cc.description}">
                                        </c:otherwise>
                                    </c:choose>
                                    <label for="${cc.id}">
                                        <c:out value="${cc.name}" />
                                    </label>
                                </c:forEach>
                                <br>
                                <c:set var="theFirst" value="true" />
                                <c:forEach items="${features}" var="feature">
                                    <c:choose>
                                        <c:when test="${theFirst == 'true'}">
                                            <input type="checkbox" name="feature" value="${feature.id}"
                                                title="${feature.featureDescriptionLong}" checked="true">
                                            <c:set var="theFirst" value="false" />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="feature" value="${feature.id}"
                                                title="${feature.featureDescriptionLong}">
                                        </c:otherwise>
                                    </c:choose>
                                    <label for="${feature.id}">
                                        <c:out value="${feature.featureDescription}" />
                                    </label>
                                </c:forEach>

                                <table id="tblPoints" class="table">
                                    <tr>
                                        <fmt:message key="customer.startPoint" var="tmpVar" />
                                        <td><input class="form-control" type="text" name="pointName"
                                                maxlength="10" placeholder="${tmpVar}">
                                        </td>
                                    </tr>
                                    <br>
                                    <tr>
                                        <fmt:message key="customer.endPoint" var="tmpVar" />
                                        <td><input class="form-control" type="text" name="pointName"
                                                maxlength="10" placeholder="${tmpVar}">
                                        </td>
                                    </tr>
                                </table>
                                <button class="btn btn-success" type="button" id="btnAdd" value="Add"
                                    onclick="addNewPoint();">
                                    <fmt:message key="customer.addPoint" />
                                </button>
                                <button class="btn btn-primary" type="submit" value="Submit" id="submit">
                                    <fmt:message key="customer.submit" />
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3>
                            <fmt:message key="customer.ordersHistory"/>
                        </h3>
                    </div>
                    <div class="panel-body">

                        <table class="table">
                            <tr>
                                <form>
                                    <c:if test="${not empty customerSort}">
                                        <th style="width: 40%">
                                            <c:choose>
                                                <c:when test="${customerSort.fieldName == 'routes'}">
                                                    <button class="btn" name="customerSort" value="routes"
                                                        onclick="submit();">
                                                        <fmt:message key="customer.route" />
                                                        ${customerSort.chCode}
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn" name="customerSort" value="routes"
                                                        onclick="submit();">
                                                        <fmt:message key="customer.route" /> &#x25b4 &#x25be
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                        <th style="width: 20%">
                                            <c:choose>
                                                <c:when test="${customerSort.fieldName == 'price'}">
                                                    <button class="btn" name="customerSort" value="price"
                                                        onclick="submit();">
                                                        <fmt:message key="customer.price" />
                                                        ${customerSort.chCode}
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn" name="customerSort" value="price"
                                                        onclick="submit();">
                                                        <fmt:message key="customer.price" /> &#x25b4 &#x25be
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                        <th style="width: 30%">
                                            <c:choose>
                                                <c:when test="${customerSort.fieldName == 'start_at'}">
                                                    <button class="btn" name="customerSort" value="start_at"
                                                        onclick="submit();">
                                                        <fmt:message key="customer.startedAt" />
                                                        ${customerSort.chCode}
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn" name="customerSort" value="start_at"
                                                        onclick="submit();">
                                                        <fmt:message key="customer.startedAt" /> &#x25b4 &#x25be
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                    </c:if>
                                    <c:if test="${empty customerSort}">
                                        <th>
                                            <button class="btn" name="customerSort" value="routes" onclick="submit();">
                                                <fmt:message key="customer.route" /> <em
                                                    class="fa fa-sort"></em>
                                            </button>
                                        </th>
                                        <th>
                                            <button class="btn" name="customerSort" value="price" onclick="submit();">
                                                <fmt:message key="customer.price" /> <em
                                                    class="fa fa-sort"></em>
                                            </button>
                                        </th>
                                        <th>
                                            <button class="btn" name="customerSort" value="start_at" onclick="submit();">
                                                <fmt:message key="customer.startedAt" /> <em
                                                    class="fa fa-sort"></em>
                                            </button>
                                        </th>
                                    </c:if>
                                </form>
                            </tr>
                            <c:forEach items="${orders}" var="o">
                                <tr>
                                    <td>${o.routes}</td>
                                    <td>${o.price}</td>
                                    <td>${o.startAt}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <form>
                        <c:if test="${ordersByUserPagesCount > 0}">
                            <c:forEach var="i" begin="0" end="${ordersByUserPagesCount - 1}" step="1">
                                <button class="btn" name="ordersByUserPageNo" value="${i}"
                                    onclick="submit();">${i + 1}
                                </button>
                            </c:forEach>
                        </c:if>
                    </form>
                </div>
            </div>
            <div class="col-sm-2 sidenav">


            </div>
        </div>
    </div>
</body>

</html>