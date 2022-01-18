package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static utils.AppUtils.*;

import db.dao.OrderDao;
import db.dao.ProposalDao;

import org.apache.log4j.Logger;

@WebServlet("/proposalTask")
public class ProposalServlet  extends HttpServlet {

    private static final Logger log = Logger.getLogger(ProposalServlet.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.getSession().setAttribute(
                "proposals",
                ProposalDao.getProposalByOrderId(
                    (Integer)(request.getSession().getAttribute("orderId")),
                    (String)request.getSession().getAttribute("lng")
                )
            );
        } catch (SQLException | NullPointerException e) {
            storeError(request.getSession(), "Cannot get proposals for order");
            log.error("Cannot get proposals for order", e);
            RequestDispatcher dispatcher
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/error_page.jsp");
            dispatcher.forward(request, response);
            return;
        }

        RequestDispatcher dispatcher
            = this.getServletContext().getRequestDispatcher("/WEB-INF/views/proposalView.jsp");
    
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String btnSubmit = request.getParameter("Submit");
        String btnDiscard = request.getParameter("Discard");
        String proposalId = request.getParameter("proposal");

        if (null != btnSubmit) {
            try {
                OrderDao.commitOrder(
                    (Integer)request.getSession().getAttribute("orderId"),
                    Integer.parseInt(proposalId)
                );
                storeMessage(request.getSession(), "Order submitted");
            } catch(NumberFormatException | SQLException e) {
                log.error("Error commiting order in ProposalServlet.doPost()", e);
                storeError(request.getSession(),
                    e.getMessage().substring(e.getMessage().indexOf("+") + 1));
            }
        }

        if (null != btnDiscard) {
            try {
                OrderDao.discardOrder(
                    (Integer)request.getSession().getAttribute("orderId")
                );
                storeMessage(request.getSession(), "Order discarded");
            } catch (SQLException e) {
                log.error("Error discarding order", e);
                storeError(request.getSession(), "Enable to discard order");
            }
        }

        response.sendRedirect("customerTask");

    }

}