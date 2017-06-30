package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.akushylun.model.dao.SheduleDao;
import com.akushylun.model.entity.Shedule;
import com.akushylun.model.entity.Station;
import com.akushylun.model.entity.Train;

public class JdbcSheduleDaoTest {
    static Connection connection;
    static SheduleDao sheduleDao;
    static Station stationInH2Script = new Station.Builder().withId(1).from("kiev").to("odessa").build();
    static Train trainInH2Script = new Train.Builder().withId(1).withName("intercity").build();
    static Shedule sheduleInH2Script = new Shedule.Builder().withId(1).start(LocalDateTime.of(2017, 06, 27, 19, 53, 46))
	    .end(LocalDateTime.of(2017, 06, 27, 19, 53, 46)).withStation(stationInH2Script).withTrain(trainInH2Script)
	    .build();
    static Shedule shedule = new Shedule.Builder().withId(2).start(LocalDateTime.of(2017, 04, 23, 19, 53, 46))
	    .end(LocalDateTime.of(2017, 05, 24, 19, 53, 46)).withStation(stationInH2Script).withTrain(trainInH2Script)
	    .build();

    @BeforeClass
    public static void setH2DataBase()
	    throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	Class.forName("org.h2.Driver").newInstance();
	connection = DriverManager.getConnection("jdbc:h2:mem:;MODE=mysql;", "sa", "");
	try {
	    File script = new File(JdbcSheduleDaoTest.class.getResource("/mydb.sql").getFile());
	    RunScript.execute(connection, new FileReader(script));
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("could not initialize with script");
	}
	sheduleDao = new JdbcSheduleDao(connection);
	sheduleDao.create(shedule);
    }

    @Test
    public void getByIdTest() {
	assertEquals(shedule, sheduleDao.find(shedule.getId()).get());
    }

    @Test
    public void getAllTest() {
	assertEquals(2, sheduleDao.findAll().size());
    }

    @Test
    public void createTest() {
	assertNotNull(shedule.getId());
    }

    @Test
    public void deleteTest() {
	Shedule createShedule = new Shedule.Builder().withId(3).start(LocalDateTime.of(2017, 04, 23, 19, 53, 46))
		.end(LocalDateTime.of(2017, 05, 24, 19, 53, 46)).withStation(stationInH2Script)
		.withTrain(trainInH2Script).build();
	sheduleDao.create(createShedule);
	assertNotNull(createShedule.getId());
	sheduleDao.delete(createShedule.getId());
	assertEquals(2, sheduleDao.findAll().size());
    }

    @Test
    public void updateTest() {
	LocalDateTime newDateTime = LocalDateTime.now();
	Shedule newShedule = new Shedule.Builder().withId(sheduleInH2Script.getId()).start(newDateTime).end(newDateTime)
		.withStation(stationInH2Script).withTrain(trainInH2Script).build();
	sheduleDao.update(newShedule);
	assertEquals(newShedule.getStart(), sheduleDao.find(newShedule.getId()).get().getStart());
	assertEquals(newShedule.getEnd(), sheduleDao.find(newShedule.getId()).get().getEnd());
	sheduleDao.update(sheduleInH2Script);
	assertEquals(sheduleInH2Script.getStart(), sheduleDao.find(sheduleInH2Script.getId()).get().getStart());
	assertEquals(sheduleInH2Script.getEnd(), sheduleDao.find(sheduleInH2Script.getId()).get().getEnd());
    }

    @AfterClass
    public static void closeConnection() throws Exception {
	connection.close();
    }
}
