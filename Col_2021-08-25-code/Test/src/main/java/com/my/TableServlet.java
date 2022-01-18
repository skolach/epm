package com.my;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// http://localhost:8080/Test/table?n=5


@WebServlet("/table")
public class TableServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// (1) obtain an input info
		String nAsString = req.getParameter("n");
		int n = Integer.parseInt(nAsString);
		
		System.out.println("n ==> " + n);
		
		// (2) generate a result
		Map<Integer, Integer> table = new HashMap<>();
		for (int j = 1; j <= n; j++) {
			table.put(j, j * j);
		}
		
		// (3) save a result as an attribute in container
		req.setAttribute("table", table);
		
		// (4) go to a view layer
		req.getRequestDispatcher("table.jsp").forward(req, resp);

	}	
	
}