package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akushylun.model.dao.StationDao;
import com.akushylun.model.entities.Station;

public class JdbcStationDaoTest {
    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private StationDao dao;

    @Before
    public void setUp() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	dao = new JdbcStationDao(databaseConfig.getConnection());
    }

    @Test
    public void getByIdTest() throws SQLException {
	Station actualStation = dao.find(1).get();
	assertNotNull(actualStation);
	assertEquals(1, actualStation.getId());
	assertEquals("kiev", actualStation.getName());
    }

    @Test
    public void getAllTest() throws SQLException {
	assertEquals(4, dao.findAll().size());
    }

    @Test
    public void createTest() throws SQLException {
	Station expectedStation = new Station.Builder().withName("warshava").build();
	dao.create(expectedStation);
	assertNotNull(expectedStation.getId());
	assertEquals(expectedStation.getId(), dao.find(expectedStation.getId()).get().getId());
	assertEquals(expectedStation.getName(), dao.find(expectedStation.getId()).get().getName());
    }

    @Test
    public void deleteTest() throws SQLException {
	Station expectedStation = new Station.Builder().withName("warshava").build();
	dao.create(expectedStation);
	dao.delete(expectedStation.getId());
	assertFalse(dao.find(expectedStation.getId()).isPresent());
    }

    @Test
    public void updateTest() throws SQLException {
	Station expectedStation = new Station.Builder().withId(1).withName("warshava").build();
	dao.update(expectedStation);
	assertEquals(expectedStation.getName(), dao.find(expectedStation.getId()).get().getName());
    }

    @After
    public void closeConnection() throws Exception {
	databaseConfig.closeConnection();
    }
}
