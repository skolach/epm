package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import db.dbmanager.DBManager;
import db.entity.Route;

public class RouteDao {

    private static final Logger log = Logger.getLogger(RouteDao.class.getName());
    private static final String SQL_INSERT_ROUTE =
    "INSERT INTO `route`(`order_id`, `point_order`, `point_name`) VALUES(?, ?, ?)";

    private RouteDao(){}

    public static void insertRoute(Route route) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_ROUTE, Statement.RETURN_GENERATED_KEYS);// NOSONAR
            int k = 1;
            pstmt.setInt(k++, route.getOrderId());
            pstmt.setInt(k++, route.getPointOrder());
            pstmt.setString(k, route.getPointName());

            if (pstmt.executeUpdate() > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    route.setId(rs.getInt(1));
                }
                rs.close();
            }
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot insert user to DB", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

}
