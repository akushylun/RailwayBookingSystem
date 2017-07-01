package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.entities.Person;
import com.akushylun.model.services.PersonService;

public class GetPersons implements Command {

    PersonService service = PersonService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	List<Person> personsList = service.getAll();
	if (!personsList.isEmpty()) {
	    request.setAttribute("personList", personsList);
	}
	return "/WEB-INF/view/personList.jsp";
    }

}
