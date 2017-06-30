package com.akushylun.model.dao;

public interface DaoConnection extends AutoCloseable {
    void begin();

    void commit();

    void rollback();

    @Override
    void close();
}
