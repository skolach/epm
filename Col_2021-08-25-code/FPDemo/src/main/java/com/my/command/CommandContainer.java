package com.my.command;

import java.util.*;

public class CommandContainer {
	
	private static Map<String, Command> commands;
	
	static {
		commands = new HashMap<>();
		
		commands.put("login", new LoginCommand());
		commands.put("listUsers", new ListUsersCommand());
		// ...
	}

	public static Command getCommmand(String commmandName) {
		return commands.get(commmandName);
	}
	
}
