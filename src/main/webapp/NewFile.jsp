<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/mytaglib.tld" prefix="my"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%!int x = 10;%>
	<%
	String s = "abc";
	for (int i = 0; i < 5; i++) {
		s += i;
	}
	%>
	<%="3" + s + "   " + x%>

	${"<tag>  &" }




	<form action="Serv03" method="post">
		Name <input type="text" name="name"> <input type="submit">
	</form>

	<p>
		Session:
		<%=session.getAttribute("sparam")%>
		<br>
		<%=pageContext.getSession().getAttribute("sparam")%>
	<p>

		<c:set var="i" value="10000"></c:set>

		<c:if test="${i < 100 }">

			<c:out value="${i}"></c:out>

		</c:if>

		<c:remove var="i" />
		<c:out value="${i}" default="no var"></c:out>


		<c:forTokens items="A BC ZXC" delims=" " var="s">
		${s }<br>
		</c:forTokens>

		<jsp:useBean id="set" class="web_jstl.MySet"></jsp:useBean>
		<c:forEach items="${set}" var="i">
		${i } <br>
		</c:forEach>

		<jsp:useBean id="bean" class="web_jstl.MyBean"></jsp:useBean>
		<jsp:setProperty property="name" name="bean" value="VALUE" />

		<c:out value="${bean.name}">
		</c:out>

		${bean.name} <br> ${sparam} ${sessionScope.sparam}
	<p />

	<my:bodyattr num="${set.size }">
	${set.element }
	</my:bodyattr>

	<my:bodyattr>
	Any text
	</my:bodyattr>


</body>
</html>