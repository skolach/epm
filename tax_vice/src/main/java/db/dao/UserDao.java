package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import db.dbmanager.DBManager;
import db.entity.User;

public class UserDao {

    private static final Logger log = Logger.getLogger(UserDao.class.getName());

    private static final String SQL_FIND_USER_BY_ID =
        "SELECT `id`, `is_admin`, `login`, `password`, `name`, `discount` " + //NOSONAR
        "FROM `user` WHERE `id` = ?";

    private static final String SQL_FIND_USER_BY_LOGIN =
        "SELECT `id`, `is_admin`, `login`, `password`, `name`, `discount` " +
        "FROM `user` WHERE `login` = ?";

    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD =
        "SELECT `id`, `is_admin`, `login`, `password`, `name`, `discount` " +
        "FROM `user` WHERE `login` = ? and `password` = ?";

    private static final String SQL_UPDATE_USER =
        "UPDATE `user` SET `is_admin` = ?, `login` = ?, `password` = ?, `name` = ?, `discount` = ? " +
        "WHERE `user_id` = ?";

    private static final String SQL_INSERT_USER =
        "INSERT INTO `user` (`is_admin`, `login`, `password`, `name`, `discount` )" +
        " VALUES (?,?,?,?,?);";

    private static final String SQL_DELETE_USER_TEST = "DELETE FROM `user` WHERE `login` = 'test';";

    public static User findUserById(int id) throws SQLException {
        User user = new User();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID); //NOSONAR
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
                mapRowToUser(rs, user);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    public static User findUserByLogin(String login) throws SQLException {
        User user = new User();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN); //NOSONAR
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next())
                mapRowToUser(rs, user);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot find user by login", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    public static User findUserByLoginAndPassword(String login, String password)
            throws SQLException{
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD); //NOSONAR
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                mapRowToUser(rs, user);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot find user by login and password", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    public static void updateUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_USER); //NOSONAR
            int k = 1;
            pstmt.setInt(k++, user.getId());
            pstmt.setBoolean(k++, user.getIsAdmin());
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getName());
            pstmt.setInt(k, user.getDiscount());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot update user", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public static void insertUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);//NOSONAR
            int k = 1;
            pstmt.setBoolean(k++, user.getIsAdmin());
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getName());
            pstmt.setInt(k, user.getDiscount());
            if (pstmt.executeUpdate() > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()){
                    user.setId(rs.getInt(1));
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

    private static void mapRowToUser(ResultSet rs, User user) throws SQLException {
        try {
            user.setId(rs.getInt("id"));
            user.setIsAdmin(rs.getBoolean("is_admin"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setDiscount(rs.getInt("discount"));
        } catch (SQLException e) {
            log.error("Can't map ResultSet row to User entity ", e);
            throw new SQLException(e);
        }
    }


    public static void deleteUserTest() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_DELETE_USER_TEST); //NOSONAR
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            log.error("Cannot delete user 'test'", ex);
            throw new SQLException(ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("======================");
        System.out.println("User with id=1 is " + findUserById(1).getLogin());
        System.out.println("User 'admin' has id " + findUserByLogin("admin").getId());
        System.out.println("User finded by login and password is " +
            findUserByLoginAndPassword("admin", "d41d8cd98f00b204e9800998ecf8427e"));
        System.out.println("Delete user 'test' ");
        deleteUserTest();
        User testUser = new User(false, "test", "d41d8cd98f00b204e9800998ecf8427e", "name", 11);
        System.out.println(testUser);
        System.out.println("Insert user 'test'");
        insertUser(testUser);
        System.out.println(testUser);
    }
}