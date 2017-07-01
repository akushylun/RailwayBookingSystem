package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.entities.Login;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcLoginDao implements LoginDao {

    private static final String SELECT_LOGIN_BY_ID = "SELECT l.l_id, l.l_login, l.l_password FROM login as l"
	    + " WHERE l.l_id = ?";
    private static final String SELECT_ALL_LOGINS = "SELECT l.l_id, l.l_login, l.l_password FROM login as l";
    private static final String CREATE_LOGIN = "INSERT INTO login (l_login, l_password) " + " VALUES (?,?)";
    private static final String UPDATE_LOGIN = "UPDATE login SET l_login = ?, l_password = ?" + " WHERE l_id = ?";
    private static final String DELETE_LOGIN_BY_ID = "DELETE FROM login WHERE l_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcLoginDao(Connection connection, boolean connectionShouldBeClosed) {
	this.connection = connection;
	this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Login getLoginFromResultSet(ResultSet rs) throws SQLException {
	Login login = new Login.Builder().withId(rs.getInt("l_id")).withLogin(rs.getString("l_login"))
		.withPassword(rs.getString("l_password")).build();
	return login;
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
	    throw new RuntimeException(ex);
	}
	return result;
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
	    throw new RuntimeException(ex);
	}
	return loginList;
    }

    @Override
    public void create(Login login) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_LOGIN, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, login.getLogin());
	    query.setString(2, login.getPassword());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		login.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void update(Login login) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_LOGIN)) {
	    query.setString(1, login.getLogin());
	    query.setString(2, login.getPassword());
	    query.setInt(3, login.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_LOGIN_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void close() throws Exception {
	if (connectionShouldBeClosed) {
	    try {
		connection.close();
	    } catch (SQLException e) {
		throw new RuntimeException(e);
	    }
	}

    }

}
