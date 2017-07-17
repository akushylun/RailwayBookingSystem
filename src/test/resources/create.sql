-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	5.7.17
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,NO_KEY_OPTIONS,NO_TABLE_OPTIONS,NO_FIELD_OPTIONS' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `l_id` int(11) NOT NULL auto_increment,
  `l_email` varchar(100) NOT NULL,
  `l_password` varchar(50) NOT NULL,
  PRIMARY KEY (`l_id`),
  UNIQUE KEY `l_email_UNIQUE` (`l_email`)
);







DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `p_id` int(11) NOT NULL auto_increment,
  `p_name` varchar(100) NOT NULL,
  `p_surname` varchar(100) NOT NULL,
  `p_login_l_id` int(11),
  `p_role_r_name` varchar(255) check (`p_role_r_name` in('USER', 'ADMIN')),
  PRIMARY KEY (`p_id`),
  KEY `fk_person_login1_idx` (`p_login_l_id`),
  CONSTRAINT `fk_person_login1` FOREIGN KEY (`p_login_l_id`) REFERENCES `login` (`l_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);




DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `ti_id` int(11) NOT NULL auto_increment,
  `ti_description` VARCHAR(100) NULL,
  PRIMARY KEY (`ti_id`)
);


DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `b_id` int(11) NOT NULL auto_increment,
  `b_price` numeric(15,2) NOT NULL,
  `b_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `b_person_p_id` int(11) NOT NULL,
  `b_ticket_ti_id` INT NOT NULL,
  PRIMARY KEY (`b_id`),
  KEY `fk_booking_person1` (`b_person_p_id`),
  KEY `fk_booking_ticket1_idx` (`b_ticket_ti_id`),
  CONSTRAINT `fk_booking_person1` FOREIGN KEY (`b_person_p_id`) REFERENCES `person` (`p_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_ticket1` FOREIGN KEY (`b_ticket_ti_id`) REFERENCES `ticket` (`ti_id`)ON DELETE NO ACTION ON UPDATE NO ACTION
);




DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `st_id` int(11) NOT NULL auto_increment,
  `st_name` varchar(100) NOT NULL,
  PRIMARY KEY (`st_id`)
);


DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `train` (
  `tr_id` int(11) NOT NULL auto_increment,
  `tr_name` varchar(100) NOT NULL,
  PRIMARY KEY (`tr_id`)
);


DROP TABLE IF EXISTS `departure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `departure` (
  `d_id` int(11) NOT NULL auto_increment,
  `d_datetime` TIMESTAMP NOT NULL,
  `d_train_tr_id` int(11) NOT NULL,
  PRIMARY KEY (`d_id`),
  KEY `fk_departure_train1_idx` (`d_train_tr_id`),
  CONSTRAINT `fk_departure_train1` FOREIGN KEY (`d_train_tr_id`) REFERENCES `train` (`tr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);



CREATE TABLE IF NOT EXISTS `m2m_train_station` (
  `m2m_train_station_id` INT NOT NULL AUTO_INCREMENT,
  `m2m_cost_time` INT NOT NULL,
  `m2m_cost_price` decimal(15,2) NOT NULL,
  `m2m_station_st_id` INT NOT NULL,
  `m2m_train_tr_id` INT NOT NULL,
   PRIMARY KEY (`m2m_train_station_id`),
  KEY `fk_m2m_train_idx` (`m2m_train_tr_id`),
  KEY `fk_m2m_train_station_station1` (`m2m_station_st_id`),
  CONSTRAINT `fk_m2m_shedule_station_train1`FOREIGN KEY (`m2m_train_tr_id`) REFERENCES `train` (`tr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m2m_train_station_station1`FOREIGN KEY (`m2m_station_st_id`) REFERENCES `station` (`st_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  );

CREATE TABLE IF NOT EXISTS `m2m_ticket_train_station` (
  `m2m_ticket_ti_id` INT NOT NULL,
  `m2m_m2m_train_station_id` INT NOT NULL,
  PRIMARY KEY (`m2m_ticket_ti_id`, `m2m_m2m_train_station_id`),
  KEY `fk_ticket_has_m2m_train_station_m2m_train_station1_idx` (`m2m_m2m_train_station_id` ASC),
  KEY `fk_ticket_has_m2m_train_station_ticket1_idx` (`m2m_ticket_ti_id` ASC),
  CONSTRAINT `fk_ticket_has_m2m_train_station_ticket1` FOREIGN KEY (`m2m_ticket_ti_id`) REFERENCES `ticket` (`ti_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_has_m2m_train_station_m2m_train_station1` FOREIGN KEY (`m2m_m2m_train_station_id`) REFERENCES `m2m_train_station` (`m2m_train_station_id`)ON DELETE NO ACTION ON UPDATE NO ACTION);






  
-- Dump completed on 2017-06-21 22:58:15
