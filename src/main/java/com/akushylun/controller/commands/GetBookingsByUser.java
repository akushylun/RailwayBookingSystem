package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.entities.Booking;
import com.akushylun.model.services.BookingService;

public class GetBookingsByUser implements Command {

    BookingService service = BookingService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String path = request.getRequestURI();
	int userId = Integer.parseInt(path.replaceAll("\\D+", ""));
	List<Booking> bookingList = service.getAllByUserId(userId);
	if (!bookingList.isEmpty()) {
	    request.setAttribute("bookingList", bookingList);
	}
	return "/WEB-INF/view/bookingList.jsp";
    }

}
