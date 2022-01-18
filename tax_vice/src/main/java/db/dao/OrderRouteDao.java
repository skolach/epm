package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import db.dbmanager.DBManager;
import db.entity.Order;
import db.entity.Route;

import org.apache.log4j.Logger;

public class OrderRouteDao {

    private static final Logger log = Logger.getLogger(OrderRouteDao.class.getName());
    private static final String SQL_INSERT_ORDER = 
        "INSERT INTO `order` (`user_id`, `start_at`)" +
        "VALUES(?, ?)";
    private static final String SQL_INSERT_ROUTE = 
        "INSERT INTO `route`(`order_id`, `point_order`, `point_name`) " +
        "VALUES (?, ?, ?)";

    private OrderRouteDao(){}

    public static void placeOrder(Order order, List<Route> routes) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            //Insert order
            pstmt = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);// NOSONAR
            int k = 1;
            pstmt.setInt(k++, order.getUserId());
            pstmt.setTimestamp(k, new Timestamp(System.currentTimeMillis()));
            if (pstmt.executeUpdate() > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                }
                rs.close();
                //Insert routes for previously inserted order
                pstmt = con.prepareStatement(SQL_INSERT_ROUTE, Statement.RETURN_GENERATED_KEYS);// NOSONAR
                for (Route route : routes) {
                    k = 1;
                    pstmt.setInt(k++, order.getId());
                    pstmt.setInt(k++, route.getPointOrder());
                    pstmt.setString(k, route.getPointName());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot place order to DB", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }
}