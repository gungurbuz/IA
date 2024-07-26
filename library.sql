CREATE DATABASE  IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `library`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 192.168.1.102    Database: library
-- ------------------------------------------------------
-- Server version	8.0.37-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `idauthor` int NOT NULL AUTO_INCREMENT,
  `authorfname` varchar(255) NOT NULL,
  `authorsname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idauthor`),
  UNIQUE KEY `idauthor_UNIQUE` (`idauthor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `idbook` int NOT NULL AUTO_INCREMENT,
  `bookname` varchar(1024) NOT NULL,
  `isbn` varchar(13) DEFAULT NULL,
  `genre` varchar(45) DEFAULT NULL,
  `pubyear` year DEFAULT NULL,
  `idpublisher` int DEFAULT NULL,
  PRIMARY KEY (`idbook`),
  UNIQUE KEY `idbook_UNIQUE` (`idbook`),
  KEY `idpublisher_idx` (`idpublisher`),
  CONSTRAINT `idpublisher` FOREIGN KEY (`idpublisher`) REFERENCES `publisher` (`idpublisher`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookauthors`
--

DROP TABLE IF EXISTS `bookauthors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookauthors` (
  `idbook` int NOT NULL,
  `idauthor` int NOT NULL,
  PRIMARY KEY (`idbook`,`idauthor`),
  KEY `idauthor_idx` (`idauthor`),
  CONSTRAINT `idauthor` FOREIGN KEY (`idauthor`) REFERENCES `author` (`idauthor`),
  CONSTRAINT `idbookauthors` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `booklanguages`
--

DROP TABLE IF EXISTS `booklanguages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booklanguages` (
  `idbook` int NOT NULL,
  `idlang` int NOT NULL,
  PRIMARY KEY (`idbook`,`idlang`),
  KEY `idlang_idx` (`idlang`),
  CONSTRAINT `idbooklanguages` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`),
  CONSTRAINT `idlang` FOREIGN KEY (`idlang`) REFERENCES `language` (`idlang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `idlang` int NOT NULL AUTO_INCREMENT,
  `languagename` varchar(255) NOT NULL,
  PRIMARY KEY (`idlang`),
  UNIQUE KEY `languagename_UNIQUE` (`languagename`),
  UNIQUE KEY `idlang_UNIQUE` (`idlang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `idmember` int NOT NULL,
  `takedate` date NOT NULL,
  `idbook` int NOT NULL,
  `returndate` date DEFAULT NULL,
  PRIMARY KEY (`idmember`,`takedate`,`idbook`),
  KEY `idbook_idx` (`idbook`),
  CONSTRAINT `idbookloan` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`),
  CONSTRAINT `idmember` FOREIGN KEY (`idmember`) REFERENCES `member` (`idmember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `idmember` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `sname` varchar(255) NOT NULL,
  `passhash` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `lastlogin` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idmember`),
  UNIQUE KEY `idmember_UNIQUE` (`idmember`),
  UNIQUE KEY `uname_UNIQUE` (`uname`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `idpublisher` int NOT NULL AUTO_INCREMENT,
  `publishername` varchar(255) NOT NULL,
  PRIMARY KEY (`idpublisher`),
  UNIQUE KEY `publishername_UNIQUE` (`publishername`),
  UNIQUE KEY `idpublisher_UNIQUE` (`idpublisher`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-26 17:39:14
