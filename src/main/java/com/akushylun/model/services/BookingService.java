package com.akushylun.model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.dao.exceptions.ServiceException;
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

    public Optional<Booking> getById(int bookingId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    return bookingDao.find(bookingId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Booking> getAll() throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    return bookingDao.findAll();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Booking> getAllByUserId(int userId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    return bookingDao.findAllByUserId(userId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void createBooking(Booking booking) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.create(booking.getTickets().get(0));
	    bookingDao.create(booking);
	    bookingDao.createBookingTicketsLink(booking);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void updateBooking(Booking booking) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    bookingDao.update(booking);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void deleteBooking(int bookingId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    BookingDao bookingDao = daoFactory.createBookingDao();
	    connection.begin();
	    bookingDao.delete(bookingId);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }
}
