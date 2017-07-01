package com.akushylun.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<E> {
    Optional<E> find(int id);

    List<E> findAll();

    void create(E e);

    void update(E e);

    void delete(int id);

}
