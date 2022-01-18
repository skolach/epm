package com.epam.rd.java.basic.practice8.db;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.rd.java.basic.practice8.db.entity.Team;
import com.epam.rd.java.basic.practice8.db.entity.User;

public class DBManager {

    static Logger logger = Logger.getAnonymousLogger();

    private static final String SQL_ADD_NEW_USER =
        "insert into users(login) values(?);";
    private static final String SQL_SELECT_ALL_USERS = 
        "select id, login from users;";
    private static final String SQL_ADD_NEW_TEAM =
        "insert into teams(name) values(?);";
    private static final String SQL_SELECT_ALL_TEAMS =
        "select id, name from teams;";
    private static final String SQL_SELECT_USER_BY_LOGIN =
        "select id, login from users where login = ?;";
    private static final String SQL_SELECT_TEAM_BY_NAME =
        "select id, name from teams where name = ?;";
	private static final String SQL_INSERT_USER_TEAM =
        "insert into users_teams(user_id, team_id) values(?, ?);";
    private static final String SQL_SELECT_TEAMS_BY_USER =
        "select t.id, t.name " +
        "from teams t join users_teams ut " +
        "on t.id = ut.team_id " +
        "where ut.user_id = ?;";
    private static final String SQL_DELETE_TEAM =
        "delete from teams where id = ?;";
    private static final String SQL_UPDATE_TEAM =
        "update teams set name = ? where id = ?;";
    private static final String SQL_DELETE_USERS_TEAMS =
        "call sp_clear();";
    
    private static DBManager dbManager;
    private String connectionString = null;

