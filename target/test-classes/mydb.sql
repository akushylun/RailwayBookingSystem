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

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `l_id` int(11) NOT NULL auto_increment,
  `l_login` varchar(45) NOT NULL,
  `l_password` varchar(45) NOT NULL,
  PRIMARY KEY (`l_id`),
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

--LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('1','mark123','mark1990');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
--UNLOCK TABLES;

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `p_id` int(11) NOT NULL auto_increment,
  `p_name` varchar(45) NOT NULL,
  `p_surname` varchar(45) NOT NULL,
  `p_email` varchar(45) NOT NULL,
  `p_login_l_id` int(11),
  `p_role_r_name` varchar(255) check (`p_role_r_name` in('USER', 'ADMIN')),
  PRIMARY KEY (`p_id`),
  UNIQUE KEY `p_email_UNIQUE` (`p_email`),
  KEY `fk_person_login1_idx` (`p_login_l_id`),
  CONSTRAINT `fk_person_login1` FOREIGN KEY (`p_login_l_id`) REFERENCES `login` (`l_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

--LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'mark','johnson','mark@gmail.com',1,'USER');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
--UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `b_id` int(11) NOT NULL auto_increment,
  `b_price` decimal(15,2) NOT NULL,
  `b_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `b_person_p_id` int(11) NOT NULL,
  PRIMARY KEY (`b_id`),
  KEY `fk_booking_person1` (`b_person_p_id`),
  CONSTRAINT `fk_booking_person1` FOREIGN KEY (`b_person_p_id`) REFERENCES `person` (`p_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `booking` VALUES (1,100.00,'2017-06-27 19:58:27',1);

--
-- Dumping data for table `booking`
--

--
-- Dumping data for table `seat`
--

--LOCK TABLES `seat` WRITE;
--/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
--/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
--UNLOCK TABLES;

-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `st_id` int(11) NOT NULL auto_increment,
  `st_from` varchar(45) NOT NULL,
  `st_to` varchar(45) NOT NULL,
  PRIMARY KEY (`st_id`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `station` VALUES (1,'kiev','odessa');

--
-- Dumping data for table `station`
--

--LOCK TABLES `station` WRITE;
--/*!40000 ALTER TABLE `station` DISABLE KEYS */;
--/*!40000 ALTER TABLE `station` ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `train` (
  `tr_id` int(11) NOT NULL auto_increment,
  `tr_name` varchar(45) NOT NULL,
  PRIMARY KEY (`tr_id`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `train` VALUES (1,'intercity');

--
-- Dumping data for table `train`
--
--
--LOCK TABLES `train` WRITE;
--/*!40000 ALTER TABLE `train` DISABLE KEYS */;
--/*!40000 ALTER TABLE `train` ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table `shedule`
--

DROP TABLE IF EXISTS `shedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shedule` (
  `sh_id` int(11) NOT NULL auto_increment,
  `sh_start` timestamp NULL DEFAULT NULL,
  `sh_end` timestamp NULL DEFAULT NULL,
  `sh_station_st_id` int(11) NOT NULL,
  `sh_train_tr_id` int(11) NOT NULL,
  PRIMARY KEY (`sh_id`),
  KEY `fk_shedule_station1_idx` (`sh_station_st_id`),
  KEY `fk_shedule_train1_idx` (`sh_train_tr_id`),
  CONSTRAINT `fk_shedule_station1` FOREIGN KEY (`sh_station_st_id`) REFERENCES `station` (`st_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shedule_train1` FOREIGN KEY (`sh_train_tr_id`) REFERENCES `train` (`tr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `shedule` VALUES (1,'2017-06-27 19:53:46','2017-06-27 19:53:46',1,1);

--
-- Dumping data for table `shedule`
--

--LOCK TABLES `shedule` WRITE;
--/*!40000 ALTER TABLE `shedule` DISABLE KEYS */;
--/*!40000 ALTER TABLE `shedule` ENABLE KEYS */;
--UNLOCK TABLES;

--


--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `ti_id` int(11) NOT NULL auto_increment,
  `ti_price` decimal(15,2) NOT NULL,
  `ti_shedule_sh_id` int(11) NOT NULL,
  PRIMARY KEY (`ti_id`),
  KEY `fk_ticket_shedule1_idx` (`ti_shedule_sh_id`),
  CONSTRAINT `fk_ticket_shedule1` FOREIGN KEY (`ti_shedule_sh_id`) REFERENCES `shedule` (`sh_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `ticket` VALUES (1,100.00,1);

--
-- Dumping data for table `ticket`
--

--LOCK TABLES `ticket` WRITE;
--/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
--/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
--UNLOCK TABLES;

--
-- Table structure for table `m2m_booking_ticket`
--

DROP TABLE IF EXISTS `m2m_booking_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m2m_booking_ticket` (
  `m2m_booking_b_id` int(11) NOT NULL,
  `m2m_ticket_ti_id` int(11) NOT NULL,
  KEY `fk_m2m_booking_ticket_booking1_idx` (`m2m_booking_b_id`),
  KEY `fk_m2m_booking_ticket_ticket1_idx` (`m2m_ticket_ti_id`),
  CONSTRAINT `fk_m2m_booking_ticket_booking1` FOREIGN KEY (`m2m_booking_b_id`) REFERENCES `booking` (`b_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m2m_booking_ticket_ticket1` FOREIGN KEY (`m2m_ticket_ti_id`) REFERENCES `ticket` (`ti_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO `m2m_booking_ticket` VALUES (1,1);

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m2m_booking_ticket`
--

--LOCK TABLES `booking` WRITE;
--/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
--/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
--UNLOCK TABLES;

--LOCK TABLES `m2m_booking_ticket` WRITE;
--/*!40000 ALTER TABLE `m2m_booking_ticket` DISABLE KEYS */;
--/*!40000 ALTER TABLE `m2m_booking_ticket` ENABLE KEYS */;
--UNLOCK TABLES;

-- Dump completed on 2017-06-21 22:58:15
