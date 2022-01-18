package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import db.dao.UserDao;
import db.entity.User;
import security.PasswordHash;
import utils.AppUtils;

import org.apache.log4j.Logger;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignInServlet.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/signInView.jsp");

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = new User(
                // isAdmin
                false,
                // login
                request.getParameter("new_login"),
                // password
                PasswordHash.hashPassword(request.getParameter("new_password")),
                // name
                request.getParameter("new_name"),
                // discount
                Calendar.getInstance().get(Calendar.SECOND));

        try {
            UserDao.insertUser(user);
        } catch (SQLException e) {
            log.error("Cannot insert new user with given parameters", e);
            AppUtils.storeError(request.getSession(), "User can't be added");
            response.sendRedirect("signIn");
            return;
        }

        response.sendRedirect("login");
    }
}