package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.services.PersonService;

public class GetPersons implements Command {

    PersonService service = PersonService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException {

	String pageToGo = "";
	Authenticator authenticator = new AuthenticatorImpl(request);
	Role userRole = authenticator.getLoggedPerson().getRole();

	if (authenticator.isLoggedIn() && userRole == Role.ADMIN) {
	    List<Person> personsList = service.getAll();
	    request.setAttribute("personList", personsList);
	    pageToGo = "/WEB-INF/view/personList.jsp";
	} else
	    pageToGo = "index.jsp";
	return pageToGo;
    }

}
