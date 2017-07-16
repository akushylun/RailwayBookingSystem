package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.exceptions.DaoException;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.services.TicketService;

public class GetTickets implements Command {

    TicketService ticketService = new TicketService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, DaoException {

	List<Ticket> ticketList = new ArrayList<>();
	String path = request.getRequestURI();
	int bookingId = Integer.parseInt(path.replaceAll("\\D+", ""));

	ticketList = ticketService.getByBookingId(bookingId);

	request.setAttribute("ticketList", ticketList);

	return PagePath.TICKET_INFO;

    }
}
