package com.akushylun.model.dao;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;

public interface PersonDao extends GenericDao<Person> {
    Optional<Person> findByLogin(String login);

    List<Person> findAll(Role role);
}
