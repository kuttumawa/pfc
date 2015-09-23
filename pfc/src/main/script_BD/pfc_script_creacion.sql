CREATE DATABASE  IF NOT EXISTS `pfc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pfc`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: pfc
-- ------------------------------------------------------
-- Server version	5.6.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `codigo`
--

DROP TABLE IF EXISTS `codigo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `codigo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(5000) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `whatToShare` int(11) DEFAULT NULL,
  `test_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8ql5ta6dfu7upmj3ow97rk87d` (`test_id`),
  CONSTRAINT `FK_8ql5ta6dfu7upmj3ow97rk87d` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigo`
--

LOCK TABLES `codigo` WRITE;
/*!40000 ALTER TABLE `codigo` DISABLE KEYS */;
/*!40000 ALTER TABLE `codigo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigo_propietarios`
--

DROP TABLE IF EXISTS `codigo_propietarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `codigo_propietarios` (
  `Codigo_id` bigint(20) NOT NULL,
  `propietarios` bigint(20) DEFAULT NULL,
  KEY `FK_c3vanmqgrc1haee1esnhi85r5` (`Codigo_id`),
  CONSTRAINT `FK_c3vanmqgrc1haee1esnhi85r5` FOREIGN KEY (`Codigo_id`) REFERENCES `codigo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigo_propietarios`
--

LOCK TABLES `codigo_propietarios` WRITE;
/*!40000 ALTER TABLE `codigo_propietarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `codigo_propietarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyecto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tm7s78mp2f4q5wt8x8oxhyqix` (`user_id`),
  CONSTRAINT `FK_tm7s78mp2f4q5wt8x8oxhyqix` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto_codigo`
--

DROP TABLE IF EXISTS `proyecto_codigo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyecto_codigo` (
  `proyecto_id` bigint(20) NOT NULL,
  `codigos_id` bigint(20) NOT NULL,
  KEY `FK_o51kpqof2o6nm2o68s3cs3inf` (`codigos_id`),
  KEY `FK_hmsu0lybhhu1pksmfhgri17b6` (`proyecto_id`),
  CONSTRAINT `FK_hmsu0lybhhu1pksmfhgri17b6` FOREIGN KEY (`proyecto_id`) REFERENCES `proyecto` (`id`),
  CONSTRAINT `FK_o51kpqof2o6nm2o68s3cs3inf` FOREIGN KEY (`codigos_id`) REFERENCES `codigo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto_codigo`
--

LOCK TABLES `proyecto_codigo` WRITE;
/*!40000 ALTER TABLE `proyecto_codigo` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyecto_codigo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(5000) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `pasado` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-14 22:32:56
