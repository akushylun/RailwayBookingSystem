package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.model.entities.Station;
import com.akushylun.model.services.StationService;

public class GetTrain implements Command {

    StationService service = StationService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String pageToGo = null;
	Authenticator authenticator = new AuthenticatorImpl(request);
	if (authenticator.isLoggedIn()) {
	    List<Station> listStation = service.getByAll();
	    request.setAttribute("stationList", listStation);
	    pageToGo = "/WEB-INF/view/train.jsp";
	} else
	    pageToGo = "/WEB-INF/view/login.jsp";
	return pageToGo;

    }

}
