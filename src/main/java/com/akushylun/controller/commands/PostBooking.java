package com.akushylun.controller.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;
import com.akushylun.model.entities.TrainStation;
import com.akushylun.model.services.BookingService;

public class PostBooking implements Command {

    BookingService service = new BookingService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

	String trainIdParam = request.getParameter("trainId");
	String stationFromIdParam = request.getParameter("stationFrom");
	String stationToIdParam = request.getParameter("stationTo");
	String bookingPriceParam = request.getParameter("bookingPrice");

	int trainId = Integer.parseInt(trainIdParam);
	int stationFromId = Integer.parseInt(stationFromIdParam);
	int stationToId = Integer.parseInt(stationToIdParam);
	BigDecimal bookingPrice = BigDecimal.valueOf(Double.parseDouble(bookingPriceParam));

	Person person = (Person) request.getSession().getAttribute("authToken");

	Ticket ticket = new Ticket.Builder().withTrainStationList(getTrainStation(stationFromId, stationToId, trainId))
		.build();
	Booking booking = new Booking.Builder().withPrice(bookingPrice).withDate(LocalDateTime.now()).withUser(person)
		.withTicket(ticket).build();

	service.createBooking(booking);
	return PagePath.TICKET_SEARCH;

    }

    private List<TrainStation> getTrainStation(int stationFromIdParam, int stationToIdParam, int trainId) {

	List<TrainStation> trainStationList = new ArrayList<>();
	Train train = new Train.Builder().withId(trainId).build();
	Station stationFrom = new Station.Builder().withId(stationFromIdParam).build();
	Station stationTo = new Station.Builder().withId(stationToIdParam).build();
	TrainStation trainStationFrom = new TrainStation.Builder().withStation(stationFrom).withTrain(train).build();
	TrainStation trainStationTo = new TrainStation.Builder().withStation(stationTo).withTrain(train).build();
	trainStationList.add(trainStationTo);
	trainStationList.add(trainStationFrom);
	return trainStationList;

    }

}
