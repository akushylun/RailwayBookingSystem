package com.akushylun.model.dao;

import java.util.Optional;

import com.akushylun.model.entity.Person;

public interface PersonDao extends GenericDao<Person>, AutoCloseable {
    Optional<Person> findByEmail(String email);
}
