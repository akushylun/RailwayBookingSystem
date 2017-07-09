package com.akushylun.controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.model.entities.Departure;
import com.akushylun.model.services.DepartureService;

public class PostTrain implements Command {

    private DepartureService service = DepartureService.getInstance();
    private static final String STATION_FROM = "stationFrom";
    private static final String STATION_TO = "stationTo";
    private static final String DATE_START = "date";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String pageToGo = null;
	Authenticator authenticator = new AuthenticatorImpl(request);
	if (authenticator.isLoggedIn()) {
	    String stationFrom = request.getParameter(STATION_FROM);
	    String stationTo = request.getParameter(STATION_TO);
	    String dateStart = request.getParameter(DATE_START);
	    System.out.println(stationFrom);
	    System.out.println(stationTo);
	    System.out.println(dateStart);
	    LocalDate dateStartParsedToDate = LocalDate.parse(dateStart,
		    DateTimeFormatter.ofPattern("d-MMM-yyyy"));
	    System.out.println(dateStartParsedToDate);
////	    List<Departure> listShedules = service.getAllByShedulesParams(stationFrom, stationTo, dateStartParsedToDate);
//	    System.out.println(listShedules);
//	    request.setAttribute("sheduleList", listShedules);
	    pageToGo = "/WEB-INF/view/shedules.jsp";
	} else
	    pageToGo = "/WEB-INF/view/login.jsp";
	return pageToGo;

    }
}
