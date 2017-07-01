package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.entities.Booking;
import com.akushylun.model.services.BookingService;

public class GetBooking implements Command {

    BookingService service = BookingService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String path = request.getRequestURI();
	int bookingId = Integer.parseInt(path.replaceAll("\\D+", ""));
	Optional<Booking> booking = service.getById(bookingId);
	booking.ifPresent((bookingRecord) -> request.setAttribute("booking", booking));
	return "/WEB-INF/view/booking.jsp";
    }

}
