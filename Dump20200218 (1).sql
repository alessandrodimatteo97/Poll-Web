CREATE DATABASE  IF NOT EXISTS `PollWeb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `PollWeb`;
-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: PollWeb
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IDQ` int(10) unsigned NOT NULL,
  `ID_P` int(10) unsigned NOT NULL,
  `texta` json NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDQ` (`IDQ`),
  KEY `ID_P` (`ID_P`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`IDQ`) REFERENCES `question` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`ID_P`) REFERENCES `participant` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,37,130,'{\"1\": \"cazzi\"}'),(2,19,241,'{\"1\": \"a\"}'),(3,20,241,'{\"1\": \"\"}'),(4,22,241,'{\"1\": \"\"}'),(5,25,241,'{\"1\": \"\"}'),(6,19,242,'{\"1\": \"\"}'),(7,20,242,'{\"1\": \"\"}'),(8,22,242,'{\"1\": \"\"}'),(9,25,242,'{\"1\": \"\"}'),(10,19,243,'{\"1\": \"\"}'),(11,20,243,'{\"1\": \"\"}'),(12,22,243,'{\"1\": \"\"}'),(13,23,243,'{\"1\": \"22/02/2020\"}'),(14,25,243,'{\"1\": \"\"}'),(15,12,8,'{\"A\": \"pubblica\"}'),(16,16,8,'{\"1\": \"2\"}'),(17,11,8,'{\"1\": \"pubblica\"}'),(18,14,8,'{\"1\": \"si\"}'),(19,15,8,'{\"1\": \"no\"}'),(20,17,8,'{\"1\": \"informatica\"}'),(21,19,244,'{\"1\": \"\"}'),(22,25,244,'{\"1\": \"\"}'),(23,19,245,'{\"1\": \"\"}'),(24,25,245,'{\"1\": \"\"}'),(25,19,246,'{\"1\": \"\"}'),(26,25,246,'{\"1\": \"\"}');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participant` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `apiKey` varchar(100) DEFAULT NULL,
  `nameP` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `pwd` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `apiKey` (`apiKey`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES (5,'dd313032-a678-4515-a910-6c3d09d9cf3b','Erika','erika@gmail.com','f7f7591403c6c431053920223069550a'),(6,'991c4c93-e2e5-4837-baed-eee19fdf56f3','Miriana','miriana@gmail.com','d8cb78d5b91cf5725381e19dbde9593f'),(7,'fd32d4c6-3813-496c-a68c-53b0a9df0d7d','Luca','luca@gmail.com','ff377aff39a9345a9cca803fb5c5c081'),(8,'cJyn2kZwoX2wFsPb5WDOhBVprTJD0ctQukFQDAyj',NULL,NULL,NULL),(9,'5b9eb355-21e8-4996-9dca-a8a27349df56',NULL,'carlo@gmail.com',NULL),(10,'15a43e1c-e32a-4575-997c-64fd02159e1c',NULL,'rinco@gmail.com',NULL),(11,'331d6555-8f34-48ea-921c-be26f769b4bd',NULL,'shelly@gmail.com',NULL),(12,'6ff6c349-8ca9-4711-86cb-494fb84919e3','Laura','laura@gmail.com','laura'),(13,'f0d43d10-bed8-423b-8705-b1c160ccfb53','Angelo','angelo@gmail.com','680e89809965ec41e64dc7e447f175ab'),(14,'5b960074-6e81-4964-8db7-dc1f46b81a30','Marco','marco@gmail.com','f5888d0bb58d611107e11f7cbc41c97a'),(15,'a65f7145-5941-4eaa-99a9-5a8301e12686','Andrea','andrea@gmail.com','1c42f9c1ca2f65441465b43cd9339d6c'),(16,'efa460e2-0f06-4bd0-8840-a89c2866c2c5','Fabio','fabio@gmail.com','a53bd0415947807bcb95ceec535820ee'),(17,'ff63c492-a661-473c-a702-2e7700646132','Elvis','elvis@gmail.com','8b28c7134887bb938e1ffed68456ffb2'),(28,NULL,NULL,'alessandro@gmail.com','alessandro'),(29,NULL,NULL,'bnbb@anai.it','asdfsa'),(130,NULL,NULL,'giorgia@gmail.com','giorgia'),(189,NULL,'gianni','gianni@gmail.com','gianni'),(190,NULL,'ernesto','ernesto@gmail.com','ernesto'),(191,NULL,'franco','franco@gmail.com','franco'),(192,NULL,'andrea','andrea@gmail.com','andrea'),(193,NULL,'giampiero','giampiero@gmail.com','giampiero'),(194,NULL,'giorgio','giorgio@gmail.com','giorgio'),(195,NULL,'cesidio','cesidio@gmail.com','cesidio'),(196,NULL,'matilde','matilde@gmai.com','matilde'),(197,NULL,'elvira','elvira@gmail.com','elvira'),(198,NULL,'sergio','sergio@gmail.com','sergio'),(222,'DX8M4SAVPlOjpM0raHfYJ2DXNOFERC8LIlFyKM9r',NULL,NULL,NULL),(223,'OGJu0l8H4EU0bNnt4j9L6196Q65vq5kk543hBIs4',NULL,NULL,NULL),(224,'NIEUOe6sjWP6TR7VXog7ReVU9DvlE18nUoFLbQRs',NULL,NULL,NULL),(225,'AAUY2TtAEf4gdwP7YFMMu4NO5Fj32OmhjPBjxmBu',NULL,NULL,NULL),(226,'ggziHrHZz5F06BKHm8Lqh5oVt3R9BKsqAuZM7ZJj',NULL,NULL,NULL),(227,'otUttMfrWkpaQ4ZTzY7FHO1NDuebd2zo9haWZO22',NULL,NULL,NULL),(228,'eimDU0AzBCBP6IVnJI6u7Pli3zyBfgKGSWKaIEgy',NULL,NULL,NULL),(229,'9bvv1pTTtRRfmIouKun75UHeCoglZ7t5YNtiPhsY',NULL,NULL,NULL),(230,'oNssfAr9xurWhXVreAMnJc6gZz74piNTHSHz0Iuv',NULL,NULL,NULL),(231,'5nPUMtLi2JfLpU7HEhXWw41f7yn8kgZBlgXnNuwI',NULL,NULL,NULL),(232,'WrTjlFCMNQphuSKMOLQaQsYLyVAgheNvg6r7bUiB',NULL,NULL,NULL),(233,'1sXgVMC6Au1seEcoj441YXCGZlkc96TWUS2YXCpY',NULL,NULL,NULL),(234,'cUTC4q64lh1hMOMpJP6fauzfTInNkIwf2CibY1zj',NULL,NULL,NULL),(235,'DOlnWvxVjPlgB41GaCE2QLNb8225cVvLrWmXVxcu',NULL,NULL,NULL),(236,'wqudkvKvmBa5OPT8zru3xs36Cmw2w23iNb09tTpA',NULL,NULL,NULL),(237,'vpYiULjWD7u7vEZDNxELV9o9ZSUEWGdmTZHEuzlr',NULL,NULL,NULL),(238,'aKHnukKbFah8M4r0ssKoo4ACOwrPJstxPLu44nE4',NULL,NULL,NULL),(239,'pw7O8hszgmQDWpgFJ3pc2SVlFq3s5bnw5LqTXqCL',NULL,NULL,NULL),(240,'AU5kv5gjbVvz1czFSS6kjphubPlFXPtmq0GkBbyM',NULL,NULL,NULL),(241,'1m9S7rv7j71LfD5vd0oWgnad6hj6zWqElNXOL7Yi',NULL,NULL,NULL),(242,'MwB1Fvj2xA9OyMN9Nv22JiTLp9eRKyRzs2a7sET3',NULL,NULL,NULL),(243,'yRXfvHPQ65OVEIcroKuuKth7TmGaTsM8Yn9Afxna',NULL,NULL,NULL),(244,'nyVGNMau2GXfm0LpOAwtsLjgwV8JXXxc1HKDIXDX',NULL,NULL,NULL),(245,'yAYwxbAWpqMhgrQ5ApwHmN43awlZP1rAuDvidTdc',NULL,NULL,NULL),(246,'BGNIEOLIkHRQRAwyJjGYc9uOAP0LFI3wJlr9odcC',NULL,NULL,NULL),(247,NULL,'Angelo','angelo.dalfonso@student.univaq.it','angelo');
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participation` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_poll` int(10) unsigned NOT NULL,
  `ID_part` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_poll` (`ID_poll`),
  KEY `ID_part` (`ID_part`),
  CONSTRAINT `participation_ibfk_1` FOREIGN KEY (`ID_poll`) REFERENCES `poll` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `participation_ibfk_2` FOREIGN KEY (`ID_part`) REFERENCES `participant` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` VALUES (1,5,5),(2,5,6),(3,5,7),(4,5,12),(5,5,13),(6,5,14),(7,5,15),(8,5,16),(9,5,17),(10,3,8),(11,3,9),(12,4,10),(23,9,28),(24,9,29),(126,13,130),(185,3,189),(186,3,190),(187,3,191),(188,3,192),(189,3,193),(190,3,194),(191,3,195),(192,3,196),(193,3,197),(194,3,198),(207,4,241),(208,4,242),(209,4,243),(210,4,244),(211,4,245),(212,4,246),(213,20,247);
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poll`
--

DROP TABLE IF EXISTS `poll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poll` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `apertureText` varchar(500) DEFAULT NULL,
  `closerText` varchar(200) DEFAULT NULL,
  `typeP` enum('open','reserved') DEFAULT 'open',
  `url` varchar(100) DEFAULT NULL,
  `activated` enum('yes','no') NOT NULL DEFAULT 'no',
  `alreadyActivated` enum('yes','no') NOT NULL DEFAULT 'no',
  `idR` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `title` (`title`),
  UNIQUE KEY `url` (`url`),
  KEY `idR` (`idR`),
  CONSTRAINT `poll_ibfk_1` FOREIGN KEY (`idR`) REFERENCES `responsibleuser` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poll`
--

LOCK TABLES `poll` WRITE;
/*!40000 ALTER TABLE `poll` DISABLE KEYS */;
INSERT INTO `poll` VALUES (3,'Universitàa','Rispondi alle domande sulla tua università','Grazie per la partecipazione','reserved','localhost:8080/PollWeb/Università','no','yes',3),(4,'Scuola','Rispondi alle domande sulla scuola','Grazie per la partecipazione','open','/Scuola','yes','yes',4),(5,'Lavoro','Rispondi alle domande sul tuo lavoro','Grazie per la partecipazione','reserved','/Lavoro','no','no',5),(6,'Casa','Rispondi alle domande sulla tua casa','Grazie per la partecipazione','open','/Casa','yes','yes',3),(7,'Famiglia','Rispondi alle domande sulla tua famiglia','Grazie per la partecipazione','open','/Famiglia','no','no',4),(8,'Amici','Rispondi alle domande sui tuoi amici','Grazie per la partecipazione','open','localhost:8080/PollWeb//Amici','no','yes',3),(9,'cioa come stai? a','bene bene, tu? ','molto bne','reserved','localhost:8080/PollWeb/azz','yes','yes',3),(10,'ciao come vabene?','fghjk','ghjk','open','localhost:8080/PollWeb/vbn','yes','yes',3),(12,'ciao come vaabene?','fghjk','ghjk','open','localhost:8080/PollWeb/vbna','yes','yes',3),(13,'ciao come vai?','fghjkl','fghjkl','open','localhost:8080/PollWeb/fghjkl','no','yes',3),(14,'Provaaaaaaaaaaa','asdfgfwq','qsdfdq','reserved','localhost:8080/PollWeb/asdfvdsa','no','yes',3),(15,'aaa','aaa','aaa','open','localhost:8080/PollWeb/aaa','no','yes',3),(16,'a','a','a','reserved','localhost:8080/PollWeb/a','no','yes',3),(18,'aaaa','a','a','open','localhost:8080/PollWeb/aaaa','yes','yes',3),(19,'aaaaaaaaaa','aaaaaaaaaa','aaaaaaaaaa','open','localhost:8080/PollWeb/aaaaaaaaaa','yes','no',3),(20,'bbbbb','bbbbb','bbbbbb','open','localhost:8080/PollWeb/bbbb','no','no',3),(22,'bav','av','a','reserved','localhost:8080/PollWeb/bav','no','no',3),(23,'n','n','n','open','localhost:8080/PollWeb/n','no','no',3),(24,'cazz','cazz','cazz','open','localhost:8080/PollWeb/czz','no','no',6),(25,'babbo','BABBO','BABBO','open','localhost:8080/PollWeb/BABBO','no','no',6);
/*!40000 ALTER TABLE `poll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `typeq` enum('short text','long text','numeric','date','single choice','multiple choice') NOT NULL,
  `textq` varchar(500) NOT NULL,
  `note` varchar(200) DEFAULT NULL,
  `obbligation` enum('yes','no') NOT NULL DEFAULT 'no',
  `possible_answer` json DEFAULT NULL,
  `number` int(11) NOT NULL DEFAULT '0',
  `IDP` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDP` (`IDP`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`IDP`) REFERENCES `poll` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (11,'short text','Quale università frequenti? ',NULL,'no',NULL,3,3),(12,'single choice','Che tipo di univesità è?',NULL,'no','{\"A\": \"pubblica\", \"B\": \"privata\"}',1,3),(14,'short text','Segui le lezioni?',NULL,'no','{\"A\": \"Poco\", \"B\": \"Abbastanza\", \"C\": \"Sempre\", \"D\": \"Per niente\"}',4,3),(15,'short text','Sei soddisfatto della tua università?',NULL,'no','{\"A\": \"Poco\", \"B\": \"Abbastanza\", \"C\": \"Molto\", \"D\": \"Per niente\"}',6,3),(16,'numeric','A che anno sei?','a','no',NULL,2,3),(17,'long text','Che facoltà frequenti?',NULL,'no',NULL,7,3),(18,'single choice','Che tipo di corso è?','Domanda obbligatoria','no','{\"1\": \"laurea triennale\", \"2\": \"laurea specialistica\", \"3\": \"master\", \"4\": \"ciclo unico\"}',5,3),(19,'short text','Come si chiama la tua scuola?','rispondere massimo una riga','no',NULL,0,4),(20,'long text','Dove si trova?','rispondere su più righe','no',NULL,0,4),(21,'multiple choice','A che grado di istruzione sei?','selezionare le risposte che si desiderano','no','{\"A\": \"scuola primaria\", \"B\": \"scuola secondaria di primo grado\", \"C\": \"scuola secondaria di secondo grado\", \"D\": \"altro\"}',0,4),(22,'numeric','A che anno ti trovi? ','rispondere con valore numerico','no',NULL,0,4),(23,'date','In che anno hai iniziato la tua scuola?','inserire una data','no',NULL,0,4),(24,'single choice','Sei mai stato rimandato? ','dare massimo una risposta','no','{\"A\": \"Si\", \"B\": \"No\"}',0,4),(25,'short text','Sei mai stato bocciato?','rispondere massimo una riga','no','{\"A\": \"Si\", \"B\": \"No\"}',0,4),(26,'long text','Che lavoro fai? ',NULL,'yes',NULL,0,5),(27,'short text','Come si chiama l’azienda dove lavori? ','Rispondere solamente se si lavora in un’azienda','no',NULL,0,5),(28,'single choice','Sei soddisfatto del tuo lavoro?',NULL,'yes','{\"A\": \"Per niente\", \"B\": \"Poco\", \"C\": \"Abbastanza\", \"D\": \"Molto\"}',0,5),(29,'long text','Spiegare il motivo della risposta alla domanda “sei soddisfatto del tuo lavoro?”','Tranquillo che il sondaggio è anonimo','yes',NULL,0,5),(30,'short text','Come pensi possa migliorare il tuo lavoro? ','Rispondere con sincerità','no',NULL,0,5),(31,'single choice','Fai un lavoro di gruppo? ',NULL,'no','{\"A\": \"Si\", \"B\": \"No\"}',0,5),(32,'short text','Se la risposta alla domanda “fai un lavoro di gruppo?” è Si, ritieni di saper lavorare in gruppo?',NULL,'no','{\"A\": \"Poco\", \"B\": \"Abbastabza\", \"C\": \"Molto\", \"D\": \"Per niente\"}',0,5),(33,'short text','Selezionare quali tra le seguenti opzioni pensi possano rendere più piacevole il tuo lavoro','Puoi selezionare più risposte','yes','{\"A\": \"Meno di ore di lavoro\", \"B\": \"Pausa pranzo più lunga\", \"C\": \"Più corsi di aggiornamento\", \"D\": \"Area relax\", \"E\": \"Meno trasferte\", \"F\": \"Possibilità di lavorare da casa\", \"G\": \"Maggiori possibilità di carriera\"}',0,5),(34,'short text','aaa','aaa','no',NULL,0,9),(35,'multiple choice','sht','htjdksa','no','{\"1\": \"asvbfdsa\", \"2\": \"asdfdsa\"}',0,9),(36,'short text','aaa','aaaa','no',NULL,0,8),(37,'short text','a','a','no',NULL,0,13),(38,'single choice','asdfgfdsa','asdfdsa','no','{\"1\": \"asdfdsa\", \"2\": \"asdfdsa\", \"3\": \"asdfds\"}',0,14),(41,'short text','aaa','aaa','no',NULL,0,15),(58,'short text','a','a','no',NULL,0,18),(59,'single choice','aaaa','aaaaa','no','{\"1\": \"aaaaa\", \"2\": \"aaaa\"}',0,19);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsibleUser`
--

DROP TABLE IF EXISTS `responsibleUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responsibleUser` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nameR` varchar(100) NOT NULL,
  `surnameR` varchar(100) DEFAULT NULL,
  `fiscalCode` varchar(16) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `pwd` varchar(16) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `administrator` enum('yes','no') NOT NULL DEFAULT 'no',
  `accepted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `fiscalCode` (`fiscalCode`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsibleUser`
--

LOCK TABLES `responsibleUser` WRITE;
/*!40000 ALTER TABLE `responsibleUser` DISABLE KEYS */;
INSERT INTO `responsibleUser` VALUES (3,'Alessandro','Di Matteo','ALESSANDRO','alessandro@gmail.com','alessandro','E72Ablq0wPTbEf9vnB8rlHqH6kNVVWzAXfun0h9Z','yes',1),(4,'Davide','Fontana','DAVIDEFONTANA','davide@gmail.com','davide',NULL,'no',1),(5,'Giulia','Scoccia','GIULIASCOCCIA','giulia@gmail.com','giulia',NULL,'no',1),(6,'cazzo','za','dmtlsnp18a515d','cazzo@gmail.com','cazz','PLyyCpYaQ7WKvHxOxOKcmzCulpqD0gP0J1HrhDI6','no',1),(7,'Franco','Germano','FRMLSN96P17A5164','franchino@gmail.com','franchino','zgISlxObefw4E6yYDGhhPAr1dFdGnyGw9fnZcEy3','no',1);
/*!40000 ALTER TABLE `responsibleUser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-18 19:28:29
