package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcPersonDao implements PersonDao {

    private static final String SELECT_PERSON_BY_ID = "SELECT p.p_id, p.p_name, p.p_surname, p.p_email, "
	    + "p.p_role_r_name, l.l_id, l.l_login, l.l_password FROM person as p INNER JOIN login as l ON p.p_login_l_id = l.l_id "
	    + "WHERE p.p_id = ?";
    private static final String SELECT_PERSON_BY_EMAIL = "SELECT p.p_id, p.p_name, p.p_surname, p.p_email, "
	    + "p.p_role_r_name, l.l_id, l.l_login, l.l_password  FROM person as p INNER JOIN login as l ON p.p_login_l_id = l.l_id "
	    + "WHERE p.p_email = ?";
    private static final String SELECT_ALL_PERSONS = "SELECT p.p_id, p.p_name, p.p_surname, p.p_email, "
	    + "p.p_role_r_name, l.l_id, l.l_login, l.l_password  FROM person as p INNER JOIN login as l ON p.p_login_l_id = l.l_id ";
    private static final String CREATE_PERSON = "INSERT INTO person (p_name, p_surname, p_email, p_role_r_name, p_login_l_id) "
	    + "SELECT ?,?,?,?,l_id FROM login WHERE l_login = ?";
    private static final String UPDATE_PERSON = "UPDATE person SET p_name = ?, p_surname = ?, p_email = ?, "
	    + "p_role_r_name = ?" + " WHERE p_id = ?";
    private static final String DELETE_PERSON_BY_ID = "DELETE FROM person WHERE p_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcPersonDao(Connection connection, boolean connectionShouldBeClosed) {
	this.connection = connection;
	this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Person getUserFromResultSet(ResultSet rs) throws SQLException {
	Person person = new Person.Builder().withId(rs.getInt("p_id")).withName(rs.getString("p_name"))
		.withSurname(rs.getString("p_surname")).withEmail(rs.getString("p_email"))
		.withPersonLogin(getLoginFromResultSet(rs)).withRole(rs.getString("p_role_r_name")).build();
	return person;
    }

    private Login getLoginFromResultSet(ResultSet rs) {
	Login login = null;
	try {
	    if (rs.getString("l_login") != null) {
		int id = rs.getInt("l_id");
		String loginName = rs.getString("l_login");
		String password = rs.getString("l_password");
		login = new Login.Builder().withId(id).withLogin(loginName).withPassword(password).build();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return login;
    }

    @Override
    public Optional<Person> find(int id) {
	Optional<Person> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_PERSON_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Person person;
	    if (rs.next()) {
		person = getUserFromResultSet(rs);
		result = Optional.of(person);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return result;
    }

    @Override
    public Optional<Person> findByEmail(String email) {
	Optional<Person> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_PERSON_BY_EMAIL)) {
	    query.setString(1, email);
	    ResultSet rs = query.executeQuery();
	    Person person;
	    if (rs.next()) {
		person = getUserFromResultSet(rs);
		result = Optional.of(person);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return result;
    }

    @Override
    public List<Person> findAll() {
	List<Person> userList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_PERSONS)) {
	    ResultSet rs = query.executeQuery();
	    Person person;
	    while (rs.next()) {
		person = getUserFromResultSet(rs);
		userList.add(person);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return userList;
    }

    @Override
    public void create(Person person) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, person.getName());
	    query.setString(2, person.getSurname());
	    query.setString(3, person.getEmail());
	    query.setString(4, person.getRole());
	    query.setString(5, person.getLogin().getLogin());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		person.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void update(Person person) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_PERSON)) {
	    query.setString(1, person.getName());
	    query.setString(2, person.getSurname());
	    query.setString(3, person.getEmail());
	    query.setString(4, person.getRole());
	    query.setInt(5, person.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_PERSON_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void close() {
	if (connectionShouldBeClosed) {
	    try {
		connection.close();
	    } catch (SQLException e) {
		throw new RuntimeException(e);
	    }
	}
    }

}
