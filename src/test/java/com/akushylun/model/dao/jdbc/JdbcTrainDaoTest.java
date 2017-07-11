package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.entities.Train;

public class JdbcTrainDaoTest {
    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private TrainDao dao;

    @Before
    public void setUp() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	dao = new JdbcTrainDao(databaseConfig.getConnection());
    }

    @Test
    public void getByIdTest() throws SQLException {
	Train actualTrain = dao.find(1).get();
	assertNotNull(actualTrain);
	assertEquals(1, actualTrain.getId());
	assertEquals("intercity", actualTrain.getName());
    }

    @Test
    public void getAllTest() throws SQLException {
	assertEquals(2, dao.findAll().size());
    }

    @Test
    public void getAllByParamsTest() throws SQLException {
	String stationStart = "kiev";
	String stationEnd = "lviv";
	LocalDate startDate = LocalDate.of(2017, 07, 8);
	assertEquals(1, dao.findAll(stationStart, stationEnd, startDate).size());
    }

    @Test
    public void createTest() throws SQLException {
	Train expectedTrain = new Train.Builder().withName("A900").build();
	dao.create(expectedTrain);
	assertNotNull(expectedTrain.getId());
    }

    @Test
    public void deleteTest() throws SQLException {
	Train expectedTrain = new Train.Builder().withName("A900").build();
	dao.create(expectedTrain);
	dao.delete(expectedTrain.getId());
	assertFalse(dao.find(expectedTrain.getId()).isPresent());
    }

    @Test
    public void updateTest() throws SQLException {
	Train expectedTrain = new Train.Builder().withId(1).withName("A345").build();
	dao.update(expectedTrain);
	assertEquals(expectedTrain.getName(), dao.find(expectedTrain.getId()).get().getName());
    }

    @After
    public void closeConnection() throws Exception {
	databaseConfig.closeConnection();
    }
}
