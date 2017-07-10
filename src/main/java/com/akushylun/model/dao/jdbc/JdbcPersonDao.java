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
import com.akushylun.model.entities.Person.Role;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcPersonDao implements PersonDao {

    private static final String SELECT_PERSON_BY_ID = "SELECT p.p_id, p.p_name, p.p_surname, "
	    + "p.p_role_r_name, l.l_id, l.l_email, l.l_password FROM person as p INNER JOIN login as l ON p.p_login_l_id = l.l_id "
	    + "WHERE p.p_id = ?";
    private static final String SELECT_PERSON_BY_LOGIN_NAME = "SELECT p.p_id, p.p_name, p.p_surname, "
	    + "p.p_role_r_name, l.l_id, l.l_email, l.l_password  FROM person as p INNER JOIN login as l ON p.p_login_l_id = l.l_id "
	    + "WHERE l.l_email = ?";
    private static final String SELECT_ALL_PERSONS = "SELECT p.p_id, p.p_name, p.p_surname, "
	    + "p.p_role_r_name, l.l_id, l.l_email, l.l_password  FROM person as p INNER JOIN login as l ON p.p_login_l_id = l.l_id ";
    private static final String CREATE_PERSON = "INSERT INTO person (p_name, p_surname, p_role_r_name, p_login_l_id) "
	    + "SELECT ?,?,?,?";
    private static final String UPDATE_PERSON = "UPDATE person SET p_name = ?, p_surname = ?, " + "p_role_r_name = ?"
	    + " WHERE p_id = ?";
    private static final String DELETE_PERSON_BY_ID = "DELETE FROM person WHERE p_id = ?";

    private Connection connection;

    public JdbcPersonDao(Connection connection) {
	this.connection = connection;
    }

    private Person getUserFromResultSet(ResultSet rs) throws SQLException {
	Person person = new Person.Builder().withId(rs.getInt("p_id")).withName(rs.getString("p_name"))
		.withSurname(rs.getString("p_surname")).withPersonLogin(getLoginFromResultSet(rs))
		.withRole(Role.valueOf(rs.getString("p_role_r_name").toUpperCase())).build();
	return person;
    }

    private Login getLoginFromResultSet(ResultSet rs) throws SQLException {
	Login login = null;
	if (rs.getString("l_email") != null) {
	    int id = rs.getInt("l_id");
	    String loginName = rs.getString("l_email");
	    String password = rs.getString("l_password");
	    login = new Login.Builder().withId(id).withEmail(loginName).withPassword(password).build();

	}
	return login;

    }

    @Override
    public Optional<Person> find(int id) throws SQLException {
	Optional<Person> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_PERSON_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Person person;
	    if (rs.next()) {
		person = getUserFromResultSet(rs);
		result = Optional.of(person);
	    }
	}
	return result;
    }

    @Override
    public Optional<Person> findByLogin(String loginName) throws SQLException {
	Optional<Person> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_PERSON_BY_LOGIN_NAME)) {
	    query.setString(1, loginName);
	    ResultSet rs = query.executeQuery();
	    Person person;
	    if (rs.next()) {
		person = getUserFromResultSet(rs);
		result = Optional.of(person);
	    }
	}
	return result;
    }

    @Override
    public List<Person> findAll() throws SQLException {
	List<Person> userList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_PERSONS)) {
	    ResultSet rs = query.executeQuery();
	    Person person;
	    while (rs.next()) {
		person = getUserFromResultSet(rs);
		userList.add(person);
	    }
	}
	return userList;
    }

    @Override
    public void create(Person person) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, person.getName());
	    query.setString(2, person.getSurname());
	    query.setString(3, person.getRole().name());
	    query.setInt(4, person.getLogin().getId());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		person.setId(keys.getInt(1));
	    }
	}
    }

    @Override
    public void update(Person person) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_PERSON)) {
	    query.setString(1, person.getName());
	    query.setString(2, person.getSurname());
	    query.setString(3, person.getRole().name());
	    query.setInt(4, person.getId());
	    query.executeUpdate();
	}
    }

    @Override
    public void delete(int id) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(DELETE_PERSON_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	}
    }

}
