package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.entities.Person;
import com.akushylun.model.services.PersonService;

public class Login implements Command {

    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_PASSWORD = "password";
    PersonService service = PersonService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String pageToGo = "/index.jsp";
	String email = request.getParameter(PARAM_LOGIN);
	System.out.println(email);
	String password = request.getParameter(PARAM_PASSWORD);
	System.out.println(password);
	Optional<Person> person = service.login(email, password);
	System.out.println(person.get());
	if (person.isPresent()) {
	    request.getSession().setAttribute("person", person.get());
	    pageToGo = "/WEB-INF/view/booking.jsp";
	}
	return pageToGo;
    }

}
