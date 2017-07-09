package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;

public class JdbcTicketDaoTest {

    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private TicketDao dao;

    @Before
    public void setUp() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	dao = new JdbcTicketDao(databaseConfig.getConnection(), true);
    }

    @Test
    public void getByIdTest() {
	Ticket actualTicket = dao.find(1).get();
	assertNotNull(actualTicket);
	assertEquals(1, actualTicket.getId());
	assertEquals(BigDecimal.valueOf(100.00).setScale(2, BigDecimal.ROUND_HALF_UP), actualTicket.getPrice());
	assertEquals(1, actualTicket.getTrain().getId());
    }

    @Test
    public void getAllTest() {
	assertEquals(2, dao.findAll().size());
    }

    @Test
    public void createTest() {
	Ticket expectedTicket = new Ticket.Builder().withPrice(BigDecimal.valueOf(600))
		.withTrain(new Train.Builder().withId(1).build()).build();
	dao.create(expectedTicket);
	assertNotNull(expectedTicket.getId());
    }

    @Test
    public void deleteTest() {
	Ticket expectedTicket = new Ticket.Builder().withPrice(BigDecimal.valueOf(600))
		.withTrain(new Train.Builder().withId(1).build()).build();
	dao.create(expectedTicket);
	dao.delete(expectedTicket.getId());
	assertFalse(dao.find(expectedTicket.getId()).isPresent());
    }

    @Test
    public void updateTest() {
	Ticket expectedTicket = new Ticket.Builder().withId(1).withPrice(BigDecimal.valueOf(600))
		.withTrain(new Train.Builder().withId(1).build()).build();
	dao.update(expectedTicket);
	assertEquals(expectedTicket.getPrice(), dao.find(expectedTicket.getId()).get().getPrice());
    }

    @After
    public void closeConnection() throws Exception {
	databaseConfig.closeConnection();
    }
}
