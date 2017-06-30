package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.entity.Ticket;

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

    public Optional<Ticket> getById(int ticketId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    return ticketDao.find(ticketId);
	}
    }

    public List<Ticket> getByAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    return ticketDao.findAll();
	}
    }

    public void createTicket(Ticket ticket) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.create(ticket);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateTicket(Ticket ticket) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.update(ticket);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteTicket(int ticketId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TicketDao ticketDao = daoFactory.createTicketDao();
	    connection.begin();
	    ticketDao.delete(ticketId);
	    connection.commit();
	    connection.close();
	}
    }
}
