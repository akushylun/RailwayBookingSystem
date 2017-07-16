package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.entities.Booking;

public class BookingService {

    private DaoFactory daoFactory;

    public BookingService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final BookingService INSTANCE = new BookingService(DaoFactory.getInstance());
    }

    public static BookingService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Booking> getById(int bookingId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    return bookingDao.find(bookingId);
	}
    }

    public List<Booking> getAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    return bookingDao.findAll();
	}
    }

    public List<Booking> getAllByUserId(int userId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    return bookingDao.findAllByUserId(userId);
	}
    }

    public void createBooking(Booking booking) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.create(booking.getTicket());
	    bookingDao.create(booking);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateBooking(Booking booking) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    bookingDao.update(booking);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteBooking(int bookingId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    bookingDao.delete(bookingId);
	    connection.commit();
	    connection.close();
	}
    }
}
