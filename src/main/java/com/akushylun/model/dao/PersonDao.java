package com.akushylun.model.dao;

import java.util.Optional;

import com.akushylun.model.entities.Person;

public interface PersonDao extends GenericDao<Person>, AutoCloseable {
    Optional<Person> findByLogin(String login);
}
