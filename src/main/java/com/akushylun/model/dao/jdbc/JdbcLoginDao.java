package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.entities.Login;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcLoginDao implements LoginDao {

    private static final String SELECT_LOGIN_BY_ID = "SELECT l.l_id, l.l_email, l.l_password FROM login as l"
	    + " WHERE l.l_id = ?";
    private static final String SELECT_ALL_LOGINS = "SELECT l.l_id, l.l_email, l.l_password FROM login as l";
    private static final String CREATE_LOGIN = "INSERT INTO login (l_email, l_password) " + " VALUES (?,?)";
    private static final String UPDATE_LOGIN = "UPDATE login SET l_email = ?, l_password = ?" + " WHERE l_id = ?";
    private static final String DELETE_LOGIN_BY_ID = "DELETE FROM login WHERE l_id = ?";

    private static final Logger LOGGER = Logger.getLogger(JdbcLoginDao.class);
    private Connection connection;

    public JdbcLoginDao(Connection connection) {
	this.connection = connection;
    }

    @Override
    public Optional<Login> find(int id) {
	Optional<Login> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_LOGIN_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Login login;
	    if (rs.next()) {
		login = getLoginFromResultSet(rs);
		result = Optional.of(login);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    private Login getLoginFromResultSet(ResultSet rs) {
	Login login;
	try {
	    login = new Login.Builder().withId(rs.getInt("l_id")).withEmail(rs.getString("l_email"))
		    .withPassword(rs.getString("l_password")).build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Login.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return login;
    }

    @Override
    public List<Login> findAll() {
	List<Login> loginList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_LOGINS)) {
	    ResultSet rs = query.executeQuery();
	    Login login;
	    while (rs.next()) {
		login = getLoginFromResultSet(rs);
		loginList.add(login);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return loginList;
    }

    @Override
    public void create(Login login) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_LOGIN, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, login.getEmail());
	    query.setString(2, login.getPassword());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		login.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void update(Login login) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_LOGIN)) {
	    query.setString(1, login.getEmail());
	    query.setString(2, login.getPassword());
	    query.setInt(3, login.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_LOGIN_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

}
