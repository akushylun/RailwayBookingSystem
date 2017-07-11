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
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Train;
import com.akushylun.model.services.TrainService;

public class PostTrain implements Command {

    private static final String STATION_FROM = "stationFrom";
    private static final String STATION_TO = "stationTo";
    private static final String DATE_START = "date";

    private Pattern stationPatern = RegexValidator.compileRegExpression(RegexValidator.STATION);
    private Pattern datePatern = RegexValidator.compileRegExpression(RegexValidator.DATE);

    private TrainService service = TrainService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	String stationFrom = request.getParameter(STATION_FROM);
	String stationTo = request.getParameter(STATION_TO);

	String date = request.getParameter(DATE_START);
	String datePattern = "d-MMM-yyyy";
	LocalDate dateFormatted = LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern, Locale.ENGLISH));

	boolean inputTrainParamsAreValid = validateTrain(stationFrom, stationTo, dateFormatted.toString());

	if (inputTrainParamsAreValid) {
	    List<Train> trainList = service.getByAll(stationFrom, stationTo, dateFormatted);
	    request.setAttribute("trainList", trainList);
	}
	return PagePath.ROUTE;

    }

    private boolean validateTrain(String stationFrom, String stationTo, String date) {
	Matcher stationFromMatcher = stationPatern.matcher(stationFrom);
	Matcher stationToMatcher = stationPatern.matcher(stationTo);
	Matcher dateMatcher = datePatern.matcher(date);
	boolean isMatched = (stationFromMatcher.matches() && stationToMatcher.matches()
		&& dateMatcher.matches()) == true;
	return isMatched;
    }

}
