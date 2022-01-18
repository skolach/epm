package db.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import db.dbmanager.DBManager;
import db.entity.Order;
import db.entity.OrderEnhanced;
import utils.SortField;

public class OrderDao {

    private OrderDao() {}

    private static final Logger log = Logger.getLogger(OrderDao.class.getName());
    private static final String SQL_INSERT_ORDER =
        "INSERT INTO `order`"+
        "(`user_id`, `start_at`, `end_at`, `price`, `route_discount`, `user_discount`, `cash`)" +
        "VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ORDERS_BY_USER =
        "select o.`id`, o.`user_id`, o.`created_at`, o.`start_at`, o.`end_at`, " +
            "o.`price`, o.`route_discount`, o.`user_discount`, o.`cash`, " +
            "group_concat(r.`point_name` separator ', ') as `routes` " +
        "from `order` o join " +
            "(select `id`, `order_id`, `point_order`, `point_name` " +
            "from `route` order by `point_order` asc) r " +
        "on o.`id` = r.`order_id` and not o.`cash` is null  " +
        "group by o.`id` " +
        "having o.`user_id` = ? ";

    private static final String SQL_COUNT_ORDERS_BY_USER = 
        "select count(1) from `order` o where o.`cash` is not null and o.`user_id` = ?";

    private static final String SQL_SELECT_ORDERS = 
        "select o.`id`, o.`user_id`, o.`created_at`, o.`start_at`, o.`end_at`, " +
            "o.`price`, o.`route_discount`, o.`user_discount`, o.`cash`, " +
            "group_concat(r.`point_name` separator ', ') as `routes`, u.`login` " +
        "from `order` o join " +
            "(select `id`, `order_id`, `point_order`, `point_name` " +
            "from `route` order by `point_order` asc) r " +
        "on o.`id` = r.`order_id` and not o.`cash` is null  " +
        "join `user` u on o.`user_id` = u.`id` " +
        "group by o.`id` ";

    private static final String SQL_COUNT_ORDERS =
        "select count(1) from `order` o join `user` u on o.`user_id` = u.`id` " +
        "where o.`cash` is not null";

