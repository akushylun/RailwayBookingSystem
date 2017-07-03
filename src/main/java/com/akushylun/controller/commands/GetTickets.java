package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.services.TicketService;

public class GetTickets implements Command {

    TicketService service = TicketService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String pageToGo = "";
	Authenticator authenticator = new AuthenticatorImpl(request);
	List<Ticket> ticketList = new ArrayList<>();
	String path = request.getRequestURI();
	int bookingId = Integer.parseInt(path.replaceAll("\\D+", ""));

	if (authenticator.isLoggedIn()) {
	    ticketList = service.getAllByBookingId(bookingId);
	    request.setAttribute("ticketList", ticketList);
	    pageToGo = "/WEB-INF/view/ticketList.jsp";
	} else
	    pageToGo = "/WEB-INF/view/login.jsp";
	return pageToGo;

    }
}
