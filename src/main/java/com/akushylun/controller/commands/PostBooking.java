package com.akushylun.controller.commands;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.exceptions.DaoException;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;
import com.akushylun.model.services.BookingService;

public class PostBooking implements Command {

    BookingService service = new BookingService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, DaoException {

	String ticketIdParam = request.getParameter("ticketId");
	String ticketPriceParam = request.getParameter("ticketPrice");

	int ticketId = Integer.parseInt(ticketIdParam);
	Person person = (Person) request.getSession().getAttribute("authToken");
	BigDecimal orderPrice = BigDecimal.valueOf(Double.parseDouble(ticketPriceParam));

	Train train = new Train.Builder().withId(ticketId).build();
	Ticket ticket = new Ticket.Builder().withTrain(train).withPrice(orderPrice).build();
	List<Ticket> ticketList = new ArrayList<>();
	ticketList.add(ticket);
	Booking booking = new Booking.Builder().withPrice(orderPrice).withDate(LocalDateTime.now()).withUser(person)
		.withTickets(ticketList).build();

	service.createBooking(booking);
	return PagePath.BOOKING_LIST;

    }

}
