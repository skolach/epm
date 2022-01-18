package servlet;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import db.dao.OrderDao;
import utils.AppUtils;
import utils.SortField;

import org.apache.log4j.Logger;

@WebServlet("/adminTask")
public class AdminTaskServlet extends HttpServlet{

    private static final Logger log = Logger.getLogger(AdminTaskServlet.class);

    private static final long serialVersionUID = 1L;

    private static final int ORDERS_PER_PAGE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            // Sorting admin orders approach

            if (null != request.getParameter("adminSort")) {
                
                String fieldName = request.getParameter("adminSort");

                // If requested field for sorting exists in session, we must change order
                if (
                    (null != request.getSession().getAttribute("adminSort")) &&
                        (((SortField)request.getSession().getAttribute("adminSort")).
                        getFieldName().equals(fieldName))
                    )
                {
                    SortField sf = (SortField)request.getSession().getAttribute("adminSort");
                    sf.reverseOrder();
                    request.getSession().setAttribute("adminSort", sf);
                } else {
                    SortField sf = new SortField();
                    sf.setFieldName(fieldName);
                    sf.setOrder("asc");
                    request.getSession().setAttribute("adminSort", sf);
                }
            }

            String filterBeginStartAt = request.getParameter("filterBeginStartAt");
            String filterEndStartAt = request.getParameter("filterEndStartAt");

            request.getSession().setAttribute(
                "filterUserLogin",
                request.getParameter("filterUserLogin")
            );
            request.getSession().setAttribute("filterBeginStartAt", filterBeginStartAt);
            request.getSession().setAttribute("filterEndStartAt", filterEndStartAt);
            
            int ordersCount = OrderDao.countOrders(
                (String)request.getSession().getAttribute("filterUserLogin"),
                (String)request.getSession().getAttribute("filterBeginStartAt"),
                (String)request.getSession().getAttribute("filterEndStartAt")
                );
                
            int pagesCount = ordersCount % ORDERS_PER_PAGE == 0 ?
                (ordersCount / ORDERS_PER_PAGE) :
                (ordersCount / ORDERS_PER_PAGE + 1);
                
            request.getSession().setAttribute("ordersByAdminPagesCount", pagesCount);

            int offset =
                null == request.getParameter("ordersByAdminPageNo") ? (
                    0
                ) : (
                    Integer.parseInt(request.getParameter("ordersByAdminPageNo")) * ORDERS_PER_PAGE
                );

            request.getSession().setAttribute("adminOrders", OrderDao.getOrders(
                (String)request.getSession().getAttribute("filterUserLogin"),
                (String)request.getSession().getAttribute("filterBeginStartAt"),
                (String)request.getSession().getAttribute("filterEndStartAt"),
                offset, ORDERS_PER_PAGE,
                (utils.SortField)request.getSession().getAttribute("adminSort")));

        } catch (SQLException | UnsupportedCharsetException e) {
            log.error("Cannot generate orders list for admin user", e);
            AppUtils.storeError(request.getSession(), "Cannot generate orders list for admin user");
        }

        RequestDispatcher dispatcher
            = this.getServletContext().getRequestDispatcher("/WEB-INF/views/adminTaskView.jsp");

        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO
    }
}