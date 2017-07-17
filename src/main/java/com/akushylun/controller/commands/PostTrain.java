package com.akushylun.controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.controller.util.RegexValidator;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.entities.Train;
import com.akushylun.model.services.TrainService;

public class PostTrain implements Command {

    private static final String STATION_FROM = "stationFrom";
    private static final String STATION_TO = "stationTo";
    private static final String DATE_START = "date";

    private Pattern stationPattern = RegexValidator.compileRegExpression(RegexValidator.STATION);
    private Pattern datePattern = RegexValidator.compileRegExpression(RegexValidator.DATE);

    private TrainService service = new TrainService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String stationFrom = request.getParameter(STATION_FROM);
	String stationTo = request.getParameter(STATION_TO);

	String date = request.getParameter(DATE_START);
	String datePattern = "d-MMM-yyyy";

	boolean inputTrainParamsAreValid = validateTrain(stationFrom, stationTo, date);

	if (inputTrainParamsAreValid) {
	    LocalDate dateFormatted = LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern, Locale.ENGLISH));
	    List<Train> trainList = service.getByAll(stationFrom, stationTo, dateFormatted);
	    request.setAttribute("trainList", trainList);
	}
	return PagePath.TICKET_SEARCH_LIST;

    }

    private boolean validateTrain(String stationFrom, String stationTo, String date) {
	Matcher stationFromMatcher = stationPattern.matcher(stationFrom);
	Matcher stationToMatcher = stationPattern.matcher(stationTo);
	Matcher dateMatcher = datePattern.matcher(date);
	boolean isMatched = (stationFromMatcher.matches() && stationToMatcher.matches()
		&& dateMatcher.matches()) == true;
	return isMatched;
    }

}
