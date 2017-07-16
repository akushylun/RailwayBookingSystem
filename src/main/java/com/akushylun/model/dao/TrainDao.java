package com.akushylun.model.dao;

import java.time.LocalDate;
import java.util.List;

import com.akushylun.model.entities.Train;

public interface TrainDao extends GenericDao<Train> {

    List<Train> findAll(String stationStart, String stationEnd, LocalDate startDate);

}
