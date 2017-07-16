package com.akushylun.controller.front;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.commands.Command;
import com.akushylun.controller.commands.GetBooking;
import com.akushylun.controller.commands.GetBookingsByUser;
import com.akushylun.controller.commands.GetLogin;
import com.akushylun.controller.commands.GetLogout;
import com.akushylun.controller.commands.GetPerson;
import com.akushylun.controller.commands.GetPersons;
import com.akushylun.controller.commands.GetRegistration;
import com.akushylun.controller.commands.GetTicketSearch;
import com.akushylun.controller.commands.GetTrains;
import com.akushylun.controller.commands.PostBooking;
import com.akushylun.controller.commands.PostLogin;
import com.akushylun.controller.commands.PostPerson;
import com.akushylun.controller.commands.PostRegistration;
import com.akushylun.controller.commands.PostTrain;
import com.akushylun.model.dao.exceptions.DaoException;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(name = "frontController", urlPatterns = { "/view/*" })
public class FrontController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Map<String, Command> commands = new HashMap<>();

    public FrontController() {
    }

    @Override
    public void init() {
	commands.put("GET:/ticket/search", new GetTicketSearch());
	commands.put("POST:/ticket/search/findedList", new PostTrain());
	commands.put("GET:/bookings", new GetBookingsByUser());
	commands.put("GET:/booking", new GetBooking());
	commands.put("POST:/ticket/search/booking", new PostBooking());
	commands.put("GET:/registration", new GetRegistration());
	commands.put("POST:/registration", new PostRegistration());
	commands.put("GET:/persons", new GetPersons());
	commands.put("GET:/login", new GetLogin());
	commands.put("POST:/login", new PostLogin());
	commands.put("GET:/logout", new GetLogout());
	commands.put("GET:/trainList", new GetTrains());
	commands.put("POST:/person", new GetPerson());
	commands.put("POST:/updatePerson", new PostPerson());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String method = request.getMethod().toUpperCase();
	String path = request.getRequestURI();
	path = path.replaceAll(".*/view", "").replaceAll("\\d+", "");
	String key = method + ":" + path;
	System.out.println(key);
	Command command = commands.getOrDefault(key, (req, resp) -> "/index.jsp");
	String viewPage = null;
	try {
	    viewPage = command.execute(request, response);
	} catch (DaoException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	request.getRequestDispatcher(viewPage).forward(request, response);

    }

}
