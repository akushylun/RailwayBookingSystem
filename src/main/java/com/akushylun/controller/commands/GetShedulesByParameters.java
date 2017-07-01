package com.akushylun.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.services.SheduleService;

public class GetShedulesByParameters implements Command {

    SheduleService service = SheduleService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

	return "/WEB-INF/view/sheduleListByParam.jsp";
    }

}
