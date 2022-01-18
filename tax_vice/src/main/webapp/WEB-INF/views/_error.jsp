<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>
    <c:if test="${sessionScope.errorMessage != null}">
        <br>
        <span style="color:red">
            <c:out value="${sessionScope.errorMessage}" />
            <c:remove var="errorMessage" scope="session" />
        </span>
        <br>
    </c:if>
    <c:if test="${sessionScope.infoMessage != null}">
        <br>
        <span style="color:green">
            <c:out value="${sessionScope.infoMessage}" />
            <c:remove var="infoMessage" scope="session" />
        </span>
        <br>
    </c:if>