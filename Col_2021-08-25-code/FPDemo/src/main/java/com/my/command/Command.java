package com.my.command;

import javax.servlet.http.*;

public interface Command {
	
	String execute(HttpServletRequest req, HttpServletResponse resp);

}
