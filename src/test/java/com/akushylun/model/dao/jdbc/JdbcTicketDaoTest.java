package com.akushylun.model.dao.jdbc;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;

import com.akushylun.model.dao.TicketDao;

public class JdbcTicketDaoTest {

    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private TicketDao dao;

    @Before
    public void setUp() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	dao = new JdbcTicketDao(databaseConfig.getConnection());
    }

    /*
     * @Test public void getByIdTest() throws SQLException { Ticket actualTicket
     * = dao.find(1).get(); assertNotNull(actualTicket); assertEquals(1,
     * actualTicket.getId());
     * assertEquals(BigDecimal.valueOf(100.00).setScale(2,
     * BigDecimal.ROUND_HALF_UP), actualTicket.getPrice()); assertEquals(1,
     * actualTicket.getTrain().getId()); }
     * 
     * @Test public void getAllTest() throws SQLException { assertEquals(2,
     * dao.findAll().size()); }
     * 
     * @Test public void createTest() throws SQLException { Ticket
     * expectedTicket = new Ticket.Builder().withPrice(BigDecimal.valueOf(600))
     * .withTrain(new Train.Builder().withId(1).build()).build();
     * dao.create(expectedTicket); assertNotNull(expectedTicket.getId()); }
     * 
     * @Test public void deleteTest() throws SQLException { Ticket
     * expectedTicket = new Ticket.Builder().withPrice(BigDecimal.valueOf(600))
     * .withTrain(new Train.Builder().withId(1).build()).build();
     * dao.create(expectedTicket); dao.delete(expectedTicket.getId());
     * assertFalse(dao.find(expectedTicket.getId()).isPresent()); }
     * 
     * @Test public void updateTest() throws SQLException { Ticket
     * expectedTicket = new
     * Ticket.Builder().withId(1).withPrice(BigDecimal.valueOf(600))
     * .withTrain(new Train.Builder().withId(1).build()).build();
     * dao.update(expectedTicket); assertEquals(expectedTicket.getPrice(),
     * dao.find(expectedTicket.getId()).get().getPrice()); }
     */

    @After
    public void closeConnection() throws Exception {
	databaseConfig.closeConnection();
    }
}
