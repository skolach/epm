package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import db.dbmanager.DBManager;
import db.entity.Executor;

public class ExecutorDao {

    private static final Logger log = Logger.getLogger(ExecutorDao.class.getName());
    private static final String SQL_INSERT_EXECUTOR =
        "INSERT INTO `executor`(`order_id`, `car_id`) VALUES(?, ?)";

    private ExecutorDao() {}

    public static void insertExecutor(Executor executor) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_EXECUTOR, Statement.RETURN_GENERATED_KEYS);// NOSONAR
            int k = 1;
            pstmt.setInt(k++, executor.getOrderId());
            pstmt.setInt(k, executor.getCarId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot insert executor to DB", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }
}