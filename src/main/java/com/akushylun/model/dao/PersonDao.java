package com.akushylun.model.dao;

import java.sql.SQLException;
import java.util.Optional;

import com.akushylun.model.entities.Person;

public interface PersonDao extends GenericDao<Person> {
    Optional<Person> findByLogin(String login) throws SQLException;
}
