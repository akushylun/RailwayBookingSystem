package com.akushylun.controller.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.services.PersonService;

public class GetUsers implements Command {

    PersonService service = new PersonService(DaoFactory.getInstance());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

	List<Person> personList = service.getAll(Role.USER);
	request.setAttribute("personList", personList);
	return PagePath.USER_LIST;
    }

}
