package db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import db.dbmanager.DBManager;
import db.entity.CarClass;

public class CarClassDao {

    private CarClassDao(){}

    private static final Logger log = Logger.getLogger(CarClassDao.class.getName());
    private static final String SQL_FIND_CAR_CLASSES =
        "select `id`, `name`, `rate`, `description` from `class` order by `rate` asc";

    public static List<CarClass> findAllCarClasses() throws SQLException {

        List<CarClass> classes = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();//NOSONAR
            rs = stmt.executeQuery(SQL_FIND_CAR_CLASSES);

            while(rs.next()) {
                CarClass carClass = new CarClass(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("rate"),
                    rs.getString("description")
                );
                classes.add(carClass);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            log.error("Cannot get all car classes", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }

        return classes;
    }

    public static void main(String[] args) throws SQLException{
        List<CarClass> lst = findAllCarClasses();
        for (CarClass carClass : lst) {
            System.out.println(carClass);
        }
    }
}