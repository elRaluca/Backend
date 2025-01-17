-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: dbBackend
-- ------------------------------------------------------
-- Server version	8.0.38

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL,
  `quantity` int NOT NULL,
  `cart_id` bigint NOT NULL,
  `product_id` int DEFAULT NULL,
  `special_bouquet_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcttvuq4mxppo8sxggjtn5i2c` (`cart_id`),
  KEY `FKl7je3auqyq1raj52qmwrgih8x` (`product_id`),
  KEY `FKlqkpvom2u38jw271rofewgwex` (`special_bouquet_id`),
  CONSTRAINT `FKl7je3auqyq1raj52qmwrgih8x` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKlqkpvom2u38jw271rofewgwex` FOREIGN KEY (`special_bouquet_id`) REFERENCES `special_bouquet` (`id`),
  CONSTRAINT `FKpcttvuq4mxppo8sxggjtn5i2c` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items_seq`
--

DROP TABLE IF EXISTS `cart_items_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items_seq`
--

LOCK TABLES `cart_items_seq` WRITE;
/*!40000 ALTER TABLE `cart_items_seq` DISABLE KEYS */;
INSERT INTO `cart_items_seq` VALUES (101);
/*!40000 ALTER TABLE `cart_items_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,2),(2,3);
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts_seq`
--

DROP TABLE IF EXISTS `carts_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts_seq`
--

LOCK TABLES `carts_seq` WRITE;
/*!40000 ALTER TABLE `carts_seq` DISABLE KEYS */;
INSERT INTO `carts_seq` VALUES (101);
/*!40000 ALTER TABLE `carts_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveries`
--

DROP TABLE IF EXISTS `deliveries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveries` (
  `id` bigint NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `delivery_method` enum('PERSONAL_LIFT','COURIER') NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `status` enum('IN_TRANSIT','DELIVERED','DELAYED') DEFAULT NULL,
  `street` varchar(255) NOT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k36n9p5v7dd96hpgkwybvbogt` (`order_id`),
  CONSTRAINT `FK7isx0rnbgqr1dcofd5putl6jw` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveries`
--

LOCK TABLES `deliveries` WRITE;
/*!40000 ALTER TABLE `deliveries` DISABLE KEYS */;
INSERT INTO `deliveries` VALUES (1,'Iancu Jianu','Romania','COURIER','elenaralucalorena@gmail.com','Elena Raluca Lorena Gheorghita','0767540136','IN_TRANSIT','Jud. Olt, str. Barbu Stribei, nr.254',1),(2,'Iancu Jianu','Romania','COURIER','elenaralucalorena@gmail.com','Elena Raluca Lorena Gheorghita','0767540136','IN_TRANSIT','Jud. Olt, str. Barbu Stribei, nr.254',2),(3,'Iancu Jianu','Romania','COURIER','elenaralucalorena@gmail.com','Elena Raluca Lorena Gheorghita','0767540136','IN_TRANSIT','Jud. Olt, str. Barbu Stribei, nr.254',3),(4,'Bucuresti','Romania','COURIER','re374239@gmail.com','Marius','0767540322','IN_TRANSIT','Barbu Stribei 21',4);
/*!40000 ALTER TABLE `deliveries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveries_seq`
--

DROP TABLE IF EXISTS `deliveries_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveries_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveries_seq`
--

LOCK TABLES `deliveries_seq` WRITE;
/*!40000 ALTER TABLE `deliveries_seq` DISABLE KEYS */;
INSERT INTO `deliveries_seq` VALUES (101);
/*!40000 ALTER TABLE `deliveries_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `order_id` int NOT NULL,
  `product_id` int DEFAULT NULL,
  `special_bouquet_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKlf6f9q956mt144wiv6p1yko16` (`product_id`),
  KEY `FKk9wwo08sioq70gc26kcvkaxv3` (`special_bouquet_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id_order`),
  CONSTRAINT `FKk9wwo08sioq70gc26kcvkaxv3` FOREIGN KEY (`special_bouquet_id`) REFERENCES `special_bouquet` (`id`),
  CONSTRAINT `FKlf6f9q956mt144wiv6p1yko16` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,30,1,1,4,NULL),(2,30,1,1,2,NULL),(3,30,1,1,5,NULL),(4,35,1,2,NULL,1),(5,450,15,3,5,NULL),(6,598,23,3,3,NULL),(7,90,3,4,2,NULL),(8,29,1,4,NULL,2);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items_seq`
--

DROP TABLE IF EXISTS `order_items_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items_seq`
--

LOCK TABLES `order_items_seq` WRITE;
/*!40000 ALTER TABLE `order_items_seq` DISABLE KEYS */;
INSERT INTO `order_items_seq` VALUES (101);
/*!40000 ALTER TABLE `order_items_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id_order` int NOT NULL,
  `order_date` datetime(6) NOT NULL,
  `status` enum('PENDING','COMPLETED','CANCELLED') DEFAULT NULL,
  `total` double NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2024-07-30 11:12:02.124000','COMPLETED',90,2),(2,'2024-07-30 11:13:38.129000','COMPLETED',35,2),(3,'2024-07-30 11:14:33.066000','COMPLETED',1048,2),(4,'2024-07-30 11:17:53.985000','COMPLETED',119,3);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_seq`
--

DROP TABLE IF EXISTS `orders_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_seq`
--

LOCK TABLES `orders_seq` WRITE;
/*!40000 ALTER TABLE `orders_seq` DISABLE KEYS */;
INSERT INTO `orders_seq` VALUES (101);
/*!40000 ALTER TABLE `orders_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ourusers`
--

DROP TABLE IF EXISTS `ourusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ourusers` (
  `id` int NOT NULL,
  `active` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `otp_generated_time` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jc13yof5705wa9femhmmbf1m3` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ourusers`
--

LOCK TABLES `ourusers` WRITE;
/*!40000 ALTER TABLE `ourusers` DISABLE KEYS */;
INSERT INTO `ourusers` VALUES (1,_binary '','Admin@testadmin.com','Admin',NULL,NULL,'$2a$10$yQuUqrXrLCc1xWIdTAQcPu1/Znkqp5f4rzeXP.gf4ScmlX4J9RaG6','ROLE_ADMIN'),(2,_binary '','elenaralucalorena@gmail.com','Raluca',NULL,NULL,'$2a$10$Pi5Oe3IbAOP4bhzqDBM6J.1Y.6CB89gbAc7N4ksx05Jkdq7h9c6Xe','ROLE_USER'),(3,_binary '','re374239@gmail.com','Marius',NULL,NULL,'$2a$10$l8m5/y.t.S2.pw24BOYISe0BuSN9hhHHhDBtxqcBLzAbgZXkofcBm','ROLE_USER');
/*!40000 ALTER TABLE `ourusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ourusers_seq`
--

DROP TABLE IF EXISTS `ourusers_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ourusers_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ourusers_seq`
--

LOCK TABLES `ourusers_seq` WRITE;
/*!40000 ALTER TABLE `ourusers_seq` DISABLE KEYS */;
INSERT INTO `ourusers_seq` VALUES (101);
/*!40000 ALTER TABLE `ourusers_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_visible` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Un buchet vibrant de flori ro╚Öii si portocalii, evoc├ónd frumusetea unui apus de vara.','/image/istockphoto-1754571609-1024x1024-removebg-preview 1.png',_binary '','Apus de Soare',25),(2,' Un buchet delicat de flori albe si roz, perfect pentru a incepe ziua cu un zambet.','/image/istockphoto-1321163804-1024x1024-removebg-preview 1.png',_binary '','Roua Diminetii ',30),(3,'Un buchet sofisticat de trandafiri albi si verzi, simbol al puritatii si rafinamentului.','/image/istockphoto-665831508-1024x1024-removebg-preview 1.png',_binary '','Eleganta Pura',26),(4,'Un n buchet de trandafiri ro╚Öii intensi, simbolizand dragostea arzatoare si pasiunea nesfarsit─â. Ideal pentru momente romantice si declaratii de iubire sincere.','/image/istockphoto-665871916-1024x1024-removebg-preview 1.png',_binary '','Flacara Pasiunii ',30),(5,'Un buchet ├«ncantator de trandafiri rosii si cremi, simbolizand inceputul unei povesti de iubire pline de pasiune si tandrete. Ideal pentru ocazii speciale ce celebreaz─â dragostea.','/image/istockphoto-1204271281-1024x1024-removebg-preview 1.png',_binary '','Rasarit de Iubire',30);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_seq`
--

DROP TABLE IF EXISTS `product_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_seq`
--

LOCK TABLES `product_seq` WRITE;
/*!40000 ALTER TABLE `product_seq` DISABLE KEYS */;
INSERT INTO `product_seq` VALUES (101);
/*!40000 ALTER TABLE `product_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recoveraccount`
--

DROP TABLE IF EXISTS `recoveraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recoveraccount` (
  `id_recover_account` int NOT NULL,
  `expiration_time` datetime(6) NOT NULL,
  `failed_attempts` int NOT NULL,
  `lock_time` datetime(6) DEFAULT NULL,
  `reset_code` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id_recover_account`),
  UNIQUE KEY `UK_cmrmad1aljeyd54eftp4bhd47` (`user_id`),
  CONSTRAINT `FKqaxx23e259lrs9alwldx80h9f` FOREIGN KEY (`user_id`) REFERENCES `ourusers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recoveraccount`
--

LOCK TABLES `recoveraccount` WRITE;
/*!40000 ALTER TABLE `recoveraccount` DISABLE KEYS */;
/*!40000 ALTER TABLE `recoveraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recoveraccount_seq`
--

DROP TABLE IF EXISTS `recoveraccount_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recoveraccount_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recoveraccount_seq`
--

LOCK TABLES `recoveraccount_seq` WRITE;
/*!40000 ALTER TABLE `recoveraccount_seq` DISABLE KEYS */;
INSERT INTO `recoveraccount_seq` VALUES (1);
/*!40000 ALTER TABLE `recoveraccount_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL,
  `message` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `rating` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (6,'Am avut o experienta extraordinara cu acest magazin de flori online!','Marius Alin',5),(7,'Livrarea a fost rapida si fara probleme, iar ambalajul a fost foarte elegant. Am apreciat si atentia la detalii si comunicarea excelenta din partea echipei. ','Elena Nistor',5),(10,'Sunt extrem de multumit de serviciile oferite de acest magazin de flori online! Comanda a fost procesata rapid, iar florile au ajuns in stare impecabila, proaspete si frumos aranjate. ','Alex Crinta',5),(11,'Buchetul comandat a depasit asteptarile, culorile erau vibrante si florile de o calitate exceptionala. Livrarea a fost facuta la timp, iar personalul a fost foarte amabil si atent la toate detaliile. ','Maria Marinescu',5);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews_seq`
--

DROP TABLE IF EXISTS `reviews_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews_seq`
--

LOCK TABLES `reviews_seq` WRITE;
/*!40000 ALTER TABLE `reviews_seq` DISABLE KEYS */;
INSERT INTO `reviews_seq` VALUES (101);
/*!40000 ALTER TABLE `reviews_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `special_bouquet`
--

DROP TABLE IF EXISTS `special_bouquet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `special_bouquet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image_bouquet` varchar(255) NOT NULL,
  `price_bouquet` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `special_bouquet`
--

LOCK TABLES `special_bouquet` WRITE;
/*!40000 ALTER TABLE `special_bouquet` DISABLE KEYS */;
INSERT INTO `special_bouquet` VALUES (1,'/image/biteem_1722327209913.png',35),(2,'/image/biteem_1722327447607.png',29);
/*!40000 ALTER TABLE `special_bouquet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,' Barbu Stribei 21','Bucuresti','Ina Maria','Romania','vfrfurnizor@gmail.com','VFR SRL','1234567893','237220'),(2,'Campineanu 10','Bucuresti','Eduard Marin','Romania','dfemsrl@gmail.com','DFEM SRL','1234567893','010039');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_seq`
--

DROP TABLE IF EXISTS `supplier_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_seq`
--

LOCK TABLES `supplier_seq` WRITE;
/*!40000 ALTER TABLE `supplier_seq` DISABLE KEYS */;
INSERT INTO `supplier_seq` VALUES (101);
/*!40000 ALTER TABLE `supplier_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-30 11:35:12
