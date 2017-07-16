package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.exceptions.DaoException;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.services.BookingService;

public class GetBooking implements Command {

    BookingService service = new BookingService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, DaoException {

	String path = request.getRequestURI();
	int bookingId = Integer.parseInt(path.replaceAll("\\D+", ""));
	Optional<Booking> booking = service.getById(bookingId);
	booking.ifPresent((bookingRecord) -> request.setAttribute("booking", booking));

	return PagePath.BOOKING;

    }
}
