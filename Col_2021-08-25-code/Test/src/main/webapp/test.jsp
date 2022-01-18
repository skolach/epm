<%@ page import="java.io.*,java.util.*" %>
<%-- <%@ taglib  %> --%>

<%-- <%@ include %> --%>

<%-- <jsp: --%>

<html>
<body>

	Hi!
	
	<hr>
	
	<%-- scriplet --%>
	<%
		int x = 7;	
		System.out.println(x);
		
		out.println(m());
	%>
	
	<hr>
	
	<%-- expression --%>
	<%=new java.util.Date() %>	
	
	<%-- declaration --%>
	<%!
		int y = 7;
		private int z = 8;	
		int x = m();
		
		int m() {
			System.out.println("m()");
			return 1000;	
		}
	%>
	

</body>
</html>