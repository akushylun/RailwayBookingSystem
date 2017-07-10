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
import com.akushylun.model.entities.Train;
import com.akushylun.model.exceptions.ServiceException;
import com.akushylun.model.services.TrainService;

public class PostTrain implements Command {

    private TrainService service = TrainService.getInstance();
    private static final String STATION_FROM = "stationFrom";
    private static final String STATION_TO = "stationTo";
    private static final String DATE_START = "date";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	String pageToGo = null;
	Authenticator authenticator = new AuthenticatorImpl(request);
	if (authenticator.isLoggedIn()) {
	    String stationFrom = request.getParameter(STATION_FROM);
	    String stationTo = request.getParameter(STATION_TO);
	    String dateStart = request.getParameter(DATE_START);
	    String dateStartParsedToDate = LocalDate.parse(dateStart, DateTimeFormatter.ofPattern("d-MMM-yyyy"))
		    .toString();
	    System.out.println(dateStartParsedToDate);
	    List<Train> trainList = service.getByAll(stationFrom, stationTo, dateStartParsedToDate);
	    System.out.println(trainList);
	    request.setAttribute("trainList", trainList);
	    pageToGo = "/WEB-INF/view/shedules.jsp";
	} else
	    pageToGo = "/WEB-INF/view/login.jsp";
	return pageToGo;

    }
}
