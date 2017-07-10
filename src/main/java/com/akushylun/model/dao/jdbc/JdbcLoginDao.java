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

    private static final String SELECT_LOGIN_BY_ID = "SELECT l.l_id, l.l_email, l.l_password FROM login as l"
	    + " WHERE l.l_id = ?";
    private static final String SELECT_ALL_LOGINS = "SELECT l.l_id, l.l_email, l.l_password FROM login as l";
    private static final String CREATE_LOGIN = "INSERT INTO login (l_email, l_password) " + " VALUES (?,?)";
    private static final String UPDATE_LOGIN = "UPDATE login SET l_email = ?, l_password = ?" + " WHERE l_id = ?";
    private static final String DELETE_LOGIN_BY_ID = "DELETE FROM login WHERE l_id = ?";

    private Connection connection;

    public JdbcLoginDao(Connection connection) {
	this.connection = connection;
    }

    private Login getLoginFromResultSet(ResultSet rs) throws SQLException {
	Login login = new Login.Builder().withId(rs.getInt("l_id")).withEmail(rs.getString("l_email"))
		.withPassword(rs.getString("l_password")).build();
	return login;
    }

    @Override
    public Optional<Login> find(int id) throws SQLException {
	Optional<Login> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_LOGIN_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Login login;
	    if (rs.next()) {
		login = getLoginFromResultSet(rs);
		result = Optional.of(login);
	    }
	}
	return result;
    }

    @Override
    public List<Login> findAll() throws SQLException {
	List<Login> loginList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_LOGINS)) {
	    ResultSet rs = query.executeQuery();
	    Login login;
	    while (rs.next()) {
		login = getLoginFromResultSet(rs);
		loginList.add(login);
	    }
	}
	return loginList;
    }

    @Override
    public void create(Login login) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(CREATE_LOGIN, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, login.getEmail());
	    query.setString(2, login.getPassword());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		login.setId(keys.getInt(1));
	    }
	}
    }

    @Override
    public void update(Login login) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_LOGIN)) {
	    query.setString(1, login.getEmail());
	    query.setString(2, login.getPassword());
	    query.setInt(3, login.getId());
	    query.executeUpdate();
	}
    }

    @Override
    public void delete(int id) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(DELETE_LOGIN_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	}
    }

}
