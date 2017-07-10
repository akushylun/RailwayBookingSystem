package com.akushylun.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<E> {
    Optional<E> find(int id) throws SQLException;

    List<E> findAll() throws SQLException;

    void create(E e) throws SQLException;

    void update(E e) throws SQLException;

    void delete(int id) throws SQLException;

}
