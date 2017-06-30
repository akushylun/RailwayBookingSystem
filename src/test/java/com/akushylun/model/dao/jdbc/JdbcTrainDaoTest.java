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

import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.entities.Train;

public class JdbcTrainDaoTest {
    static Connection connection;
    static TrainDao trainDao;
    static final int ID = 2;
    static final String NAME = "intercity";
    static Train train = new Train.Builder().withId(ID).withName(NAME).build();

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
	trainDao = new JdbcTrainDao(connection);
	trainDao.create(train);
    }

    @Test
    public void getByIdTest() {
	assertEquals(train, trainDao.find(train.getId()).get());
    }

    @Test
    public void getAllTest() {
	assertEquals(2, trainDao.findAll().size());
    }

    @Test
    public void createTest() {
	assertEquals(train, trainDao.find(train.getId()).get());
    }

    @Test
    public void deleteTest() {
	Train newTrain = new Train.Builder().withId(3).withName("skyTrain").build();
	trainDao.create(newTrain);
	trainDao.delete(newTrain.getId());
	assertFalse(trainDao.find(newTrain.getId()).isPresent());
    }

    @Test
    public void updateTest() {
	Train findedTrain = trainDao.find(train.getId()).get();
	Train updateTrain = new Train.Builder().withId(findedTrain.getId()).withName("huynday").build();
	trainDao.update(updateTrain);
	assertEquals(updateTrain, trainDao.find(updateTrain.getId()).get());
	trainDao.update(findedTrain);
	assertEquals(findedTrain, trainDao.find(findedTrain.getId()).get());
    }

    @AfterClass
    public static void closeConnection() throws Exception {
	connection.close();
    }
}
