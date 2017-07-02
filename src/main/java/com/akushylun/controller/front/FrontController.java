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
import com.akushylun.controller.commands.GetPersons;
import com.akushylun.controller.commands.GetShedule;
import com.akushylun.controller.commands.GetShedulesByParameters;
import com.akushylun.controller.commands.Login;

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
	commands.put("GET:/shedule", new GetShedule());
	commands.put("GET:/shedules", new GetShedulesByParameters());
	commands.put("GET:/bookings", new GetBookingsByUser());
	commands.put("GET:/booking", new GetBooking());
	commands.put("GET:/persons", new GetPersons());
	commands.put("GET:/login", new GetLogin());
	commands.put("POST:/login", new Login());
	commands.put("GET:/logout", new GetLogout());
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
	Command command = commands.getOrDefault(key, (req, resp) -> "/index.jsp");
	String viewPage = command.execute(request, response);
	request.getRequestDispatcher(viewPage).forward(request, response);

    }

}
