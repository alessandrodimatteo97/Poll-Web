-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: PollWeb
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES (5,'dd313032-a678-4515-a910-6c3d09d9cf3b','Erika','erika@gmail.com','f7f7591403c6c431053920223069550a'),(6,'991c4c93-e2e5-4837-baed-eee19fdf56f3','Miriana','miriana@gmail.com','d8cb78d5b91cf5725381e19dbde9593f'),(7,'fd32d4c6-3813-496c-a68c-53b0a9df0d7d','Luca','luca@gmail.com','ff377aff39a9345a9cca803fb5c5c081'),(8,'ecb98225-a931-4310-a0de-fe8bb111a10e',NULL,NULL,NULL),(9,'5b9eb355-21e8-4996-9dca-a8a27349df56',NULL,NULL,NULL),(10,'15a43e1c-e32a-4575-997c-64fd02159e1c',NULL,NULL,NULL),(11,'331d6555-8f34-48ea-921c-be26f769b4bd',NULL,NULL,NULL),(12,'6ff6c349-8ca9-4711-86cb-494fb84919e3','Laura','laura@gmail.com','laura'),(13,'f0d43d10-bed8-423b-8705-b1c160ccfb53','Angelo','angelo@gmail.com','680e89809965ec41e64dc7e447f175ab'),(14,'5b960074-6e81-4964-8db7-dc1f46b81a30','Marco','marco@gmail.com','f5888d0bb58d611107e11f7cbc41c97a'),(15,'a65f7145-5941-4eaa-99a9-5a8301e12686','Andrea','andrea@gmail.com','1c42f9c1ca2f65441465b43cd9339d6c'),(16,'efa460e2-0f06-4bd0-8840-a89c2866c2c5','Fabio','fabio@gmail.com','a53bd0415947807bcb95ceec535820ee'),(17,'ff63c492-a661-473c-a702-2e7700646132','Elvis','elvis@gmail.com','8b28c7134887bb938e1ffed68456ffb2');
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` VALUES (1,5,5),(2,5,6),(3,5,7),(4,5,12),(5,5,13),(6,5,14),(7,5,15),(8,5,16),(9,5,17),(10,3,8),(11,3,9),(12,4,10);
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `poll`
--

LOCK TABLES `poll` WRITE;
/*!40000 ALTER TABLE `poll` DISABLE KEYS */;
INSERT INTO `poll` VALUES (3,'Università','Rispondi alle domande sulla tua università','Grazie per la partecipazione','open','/Università','1',3),(4,'Scuola','Rispondi alle domande sulla scuola','Grazie per la partecipazione','open','/Scuola','1',4),(5,'Lavoro','Rispondi alle domande sul tuo lavoro','Grazie per la partecipazione','reserved','/Lavoro','1',5),(6,'Casa','Rispondi alle domande sulla tua casa','Grazie per la partecipazione','open','/Casa','1',3),(7,'Famiglia','Rispondi alle domande sulla tua famiglia','Grazie per la partecipazione','open','/Famiglia','1',4),(8,'Amici','Rispondi alle domande sui tuoi amici','Grazie per la partecipazione','open','/Amici','1',3);
/*!40000 ALTER TABLE `poll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (11,'short text','Quale università frequenti? ',NULL,'no',NULL,3),(12,'single choice','Che tipo di univesità è?',NULL,'no','{\"A\": \"pubblica\", \"B\": \"privata\"}',3),(14,'short text','Segui le lezioni?',NULL,'yes','{\"A\": \"Poco\", \"B\": \"Abbastanza\", \"C\": \"Sempre\", \"D\": \"Per niente\"}',3),(15,'short text','Sei soddisfatto della tua università?',NULL,'no','{\"A\": \"Poco\", \"B\": \"Abbastanza\", \"C\": \"Molto\", \"D\": \"Per niente\"}',3),(16,'numeric','A che anno sei?',NULL,'no',NULL,3),(17,'long text','Che facoltà frequenti?',NULL,'yes',NULL,3),(18,'short text','Che tipo di corso è?',NULL,'no','{\"A\": \"laurea triennale\", \"B\": \"laurea specialistica\", \"C\": \"master\", \"D\": \"ciclo unico\"}',3),(19,'short text','Come si chiama la tua scuola?',NULL,'yes',NULL,4),(20,'short text','Dove si trova?',NULL,'no',NULL,4),(21,'single choice','A che grado di istruzione sei?',NULL,'yes','{\"A\": \"scuola primaria\", \"B\": \"scuola secondaria di primo grado\", \"C\": \"scuola secondaria di secondo grado\", \"D\": \"\"}',4),(22,'numeric','A che anno ti trovi? ',NULL,'no',NULL,4),(23,'date','In che anno hai iniziato la tua scuola?',NULL,'yes',NULL,4),(24,'single choice','Sei mai stato rimandato? ',NULL,'no','{\"A\": \"Si\", \"B\": \"No\"}',4),(25,'short text','Sei mai stato bocciato?',NULL,'no','{\"A\": \"Si\", \"B\": \"No\"}',4),(26,'long text','Che lavoro fai? ',NULL,'yes',NULL,5),(27,'short text','Come si chiama l’azienda dove lavori? ','Rispondere solamente se si lavora in un’azienda','no',NULL,5),(28,'single choice','Sei soddisfatto del tuo lavoro?',NULL,'yes','{\"A\": \"Per niente\", \"B\": \"Poco\", \"C\": \"Abbastanza\", \"D\": \"Molto\"}',5),(29,'long text','Spiegare il motivo della risposta alla domanda “sei soddisfatto del tuo lavoro?”','Tranquillo che il sondaggio è anonimo','yes',NULL,5),(30,'short text','Come pensi possa migliorare il tuo lavoro? ','Rispondere con sincerità','no',NULL,5),(31,'single choice','Fai un lavoro di gruppo? ',NULL,'no','{\"A\": \"Si\", \"B\": \"No\"}',5),(32,'short text','Se la risposta alla domanda “fai un lavoro di gruppo?” è Si, ritieni di saper lavorare in gruppo?',NULL,'no','{\"A\": \"Poco\", \"B\": \"Abbastabza\", \"C\": \"Molto\", \"D\": \"Per niente\"}',5),(33,'short text','Selezionare quali tra le seguenti opzioni pensi possano rendere più piacevole il tuo lavoro','Puoi selezionare più risposte','yes','{\"A\": \"Meno di ore di lavoro\", \"B\": \"Pausa pranzo più lunga\", \"C\": \"Più corsi di aggiornamento\", \"D\": \"Area relax\", \"E\": \"Meno trasferte\", \"F\": \"Possibilità di lavorare da casa\", \"G\": \"Maggiori possibilità di carriera\"}',5);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `responsibleUser`
--

LOCK TABLES `responsibleUser` WRITE;
/*!40000 ALTER TABLE `responsibleUser` DISABLE KEYS */;
INSERT INTO `responsibleUser` VALUES (3,'Alessandro','Di Matteo','ALESSANDRO','alessandro@gmail.com','alessandro','yes'),(4,'Davide','Fontana','DAVIDEFONTANA','davide@gmail.com','davide','no'),(5,'Giulia','Scoccia','GIULIASCOCCIA','giulia@gmail.com','giulia','no');
/*!40000 ALTER TABLE `responsibleUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'PollWeb'
--

--
-- Dumping routines for database 'PollWeb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-02  9:03:50
