package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akushylun.model.dao.DepartureDao;
import com.akushylun.model.entities.Departure;
import com.akushylun.model.entities.Train;

public class JdbcDepartreDaoTest {

    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private DepartureDao dao;

    @Before
    public void setUp() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	dao = new JdbcDepartureDao(databaseConfig.getConnection());
    }

    @Test
    public void getByIdTest() throws SQLException {
	Departure actualDeparture = dao.find(1).get();
	assertNotNull(actualDeparture);
	assertEquals(1, actualDeparture.getId());
	assertEquals(LocalDateTime.of(2017, 07, 8, 9, 38, 15), actualDeparture.getDateTime());
	assertEquals(1, actualDeparture.getTrain().getId());
    }

    @Test
    public void getAllTest() throws SQLException {
	assertEquals(2, dao.findAll().size());
    }

    @Test
    public void createTest() throws SQLException {
	Departure expectedDeparture = new Departure.Builder().withDateTtime(LocalDateTime.of(2017, 06, 5, 17, 38, 15))
		.withTrain(new Train.Builder().withId(1).build()).build();
	dao.create(expectedDeparture);
	assertNotNull(expectedDeparture.getId());
	assertEquals(expectedDeparture.getId(), dao.find(expectedDeparture.getId()).get().getId());
    }

    @Test
    public void deleteTest() throws SQLException {
	dao.delete(1);
	assertFalse(dao.find(1).isPresent());
    }

    @Test
    public void updateTest() throws SQLException {
	Departure expectedDeparture = new Departure.Builder().withId(1)
		.withDateTtime(LocalDateTime.of(2018, 04, 8, 17, 38, 15))
		.withTrain(new Train.Builder().withId(1).build()).build();
	dao.update(expectedDeparture);
	assertEquals(expectedDeparture.getDateTime(), dao.find(expectedDeparture.getId()).get().getDateTime());
    }

    @After
    public void closeConnection() throws Exception {
	databaseConfig.closeConnection();
    }
}
