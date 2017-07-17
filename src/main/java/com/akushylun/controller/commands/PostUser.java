package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.controller.util.RegexValidator;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.services.PersonService;

public class PostUser implements Command {

    PersonService service = new PersonService(DaoFactory.getInstance());

    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_EMAIL = "email";

    private Pattern namePatern = RegexValidator.compileRegExpression(RegexValidator.NAME);
    private Pattern surnamePatern = RegexValidator.compileRegExpression(RegexValidator.SURNAME);
    private Pattern emailPatern = RegexValidator.compileRegExpression(RegexValidator.EMAIL);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	int id = Integer.parseInt(request.getParameter("id"));
	String name = request.getParameter(PARAM_NAME);
	String surname = request.getParameter(PARAM_SURNAME);
	String role = request.getParameter("role");
	String email = request.getParameter(PARAM_EMAIL);

	boolean inputPersonParamsAreValid = isValidPerson(name, surname, email, role);
	if (inputPersonParamsAreValid) {

	    Login login = new Login.Builder().withEmail(email).build();
	    Person person = new Person.Builder().withId(id).withName(name).withSurname(surname)
		    .withRole(Role.valueOf(role.toUpperCase())).withPersonLogin(login).build();

	    service.update(person);

	}
	return PagePath.USER_GET;
    }

    private boolean isValidPerson(String name, String surname, String email, String role) {
	Matcher emailMatcher = emailPatern.matcher(email);
	Matcher nameMatcher = namePatern.matcher(name);
	Matcher surnameMatcher = surnamePatern.matcher(surname);
	boolean isMatched = (emailMatcher.matches() && Role.valueOf(role).equals(Role.USER) && nameMatcher.matches()
		&& surnameMatcher.matches()) == true;
	return isMatched;
    }
}