    public static void insertOrder(Order order) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);// NOSONAR
            int k = 1;
            pstmt.setInt(k++, order.getUserId());
            pstmt.setTimestamp(k++, order.getCreatedAt());
            pstmt.setTimestamp(k++, order.getStartAt());
            pstmt.setTimestamp(k++, order.getEndAt());
            pstmt.setBigDecimal(k++, order.getPrice());
            pstmt.setInt(k++, order.getRouteDiscount());
            pstmt.setInt(k++, order.getUserDiscount());
            pstmt.setBigDecimal(k, order.getCash());

            if (pstmt.executeUpdate() > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                }
                rs.close();
            }
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot insert order to DB", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public static int calcOrder(int orderId, int passengerQty, 
            int carClass, String features) throws SQLException {
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
                                                                    // IN `order_id` INT,
                                                                    // IN `capacity` INT,
                                                                    // IN `class_id` INT,
                                                                    // IN `features_id` varchar(255)
            cstmt = con.prepareCall("call `taxvice`.sp_COMCalcOrder(?, ?, ?, ?, ?)"); //NOSONAR
            int i = 1;
            cstmt.setInt(i++, orderId);
            cstmt.setInt(i++, passengerQty);
            cstmt.setInt(i++, carClass);
            cstmt.setString(i++, features);
            cstmt.registerOutParameter(i, Types.INTEGER);
            cstmt.execute();
            int proposalCount = cstmt.getInt(i);
            cstmt.close();
            return proposalCount;
        } catch (SQLException e) {
            log.error("Cannot calculate order", e);
            DBManager.getInstance().rollbackAndClose(con);
            throw new SQLException(e);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public static void commitOrder(Integer orderId, Integer proposalId) throws SQLException {
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            cstmt = con.prepareCall("call `taxvice`.sp_COMCommitOrder(?, ?)"); //NOSONAR
            cstmt.setInt(1, orderId);
            cstmt.setInt(2, proposalId);
            cstmt.executeUpdate();
            cstmt.close();
        } catch (SQLException e) {
            log.error("Cannot commit order", e);
            //DBManager.getInstance().rollbackAndClose(con);
            throw new SQLException(e);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public static void discardOrder(Integer orderId) throws SQLException {
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            cstmt = con.prepareCall("call `taxvice`.sp_COMDiscardOrder(?)"); //NOSONAR
            cstmt.setInt(1, orderId);
            cstmt.executeUpdate();
            cstmt.close();
        } catch (SQLException e) {
            log.error("Cannot discard order", e);
            DBManager.getInstance().rollbackAndClose(con);
            throw new SQLException(e);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public static List<OrderEnhanced> getOrdersByUser(
            int userId, int offset, int count, SortField sortField) throws SQLException {
        List<OrderEnhanced> result = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        // Combine SQL string according to the order and limit parameters
        String resultSQL = SQL_SELECT_ORDERS_BY_USER +
            (null != sortField ? "order by " + sortField.toSQLString() : "") +
            " limit " + offset + ", " + count + " ";

        log.info("resultSQL = " + resultSQL);

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(resultSQL); // NOSONAR
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                OrderEnhanced orderE = new OrderEnhanced(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("start_at"),
                    rs.getTimestamp("end_at"),
                    rs.getBigDecimal("price"),
                    rs.getInt("route_discount"),
                    rs.getInt("user_discount"),
                    rs.getBigDecimal("cash"),
                    rs.getString("routes"),
                    null
                );
                result.add(orderE);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot get list of orders by user id", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return result;
    }

    public static Integer countOrdersByUser(int userId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_COUNT_ORDERS_BY_USER); //NOSONAR
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot count orders by user id", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return count;
    }

    public static List<OrderEnhanced> getOrders(
            String filterUserLogin,
            String filterBeginStartAt,
            String filterEndStartAt,
            int offset, int count, SortField sortField) throws SQLException {
        List<OrderEnhanced> result = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        // Combine SQL string according to the order and limit parameters
        StringBuilder resultSQL = new StringBuilder(SQL_SELECT_ORDERS);

        // having

        if (null != filterUserLogin) {
            resultSQL.append("having u.`login` like '%" + filterUserLogin + "%' ");
            resultSQL.append(" and o.`start_at` between ifnull(" +
                
                ((null == filterBeginStartAt || "".equals(filterBeginStartAt)) ? "null" : "'" + filterBeginStartAt + "'") +

                ",'1900-01-01') and ifnull(" + 

                ((null == filterEndStartAt || "".equals(filterEndStartAt)) ? "null" : "'" + filterEndStartAt + "'") +
                
                ", '2222-01-01') "

            );
        } else {
            resultSQL.append(" having o.`start_at` between ifnull(" +
                
                ((null == filterBeginStartAt || "".equals(filterBeginStartAt)) ? "null" : "'" + filterBeginStartAt + "'") +

                ",'1900-01-01') and ifnull(" + 

                ((null == filterEndStartAt || "".equals(filterEndStartAt)) ? "null" : "'" + filterEndStartAt + "'") +
                
                ", '2222-01-01') "

            );
        }

        // order by

        if (null != sortField) {
            resultSQL.append("order by " + sortField.toSQLString() + " ");
        }

        // limit

        resultSQL.append(" limit " + offset + ", " + count + " ");

        log.info("resultSQL = " + resultSQL);

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(resultSQL.toString()); // NOSONAR
            rs = pstmt.executeQuery();
            while(rs.next()) {
                OrderEnhanced orderE = new OrderEnhanced(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("start_at"),
                    rs.getTimestamp("end_at"),
                    rs.getBigDecimal("price"),
                    rs.getInt("route_discount"),
                    rs.getInt("user_discount"),
                    rs.getBigDecimal("cash"),
                    rs.getString("routes"),
                    rs.getString("login")
                );
                result.add(orderE);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot get list of orders for admin", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return result;
    }

public static Integer countOrders(
        String filterUserLogin,
        String filterBeginStartAt,
        String filterEndStartAt) throws SQLException {

    Statement stmt = null;
    ResultSet rs = null;
    Connection con = null;
    int count = 0;

    String filterBeginStartAtNull = ("".equals(filterBeginStartAt) ? null: filterBeginStartAt);
    String filterEndStartAtNull = ("".equals(filterEndStartAt) ? null: filterEndStartAt);

    String resultSQL = SQL_COUNT_ORDERS +

    // where
    (null != filterUserLogin ? " and u.`login` like '%" + filterUserLogin + "%' " : "") +
    (null != filterBeginStartAtNull || null != filterEndStartAtNull ? 
        " and o.`start_at` between ifnull(" +
        (null == filterBeginStartAtNull ? "null": "'" + filterBeginStartAtNull +"'" ) +
        ", '1900-01-01') and " +
        "ifnull(" +
        (null == filterEndStartAtNull ? "null": "'" + filterEndStartAtNull + "'") +
        ", '2222-01-01') " : "");

    log.info("resultCountSQL = " + resultSQL);

    try {
        con = DBManager.getInstance().getConnection();
        stmt = con.createStatement(); //NOSONAR
        rs = stmt.executeQuery(resultSQL);
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        stmt.close();
    } catch (SQLException ex) {
        DBManager.getInstance().rollbackAndClose(con);
        log.error("Cannot count orders for admin", ex);
        throw new SQLException(ex);
    } finally {
        DBManager.getInstance().commitAndClose(con);
    }
    return count;
}

    public static void main(String[] args) throws SQLException{
        // for (OrderEnhanced oe : getOrdersByUser(1, 2, 2)) {
        //     System.out.println(oe);
        // }
    }
}