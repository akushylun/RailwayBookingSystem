package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.entities.Shedule;
import com.akushylun.model.services.SheduleService;

public class GetShedule implements Command {

    SheduleService service = SheduleService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String path = request.getRequestURI();
	int sheduleId = Integer.parseInt(path.replaceAll("\\D+", ""));
	Optional<Shedule> shedule = service.getById(sheduleId);
	shedule.ifPresent((sheduleRecord) -> request.setAttribute("shedule", shedule));
	return "/WEB-INF/view/shedule.jsp";
    }

}
