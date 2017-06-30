package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.entity.Shedule;
import com.akushylun.model.entity.Ticket;

public class JdbcTicketDaoTest {
    static Connection connection;
    static TicketDao ticketDao;
    static Shedule shedule = new Shedule.Builder().withId(1).start(LocalDateTime.of(2017, 06, 27, 19, 53, 46))
	    .end(LocalDateTime.of(2017, 06, 27, 19, 53, 46)).build();
    static Ticket ticketInH2Script = new Ticket.Builder().withId(1).withPrice(BigDecimal.valueOf(100.00))
	    .withShedule(shedule).build();
    static Ticket ticket = new Ticket.Builder().withPrice(BigDecimal.valueOf(200)).withShedule(shedule).build();

    @BeforeClass
    public static void setH2DataBase()
	    throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	Class.forName("org.h2.Driver").newInstance();
	connection = DriverManager.getConnection("jdbc:h2:mem:;MODE=mysql;", "sa", "");
	try {
	    File script = new File(JdbcSheduleDaoTest.class.getResource("/mydb.sql").getFile());
	    RunScript.execute(connection, new FileReader(script));
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("could not initialize with script");
	}
	ticketDao = new JdbcTicketDao(connection);
	ticketDao.create(ticket);
    }

    @Test
    public void getByIdTest() {
	assertEquals(ticket, ticketDao.find(ticket.getId()).get());
    }

    @Test
    public void getAllTest() {
	assertEquals(2, ticketDao.findAll().size());
    }

    @Test
    public void createTest() {
	assertNotNull(ticket.getId());
    }

    @Test
    public void deleteTest() {
	Ticket createTicket = new Ticket.Builder().withId(3).withPrice(BigDecimal.valueOf(200)).withShedule(shedule)
		.build();
	ticketDao.create(createTicket);
	assertNotNull(createTicket.getId());
	ticketDao.delete(createTicket.getId());
	assertEquals(2, ticketDao.findAll().size());
    }

    @Test
    public void updateTest() {
	BigDecimal newPrice = BigDecimal.valueOf(230.567);
	Ticket newTicket = new Ticket.Builder().withId(ticketInH2Script.getId()).withPrice(newPrice)
		.withShedule(shedule).build();
	ticketDao.update(newTicket);
	assertEquals(newTicket.getPrice(), ticketDao.find(newTicket.getId()).get().getPrice());
	assertEquals(newTicket.getShedule(), ticketDao.find(newTicket.getId()).get().getShedule());
	ticketDao.update(ticketInH2Script);
	assertEquals(ticketInH2Script.getPrice(), ticketDao.find(ticketInH2Script.getId()).get().getPrice());
	assertEquals(ticketInH2Script.getShedule(), ticketDao.find(ticketInH2Script.getId()).get().getShedule());
    }

    @AfterClass
    public static void closeConnection() throws Exception {
	connection.close();
    }
}
