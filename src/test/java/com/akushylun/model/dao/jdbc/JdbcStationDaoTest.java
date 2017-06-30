package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.akushylun.model.dao.StationDao;
import com.akushylun.model.entity.Station;

public class JdbcStationDaoTest {
    static Connection connection;
    static StationDao stationDao;
    static final int ID = 2;
    static final String FROM_STATION = "Kyiv";
    static final String TO_STATION = "ODESSA";
    static Station station = new Station.Builder().withId(ID).from(FROM_STATION).to(TO_STATION).build();

    @BeforeClass
    public static void setH2DataBase()
	    throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	Class.forName("org.h2.Driver").newInstance();
	connection = DriverManager.getConnection("jdbc:h2:mem:;MODE=mysql;", "sa", "");
	try {
	    File script = new File(JdbcPersonDaoTest.class.getResource("/mydb.sql").getFile());
	    RunScript.execute(connection, new FileReader(script));
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("could not initialize with script");
	}
	stationDao = new JdbcStationDao(connection);
	stationDao.create(station);
    }

    @Test
    public void getByIdTest() {
	assertEquals(station, stationDao.find(station.getId()).get());
    }

    @Test
    public void getAllTest() {
	assertEquals(2, stationDao.findAll().size());
    }

    @Test
    public void createTest() {
	assertEquals(station, stationDao.find(station.getId()).get());
    }

    @Test
    public void deleteTest() {
	Station newStation = new Station.Builder().withId(3).from("Lviv").from("DONETSK").build();
	stationDao.create(newStation);
	stationDao.delete(newStation.getId());
	assertFalse(stationDao.find(newStation.getId()).isPresent());
    }

    @Test
    public void updateTest() {
	Station findedStation = stationDao.find(station.getId()).get();
	Station updateStation = new Station.Builder().withId(findedStation.getId()).from("Chercassy").to("Chernigiv")
		.build();
	stationDao.update(updateStation);
	assertEquals(updateStation, stationDao.find(updateStation.getId()).get());
	stationDao.update(findedStation);
	assertEquals(findedStation, stationDao.find(findedStation.getId()).get());
    }

    @AfterClass
    public static void closeConnection() throws Exception {
	connection.close();
    }
}
