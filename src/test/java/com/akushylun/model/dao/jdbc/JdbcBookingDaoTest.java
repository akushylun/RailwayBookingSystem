package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Ticket;

public class JdbcBookingDaoTest {

    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private BookingDao dao;

    @Before
    public void setUp() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	dao = new JdbcBookingDao(databaseConfig.getConnection());
    }

    @Test
    public void getByIdTest() throws SQLException {
	Booking actualBooking = dao.find(1).get();
	assertNotNull(actualBooking);
	assertEquals(1, actualBooking.getId());
	assertEquals(BigDecimal.valueOf(100.00).setScale(2, BigDecimal.ROUND_HALF_UP), actualBooking.getPrice());
	assertEquals(LocalDateTime.of(2017, 06, 27, 19, 58, 27), actualBooking.getDate());
	assertEquals(1, actualBooking.getUser().getId());
    }

    @Test
    public void getAllTest() throws SQLException {
	List<Booking> actualBookingList = dao.findAll();
	assertNotNull(actualBookingList.size());
	assertEquals(2, actualBookingList.size());
    }

    @Test
    public void getAllByUserIdTest() throws SQLException {
	List<Booking> actualBookingList = dao.findAllByUserId(1);
	assertEquals(2, actualBookingList.size());
    }

    @Test
    public void createTest() throws SQLException {

	List<Ticket> ticketList = new ArrayList<>();
	Ticket ticket = new Ticket.Builder().withId(2).build();
	ticketList.add(ticket);
	Booking booking = new Booking.Builder().withId(3).withPrice(BigDecimal.valueOf(200))
		.withDate(LocalDateTime.of(2017, 05, 15, 13, 20, 27)).withUser(new Person.Builder().withId(1).build())
		.withTickets(ticketList).build();

	dao.create(booking);
	dao.createBookingTicketsLink(booking);
	assertNotNull(booking.getId());
	assertEquals(3, dao.find(booking.getId()).get().getId());

    }

    @Test
    public void deleteTest() throws SQLException {
	dao.deleteBookingTicketsLink(1);
	dao.delete(1);
	assertFalse(dao.find(1).isPresent());
    }

    @Test
    public void updateTest() throws SQLException {
	Booking expectedBooking = dao.find(1).get();
	expectedBooking.setPrice(BigDecimal.valueOf(500));
	dao.update(expectedBooking);
	assertEquals(expectedBooking, dao.find(expectedBooking.getId()).get());
    }

    @After
    public void closeConnection() throws Exception {
	databaseConfig.closeConnection();
    }
}
