package servlet;

import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(LoginServlet.class);

    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher
            = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        String userName = request.getParameter("userName");
        String hashedPassword = PasswordHash.hashPassword(request.getParameter("password"));
        User user = null;
        try {
            user = UserDao.findUserByLoginAndPassword(userName, hashedPassword);
        } catch (SQLException e) {
            log.error("Cannot find user by login and password in LoginServlet.doPost()", e);
        }

        if (user == null) {
            AppUtils.storeError(request.getSession(),"Wrong user name or password!");
            response.sendRedirect("login");
            return;
        }

        AppUtils.storeLoginedUser(request.getSession(), user);

        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (Exception e) {
            log.error(e);
        }
        String requestUri = AppUtils.getRedirectAfterLoginUrl(redirectId);
        if (requestUri != null) {
            response.sendRedirect(requestUri);
        } else {
            // Default after successful login
            // redirect to /customerTask page
            response.sendRedirect(request.getContextPath() + "/customerTask");
        }
    }
}