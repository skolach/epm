<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lng" value="${not empty param.lng ? param.lng : not empty lng ? lng : 'uk_UA'}"
    scope="session" />
<fmt:setLocale value="${lng}" />
<fmt:setBundle basename="resources.text" />

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <style>
        /* Remove the navbar's default margin-bottom and rounded borders */
        .navbar {
            margin-bottom: 0;
            border-radius: 0;
        }

        /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
        .row.content {
            height: 450px
        }

        /* Set gray background color and 100% height */
        .sidenav {
            padding-top: 20px;
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }

            .row.content {
                height: auto;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <c:if test="${not empty loginedUser}">
                        <li>
                            <a href="${pageContext.request.contextPath}/customerTask">
                                <fmt:message key="customer.task" />
                            </a>
                        </li>
                        <c:if test="${loginedUser.isAdmin}">
                            <li>
                                <a href="${pageContext.request.contextPath}/adminTask">
                                    <fmt:message key="admin.task" />
                                </a>
                            </li>
                        </c:if>
                    </c:if>

                    <c:if test="${empty loginedUser}">
                        <li>
                            <a href="${pageContext.request.contextPath}/login">
                                <fmt:message key="user.login" />
                            </a>
                        </li>

                        <li>
                            <a href="${pageContext.request.contextPath}/signIn">
                                <fmt:message key="user.signIn" />
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${not empty loginedUser}">
                        <li>
                            <a href="${pageContext.request.contextPath}/logout">
                                <fmt:message key="user.logOut" />
                                ( ${loginedUser.login} )
                            </a>
                        </li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <form>
                            <select name="lng" id="selectLanguage" onchange="submit();">
                                <option value="en_GB" ${lng=='en_GB' ? 'selected' : '' }>EN</option>
                                <option value="uk_UA" ${lng=='uk_UA' ? 'selected' : '' }>UA</option>
                            </select>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    </body>