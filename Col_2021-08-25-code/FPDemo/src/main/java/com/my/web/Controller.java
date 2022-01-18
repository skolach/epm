package com.my.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.my.command.*;

@WebServlet("/controller")
public class Controller extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// (1) get commmand name
		String commmandName = req.getParameter("command");
		System.out.println("commmandName ==> " + commmandName);
		
		// (2) get command
		Command command = CommandContainer.getCommmand(commmandName);
		System.out.println("command ==> " + command);
		
		String address = "error.jsp";
		
		// (3) do command
		try {
			address = command.execute(req, resp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// (4) go to address
		req.getRequestDispatcher(address).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
