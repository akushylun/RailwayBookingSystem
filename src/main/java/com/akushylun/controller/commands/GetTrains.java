package com.akushylun.controller.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.PagePath;
import com.akushylun.model.dao.exceptions.DaoException;
import com.akushylun.model.entities.Train;
import com.akushylun.model.services.TrainService;

public class GetTrains implements Command {

    TrainService service = TrainService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, DaoException {

	List<Train> trainList = service.getByAll();
	request.setAttribute("trainList", trainList);
	return PagePath.TRAIN_LIST;
    }

}
