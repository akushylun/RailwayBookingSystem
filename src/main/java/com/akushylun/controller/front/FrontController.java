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
import com.akushylun.controller.commands.GetBookingsByUser;
import com.akushylun.controller.commands.GetLogin;
import com.akushylun.controller.commands.GetLogout;
import com.akushylun.controller.commands.GetRegistration;
import com.akushylun.controller.commands.GetTrainSearch;
import com.akushylun.controller.commands.GetTrains;
import com.akushylun.controller.commands.GetUser;
import com.akushylun.controller.commands.GetUsers;
import com.akushylun.controller.commands.PostBooking;
import com.akushylun.controller.commands.PostLogin;
import com.akushylun.controller.commands.PostRegistration;
import com.akushylun.controller.commands.PostTrain;
import com.akushylun.controller.commands.PostUser;

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
	commands.put("GET:/train/search", new GetTrainSearch());
	commands.put("POST:/train/search/findedList", new PostTrain());
	commands.put("POST:/train/search/booking", new PostBooking());
	commands.put("GET:/bookings", new GetBookingsByUser());
	commands.put("GET:/trainList", new GetTrains());
	commands.put("GET:/registration", new GetRegistration());
	commands.put("POST:/registration", new PostRegistration());
	commands.put("GET:/login", new GetLogin());
	commands.put("POST:/login", new PostLogin());
	commands.put("GET:/logout", new GetLogout());
	commands.put("POST:/user", new GetUser());
	commands.put("GET:/users", new GetUsers());
	commands.put("POST:/userUpdate", new PostUser());
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