    private DBManager() {
        try (InputStream input = new FileInputStream("app.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            connectionString = properties.getProperty("connection.url");
        } catch (IOException io) {
            logger.log(Level.SEVERE, "", io);
        }
    }

    public static DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString);
    }

    public void insertUser(User user) throws SQLException {
        ResultSet rs = null;
        try (
            Connection connection = getConnection();
            PreparedStatement pstmt = 
                connection.prepareStatement(SQL_ADD_NEW_USER,
                Statement.RETURN_GENERATED_KEYS);
        ) {
			pstmt.setString(1, user.getLogin());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getLong(1));
				}
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "", ex);
		} finally {
            close(rs);
        }
    }
    
    public List<User> findAllUsers() throws SQLException {
        List<User> result = new ArrayList<>();
		ResultSet rsAllUsers = null;

		try (
			Connection allUsersCon = getConnection();
			Statement allUsersStmt = allUsersCon.createStatement();

        ){
			rsAllUsers = allUsersStmt.executeQuery(SQL_SELECT_ALL_USERS);
			while(rsAllUsers.next()){
                User user = new User();
                user.setId(rsAllUsers.getLong("id"));
                user.setLogin(rsAllUsers.getString("login"));
                result.add(user);
            }
		} catch (SQLException ex) {
            logger.log(Level.SEVERE, "", ex);
		} finally {
			close(rsAllUsers);
		}
        return result;
    }

    public void insertTeam(Team team) throws SQLException {
		ResultSet resultSet = null;

		try (
			Connection teamCon = getConnection();
			PreparedStatement teamPstmt = 
                teamCon.prepareStatement(SQL_ADD_NEW_TEAM,
                Statement.RETURN_GENERATED_KEYS);
        ) {
			teamPstmt.setString(1, team.getName());
			if (teamPstmt.executeUpdate() > 0) {
				resultSet = teamPstmt.getGeneratedKeys();
				if (resultSet.next()) {
					team.setId(resultSet.getLong(1));
				}
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "", ex);
		} finally {
			close(resultSet);
		}
    }

    public List<Team> findAllTeams() throws SQLException {
        List<Team> allTeamsResult = new ArrayList<>();
        ResultSet allTeamsRs = null;

        try (
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();

        ) {
            allTeamsRs = stmt.executeQuery(SQL_SELECT_ALL_TEAMS);
            while(allTeamsRs.next()){
                Team team = new Team();
                team.setId(allTeamsRs.getLong("id"));
                team.setName(allTeamsRs.getString("name"));
                allTeamsResult.add(team);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "", ex);
        } finally {
            close(allTeamsRs);
        }
        return allTeamsResult;
    }

    public User getUser(String login) throws SQLException {
        User userResult = new User();
		ResultSet userRs = null;

		try (
			Connection userCon = getConnection();
			PreparedStatement pstmt =
                userCon.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
        ) {
			pstmt.setString(1, login);
			pstmt.executeQuery();
            userRs = pstmt.getResultSet();
            if (userRs.next()) {
                userResult.setId(userRs.getLong("id"));
                userResult.setLogin(login);// or resultSet.getString("login") ??????
            }
			
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "", ex);
		} finally {
			close(userRs);
		}
        return userResult;
    }

    public Team getTeam(String name) throws SQLException {
        Team result = null;
		ResultSet resultSet = null;

		try (
			Connection connection = getConnection();
			PreparedStatement pstmt =
                connection.prepareStatement(SQL_SELECT_TEAM_BY_NAME);
        ) {
			pstmt.setString(1, name);
			pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            if (resultSet.next()) {
                result = new Team();
                result.setId(resultSet.getLong("id"));
                result.setName(name);// or resultSet.getString("name") ??????
            }
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "", ex);
		} finally {
			close(resultSet);
		}
        return result;
    }

    public void setTeamsForUser(User user, Team... teams) throws SQLException {
        Objects.requireNonNull(user);
        Objects.requireNonNull(teams);
        Connection connection = null;
        PreparedStatement pstmt = null;
        
		try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL_INSERT_USER_TEAM);
            connection.setAutoCommit(false);
            for (Team t : teams) {
                if ( t!=null ){
                    pstmt.setLong(1, user.getId());
                    pstmt.setLong(2, t.getId());
                    pstmt.executeUpdate();
                }
            }
            connection.commit();
            
		} catch (SQLException ex) {
            if (connection != null){
                connection.rollback();
            }
			logger.log(Level.SEVERE, "", ex);
		} finally {
            close(pstmt);
			close(connection);
		}
    }
    
    public List<Team> getUserTeams(User user) throws SQLException {
        List<Team> result = new ArrayList<>();
		ResultSet resultSet = null;
        
		try (
            Connection connection = getConnection();
			PreparedStatement pstmt =
                connection.prepareStatement(SQL_SELECT_TEAMS_BY_USER);
        ) {
			pstmt.setLong(1, user.getId());
			pstmt.executeQuery();
            resultSet = pstmt.getResultSet();
            while (resultSet.next()) {
                Team t = new Team();
                t.setId(resultSet.getLong("id"));
                t.setName(resultSet.getString("name"));
                result.add(t);
			}
		} catch (SQLException ex) {
            logger.log(Level.SEVERE, "", ex);
		} finally {
            close(resultSet);
		}
        return result;
    }
    
    public void deleteTeam(Team team) throws SQLException {

        if (team == null) {
            return;
        }
        
        Connection connection = null;
		PreparedStatement pstmt = null;
        
        try {
			connection = getConnection();
            connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(SQL_DELETE_TEAM);
			pstmt.setLong(1, team.getId());
            pstmt.executeUpdate();
            connection.commit();
		} catch (SQLException ex){
            if (connection != null){
                connection.rollback();
            }
			logger.log(Level.SEVERE, "", ex);
		} finally {
            close(pstmt);
			close(connection);
		}
    }
    
    public void updateTeam(Team team) throws SQLException {
        Connection connection = null;
		PreparedStatement pstmt = null;
        
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(SQL_UPDATE_TEAM);
            pstmt.setString(1, team.getName());
			pstmt.setLong(2, team.getId());
            pstmt.executeUpdate();
            connection.commit();
		} catch (SQLException ex){
            if (connection != null){
                connection.rollback();
            }
			logger.log(Level.SEVERE, "", ex);
		} finally {
            close(pstmt);
			close(connection);
		}
    }

    private void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "", ex);
            }
        }
    }

    public void clearDB() throws SQLException {
        Connection connection = null;
		Statement stmt = null;
        
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
			stmt = connection.createStatement();
            stmt.executeUpdate(SQL_DELETE_USERS_TEAMS);
            connection.commit();
		} catch (SQLException ex){
            if (connection != null){
                connection.rollback();
            }
			logger.log(Level.SEVERE, "", ex);
		} finally {
            close(stmt);
			close(connection);
		}

    }
}