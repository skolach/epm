<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>

	<table border="1">
	<%
		Map<Integer, Integer> table = (Map)request.getAttribute("table");
		System.out.println("table ==> " + table);
		for (Map.Entry<Integer, Integer> entry : table.entrySet()) {
	%>
		<tr>
			<td><%=entry.getKey() %></td>
			<td><%=entry.getValue() %></td>
		</tr>
	<%
		}
	%>
	</table>
	
	<hr>
	 
	<table border="1">
		<c:forEach items="${table}" var="entry">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
			</tr>	
		</c:forEach>
	</table>
	 
</body>
</html>