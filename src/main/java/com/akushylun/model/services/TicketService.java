package com.akushylun.model.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Ticket;

public class TicketService {
    private DaoFactory daoFactory;

    public TicketService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final TicketService INSTANCE = new TicketService(DaoFactory.getInstance());
    }

    public static TicketService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Ticket> getById(int ticketId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    return ticketDao.find(ticketId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Ticket> getByAll() throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    return ticketDao.findAll();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Ticket> getByBookingId(int bookingId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    return ticketDao.findAll(bookingId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Ticket> getByAll(String stationStart, String stationEnd, LocalDate startDate) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    return ticketDao.findAll(stationStart, stationEnd, startDate);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void createTicket(Ticket ticket) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.create(ticket);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void updateTicket(Ticket ticket) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.update(ticket);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void deleteTicket(int ticketId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.delete(ticketId);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }
}
