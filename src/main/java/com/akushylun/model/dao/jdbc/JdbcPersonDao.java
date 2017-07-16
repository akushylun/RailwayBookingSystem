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

    private static final Logger LOGGER = Logger.getLogger(JdbcPersonDao.class);
    private Connection connection;

    public JdbcPersonDao(Connection connection) {
	this.connection = connection;
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
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    private Person getUserFromResultSet(ResultSet rs) {
	Person person;
	try {
	    person = new Person.Builder().withId(rs.getInt("p_id")).withName(rs.getString("p_name"))
		    .withSurname(rs.getString("p_surname")).withPersonLogin(getLoginFromResultSet(rs))
		    .withRole(Role.valueOf(rs.getString("p_role_r_name").toUpperCase())).build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Person.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return person;
    }

    private Login getLoginFromResultSet(ResultSet rs) {
	Login login = null;
	try {
	    if (rs.getString("l_email") != null) {
		int id = rs.getInt("l_id");
		String loginName = rs.getString("l_email");
		String password = rs.getString("l_password");
		login = new Login.Builder().withId(id).withEmail(loginName).withPassword(password).build();
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Login.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return login;

    }

    @Override
    public Optional<Person> findByLogin(String loginName) {
	Optional<Person> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_PERSON_BY_LOGIN_NAME)) {
	    query.setString(1, loginName);
	    ResultSet rs = query.executeQuery();
	    Person person;
	    if (rs.next()) {
		person = getUserFromResultSet(rs);
		result = Optional.of(person);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_LOGIN;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
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
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return userList;
    }

    @Override
    public void create(Person person) {
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
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void update(Person person) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_PERSON)) {
	    query.setString(1, person.getName());
	    query.setString(2, person.getSurname());
	    query.setString(3, person.getRole().name());
	    query.setInt(4, person.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_PERSON_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

}
