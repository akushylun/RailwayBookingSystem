package com.akushylun.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.akushylun.model.entities.Train;

public interface TrainDao extends GenericDao<Train> {

    List<Train> findAll(String stationStart, String stationEnd, String startDate) throws SQLException;

}
