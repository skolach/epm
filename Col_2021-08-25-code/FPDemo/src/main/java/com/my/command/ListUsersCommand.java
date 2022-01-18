package com.my.command;

import java.sql.*;
import java.util.*;

import javax.servlet.http.*;

import com.my.db.entity.User;

public class ListUsersCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://10.7.0.9:3307/testdb", "testuser", "testpass");
			System.out.println("con ==> " + con);

			ResultSet rs = con.createStatement()
				.executeQuery("select * from users");

			List<User> users = new ArrayList<>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));

				users.add(user);
			}

			req.setAttribute("users", users);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "listUsers.jsp";
	}

}
