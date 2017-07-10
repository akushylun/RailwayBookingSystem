package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;
import com.akushylun.model.services.TicketService;
import com.akushylun.model.services.TrainService;

public class GetTickets implements Command {

    TicketService ticketService = TicketService.getInstance();
    TrainService trainService = TrainService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	String pageToGo = "";

	List<Ticket> ticketList = new ArrayList<>();
	String path = request.getRequestURI();
	int bookingId = Integer.parseInt(path.replaceAll("\\D+", ""));

	ticketList = ticketService.getByBookingId(bookingId);
	for (Ticket ticket : ticketList) {
	    Train train = trainService.getById(ticket.getId()).get();
	    ticket.setTrain(train);
	    System.out.println(train);
	}
	request.setAttribute("ticketList", ticketList);
	pageToGo = "/WEB-INF/view/ticketList.jsp";

	return pageToGo;

    }
}
