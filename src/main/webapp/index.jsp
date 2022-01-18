<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fil" tagdir="/WEB-INF/tags"%>

<html>
<head>
<title>Example</title>
</head>
<body>

	${2 * 2} 
	<br>
	${paramValues}
	<br>
	${param.b}
	
	
	
	<p />

	<c:set var="abc" value="15"></c:set>
	<c:out value="${abc }"></c:out>
	
	<c:if test="${abc < 10 }" var="res">
		TRUE
	</c:if>
	<c:out value="${res}" default="default"></c:out>
	<p />

	<c:forEach begin="2" end="10" step="3" var="i">
	${i}
	</c:forEach>
	<p />

	<c:set var="items" value="one;two;three"></c:set>
	<c:forTokens items="${items}" delims=";" var="item">
		<p>${item }
	</c:forTokens>
	<p />

	<fmt:setLocale value="en_UA" />
	<fmt:bundle basename="resources.prop" prefix="main.">
		<fmt:message key="message"></fmt:message>
	</fmt:bundle>


	${sparam}
	<br>

	<fil:mytagfile aname="${param }" />
	
	<fil:mytagfile aname="TEXT_ATTR" />

</body>
</html>
