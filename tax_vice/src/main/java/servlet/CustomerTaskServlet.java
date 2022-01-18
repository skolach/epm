package servlet;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import db.dao.CarClassDao;
import db.dao.FeatureDao;
import db.dao.OrderDao;
import db.dao.OrderRouteDao;
import db.entity.Order;
import db.entity.Route;
import utils.AppUtils;
import utils.SortField;

import org.apache.log4j.Logger;

@WebServlet("/customerTask")
public class CustomerTaskServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(CustomerTaskServlet.class);

    private static final long serialVersionUID = 1L;

    private static final Integer ORDERS_PER_PAGE = 3;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.getSession().removeAttribute("orderId");
            request.getSession().removeAttribute("proposals");

            // Sorting customer orders approach

            if (null != request.getParameter("customerSort")) {
                
                String fieldName = request.getParameter("customerSort");

                // If requested field for sorting exists in session, we must change order
                if (
                    (null != request.getSession().getAttribute("customerSort")) &&
                        (((SortField)request.getSession().getAttribute("customerSort")).
                        getFieldName().equals(fieldName))
                    )
                {
                    SortField sf = (SortField)request.getSession().getAttribute("customerSort");
                    sf.reverseOrder();
                    request.getSession().setAttribute("customerSort", sf);
                } else {
                    SortField sf = new SortField();
                    sf.setFieldName(fieldName);
                    sf.setOrder("asc");
                    request.getSession().setAttribute("customerSort", sf);
                }
            }

            int userId = AppUtils.getLoginedUser(request.getSession()).getId();
            int ordersCount = OrderDao.countOrdersByUser(userId);

            int pagesCount = ordersCount % ORDERS_PER_PAGE == 0 ?
                    (ordersCount / ORDERS_PER_PAGE) :
                    (ordersCount / ORDERS_PER_PAGE + 1);

            int offset =
                null == request.getParameter("ordersByUserPageNo") ? (
                    0
                ) : (
                    Integer.parseInt(request.getParameter("ordersByUserPageNo")) * ORDERS_PER_PAGE
                );

            request.getSession().setAttribute("ordersByUserPagesCount", pagesCount);

            request.getSession().setAttribute("features",
                FeatureDao.getFeaturesLocalized(
                    request.getParameter("lng") == null ?
                    (String)request.getSession().getAttribute("lng") :
                    request.getParameter("lng")
                )
            );

            request.getSession().setAttribute("carClasses",
                CarClassDao.findAllCarClasses());

            request.getSession().setAttribute("orders", OrderDao.getOrdersByUser(
                userId, offset, ORDERS_PER_PAGE,
                (SortField)request.getSession().getAttribute("customerSort")));

        } catch (SQLException | UnsupportedCharsetException e) {
            log.error("Cannot get additional parameters(features or car classes)", e);
            //TODO: redirect to the error page
        }

        RequestDispatcher dispatcher
            = this.getServletContext().getRequestDispatcher("/WEB-INF/views/customerTaskView.jsp");

        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        request.getSession().removeAttribute("orderId");
        request.getSession().removeAttribute("proposals");

        Order order = new Order();
        order.setUserId(AppUtils.getLoginedUser(request.getSession()).getId());
        int passengerQty = 0;
        int carClass = 0;
        try{
            passengerQty = Integer.parseInt(request.getParameterValues("qty")[0]);
            carClass = Integer.parseInt(request.getParameterValues("carClass")[0]);
        } catch(NumberFormatException e) {
            log.error("Error parsing string to int in CustomerTasckServlet.doPost()", e);
            AppUtils.storeError(request.getSession(), "Error submiting order");
        }
        
        StringBuilder features = new StringBuilder();
        
        log.info(request.getParameterValues("feature"));

        for (String feature : request.getParameterValues("feature")) {
            features.append((features.length() > 0 ? "," : ""));
            features.append(feature);
        }
        
        List<Route> routeList = new ArrayList<>();
        int i = 0;

        for (String pointName : request.getParameterValues("pointName")) {
            Route route = new Route();
            route.setPointOrder(i++);

            route.setPointName(pointName);
            routeList.add(route);
        }

        try {

            OrderRouteDao.placeOrder(order, routeList);
            request.getSession(false).setAttribute("orderId", order.getId());
            
            if(OrderDao.calcOrder(
                    order.getId(), passengerQty, carClass, features.toString()) == 0) {
                AppUtils.storeError(request.getSession(),
                    "Sorry, but nothing to propose. All cars busy. Try to change order parameters.");
                response.sendRedirect("customerTask");
            } else {
                response.sendRedirect("proposalTask");
            }
        } catch (SQLException e) {
            log.error("Cannot place order(with routes) to DB", e);
            AppUtils.storeError(request.getSession(), "Order can't be placed");
            response.sendRedirect("customerTask");
            return;
        }
    }
}