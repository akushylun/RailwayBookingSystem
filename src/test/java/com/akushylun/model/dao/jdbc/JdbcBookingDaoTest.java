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
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.entities.Ticket;

public class JdbcBookingDaoTest {
    static Connection connection;
    static BookingDao bookingDao;
    static Ticket ticketInH2Script = new Ticket.Builder().withId(1).build();
    static List<Ticket> tickets = new ArrayList<>();
    static Person person = new Person.Builder().withId(1).withName("mark").withSurname("johnson")
	    .withEmail("mark@gmail.com").withRole(Role.USER.name()).build();
    static Booking bookingInH2Script = new Booking.Builder().withId(1).withPrice(BigDecimal.valueOf(100))
	    .withDate(LocalDateTime.of(2017, 06, 27, 19, 58, 27)).withUser(person).build();
    static Booking booking = new Booking.Builder().withId(2).withPrice(BigDecimal.valueOf(200))
	    .withDate(LocalDateTime.of(2017, 05, 15, 13, 20, 27)).withUser(person).build();

    @BeforeClass
    public static void setH2DataBase()
	    throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	Class.forName("org.h2.Driver").newInstance();
	connection = DriverManager.getConnection("jdbc:h2:mem:;MODE=mysql;", "sa", "");
	try {
	    File script = new File(JdbcPersonDaoTest.class.getResource("/mydb.sql").getFile());
	    RunScript.execute(connection, new FileReader(script));
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("could not initialize with script");
	}
	bookingDao = new JdbcBookingDao(connection, true);
	bookingDao.create(booking);
    }

    @Test
    public void getByIdTest() {
	assertEquals(1, bookingInH2Script.getId());
    }

    @Test
    public void getAllTest() {
	assertEquals(1, bookingDao.findAll().size());
    }

    @Test
    public void getAllByUserIdTest() {
	List<Booking> bookingList = bookingDao.findAllByUserId(bookingInH2Script.getId());
	assertEquals(1, bookingList.size());
    }

    @Test
    public void createTest() {
	assertNotNull(booking.getId());
    }

    @Test
    public void createBookingTicketLinkTest() {
	tickets.add(ticketInH2Script);
	booking.setTickets(tickets);
	bookingDao.createBookingTicketsLink(booking);
	Booking findedBooking = bookingDao.find(booking.getId()).get();
	assertEquals(booking.getId(), findedBooking.getId());
	assertEquals(booking.getDate(), findedBooking.getDate());
	assertEquals(booking.getPrice(), findedBooking.getPrice());
	assertEquals(booking.getTickets().size(), findedBooking.getTickets().size());
	assertEquals(booking.getUser(), findedBooking.getUser());
    }

    @Test
    public void deleteTest() {
	Booking createBooking = new Booking.Builder().withId(2).withPrice(BigDecimal.valueOf(200))
		.withDate(LocalDateTime.of(2017, 05, 15, 13, 20, 27)).withUser(person).build();
	bookingDao.create(createBooking);
	assertNotNull(createBooking.getId());
	bookingDao.delete(createBooking.getId());
	assertEquals(1, bookingDao.findAll().size());
    }

    @Test
    public void updateTest() {
	BigDecimal newPrice = BigDecimal.valueOf(230.567);
	LocalDateTime newDateTime = LocalDateTime.of(2017, 02, 12, 11, 24, 15);
	Booking newBooking = new Booking.Builder().withId(bookingInH2Script.getId()).withPrice(newPrice)
		.withDate(newDateTime).withUser(bookingInH2Script.getUser()).build();
	bookingDao.update(newBooking);
	assertEquals(newBooking.getPrice(), bookingDao.find(newBooking.getId()).get().getPrice());
	assertEquals(newBooking.getDate(), bookingDao.find(newBooking.getId()).get().getDate());
	bookingDao.update(bookingInH2Script);
	assertEquals(bookingInH2Script.getPrice(), bookingDao.find(bookingInH2Script.getId()).get().getPrice());
	assertEquals(bookingInH2Script.getDate(), bookingDao.find(bookingInH2Script.getId()).get().getDate());
    }

    @AfterClass
    public static void closeConnection() throws Exception {
	connection.close();
    }
}
