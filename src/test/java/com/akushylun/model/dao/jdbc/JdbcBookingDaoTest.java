package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.entities.Booking;

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

    @Ignore
    @Test
    public void getByIdTest() throws SQLException {
	Booking actualBooking = dao.find(1).get();
	assertNotNull(actualBooking);
	assertEquals(1, actualBooking.getId());
	assertEquals(BigDecimal.valueOf(100.00).setScale(2, BigDecimal.ROUND_HALF_UP), actualBooking.getPrice());
	assertEquals(LocalDateTime.of(2017, 06, 27, 19, 58, 27), actualBooking.getDate());
	assertEquals(1, actualBooking.getUser().getId());
    }

    @Ignore
    @Test
    public void getAllTest() throws SQLException {
	List<Booking> actualBookingList = dao.findAll();
	assertNotNull(actualBookingList.size());
	assertEquals(2, actualBookingList.size());
    }

    @Ignore
    @Test
    public void getAllByUserIdTest() throws SQLException {
	List<Booking> actualBookingList = dao.findAllByUserId(1);
	assertEquals(2, actualBookingList.size());
    }

    @Ignore
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
