package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.Authenticator;
import com.akushylun.controller.util.AuthenticatorImpl;
import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Person;
import com.akushylun.model.services.BookingService;

public class GetBookingsByUser implements Command {

    private BookingService service = new BookingService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	Authenticator authenticator = new AuthenticatorImpl(request);
	List<Booking> bookingList = new ArrayList<>();

	Person person = authenticator.getLoggedPerson();
	int userId = person.getId();
	bookingList = service.getAllByUserId(userId);
	request.setAttribute("bookingList", bookingList);

	return PagePath.BOOKING_LIST;

    }
}
