package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Station;
import com.akushylun.model.services.StationService;

public class GetRoute implements Command {

    StationService service = StationService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	List<Station> listStation = service.getByAll();
	request.setAttribute("stationList", listStation);

	return PagePath.ADD_ROUTE;
    }

}
