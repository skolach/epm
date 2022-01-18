package com.my.command;

import javax.servlet.http.*;

public class LoginCommand implements Command {
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		// get login, password
		// obtain from DB user by login
		// check password
	
		return "controller?command=listUsers";
	}

}
