<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>

	<table border="1">
		<c:forEach items="${requestScope.users}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.login}</td>
			</tr>
		</c:forEach>	
	</table>

</body>
</html>
