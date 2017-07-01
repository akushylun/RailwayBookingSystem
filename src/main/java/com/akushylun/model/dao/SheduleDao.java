package com.akushylun.model.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.akushylun.model.entities.Shedule;

public interface SheduleDao extends GenericDao<Shedule>, AutoCloseable {

    List<Shedule> findAllByDate(LocalDateTime date);

}
