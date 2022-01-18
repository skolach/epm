package db.dbmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DBManager {

    private static final Logger log = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null){
            instance = new DBManager();
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/taxvice?characterEncoding=utf8");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("Root_Password2000");
        p.setDefaultAutoCommit(false);
        p.setValidationQuery("SELECT 1");
        p.setMaxActive(100);
        p.setMaxWait(10000);
        p.setMaxIdle(30);
        p.setMinIdle(10);
        p.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        Connection connection = null;
        connection = datasource.getConnection();
        return connection;
    }

    public void rollbackAndClose(Connection con) throws SQLException{
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            log.error("Cannot rollback transaction ", ex);
            throw new SQLException(ex);
        }
    }

    public void commitAndClose(Connection con) throws SQLException{
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            log.error("Cannot commit transaction ", ex);
            throw new SQLException(ex);
        }
    }

    public static void main(String[] args){
        try (
            ResultSet rs = 
                DBManager.getInstance().getConnection().createStatement().
                executeQuery("select * from `user`");
        ) {
            while(rs.next()) {
                System.out.println(rs.getString("login") + " " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}