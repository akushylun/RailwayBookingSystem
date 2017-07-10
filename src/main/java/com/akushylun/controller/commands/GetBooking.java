package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.constants.PagePath;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.services.BookingService;

public class GetBooking implements Command {

    BookingService service = BookingService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	String path = request.getRequestURI();
	int bookingId = Integer.parseInt(path.replaceAll("\\D+", ""));
	Optional<Booking> booking = service.getById(bookingId);
	booking.ifPresent((bookingRecord) -> request.setAttribute("booking", booking));

	return PagePath.BOOKING;

    }
}
