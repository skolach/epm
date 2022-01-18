


<%

	Object attributeContainer;

	// page scope
	attributeContainer = pageContext;
	
	// request scope 					FORWARD
	attributeContainer = request;
	
	// session scope					during session | REDIRECT
	attributeContainer = session;
	
	// application scope				Global config info+
	attributeContainer = application;	

%>