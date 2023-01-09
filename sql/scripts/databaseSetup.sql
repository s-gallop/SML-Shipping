-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: slm
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Account` (
  `Username` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `AccountID` int NOT NULL,
  `ReceiverID` int DEFAULT NULL,
  PRIMARY KEY (`AccountID`),
  KEY `account_ibfk_1` (`ReceiverID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
INSERT INTO `Account` VALUES ('johndoe400','a!b!Cd$300',1,3),('chr1s1ina','A@ccountP@55word!',2,2),('cs304','$A+nfjoe2',3,4),('DrRacket','Recursion$42',4,1),('FunctionalDependency','PromiseCandidate$FD',5,5);
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Carrier`
--

DROP TABLE IF EXISTS `Carrier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Carrier` (
  `CarrierID` int NOT NULL,
  `CarrierOwner` varchar(50) NOT NULL,
  `Operator` varchar(50) DEFAULT NULL,
  `StorageID` int NOT NULL,
  `RouteID` int DEFAULT NULL,
  PRIMARY KEY (`CarrierID`,`CarrierOwner`),
  UNIQUE KEY `StorageID` (`StorageID`),
  KEY `RouteID` (`RouteID`),
  CONSTRAINT `carrier_ibfk_1` FOREIGN KEY (`StorageID`) REFERENCES `StorageUnit` (`StorageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `carrier_ibfk_2` FOREIGN KEY (`RouteID`) REFERENCES `Route` (`RouteID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Carrier`
--

LOCK TABLES `Carrier` WRITE;
/*!40000 ALTER TABLE `Carrier` DISABLE KEYS */;
INSERT INTO `Carrier` VALUES (1,'FastCarryOn','RichAssetHoldings',6,1),(2,'Blue Cruz','John Smithe',7,2),(3,'Buling Ltd.','Bufferman Buling',8,3),(4,'FedUp Express','Chris Jones',9,4),(5,'BringMeShip','Ronald Gover',10,5);
/*!40000 ALTER TABLE `Carrier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DeliveryRoute`
--

DROP TABLE IF EXISTS `DeliveryRoute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DeliveryRoute` (
  `RouteID` int NOT NULL,
  `ReceiverID` int DEFAULT NULL,
  `OfficeID` int DEFAULT NULL,
  PRIMARY KEY (`RouteID`),
  UNIQUE KEY `ReceiverID` (`ReceiverID`),
  KEY `deliveryroute_ibfk_2` (`OfficeID`),
  CONSTRAINT `deliveryroute_ibfk_1` FOREIGN KEY (`RouteID`) REFERENCES `Route` (`RouteID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `deliveryroute_ibfk_2` FOREIGN KEY (`OfficeID`) REFERENCES `ShippingOffice` (`OfficeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `deliveryroute_ibfk_3` FOREIGN KEY (`ReceiverID`) REFERENCES `Receiver` (`ReceiverID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DeliveryRoute`
--

LOCK TABLES `DeliveryRoute` WRITE;
/*!40000 ALTER TABLE `DeliveryRoute` DISABLE KEYS */;
INSERT INTO `DeliveryRoute` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,5);
/*!40000 ALTER TABLE `DeliveryRoute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Item`
--

DROP TABLE IF EXISTS `Item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Item` (
  `ItemID` int NOT NULL,
  `StorageID` int NOT NULL,
  `WarehouseID` int NOT NULL,
  `WarehouseOwner` varchar(50) NOT NULL,
  `OrderID` int DEFAULT NULL,
  `ItemType` varchar(50) NOT NULL,
  PRIMARY KEY (`ItemID`,`WarehouseID`,`WarehouseOwner`),
  KEY `WarehouseID` (`WarehouseID`,`WarehouseOwner`),
  KEY `StorageID` (`StorageID`),
  KEY `OrderID` (`OrderID`),
  KEY `ItemType` (`ItemType`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`WarehouseID`, `WarehouseOwner`) REFERENCES `ManufacturingPlant` (`WarehouseID`, `WarehouseOwner`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `item_ibfk_2` FOREIGN KEY (`StorageID`) REFERENCES `StorageUnit` (`StorageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `item_ibfk_3` FOREIGN KEY (`OrderID`) REFERENCES `Order` (`OrderID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `item_ibfk_4` FOREIGN KEY (`ItemType`) REFERENCES `ItemType` (`ItemType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Item`
--

LOCK TABLES `Item` WRITE;
/*!40000 ALTER TABLE `Item` DISABLE KEYS */;
INSERT INTO `Item` VALUES (1,1,1,'ASB Inc.',1,'Book'),(2,2,2,'StoreItAll',2,'Beverage'),(3,4,3,'SafeSafe Inc.',3,'Toy'),(4,5,4,'StoreNet',4,'Clothes'),(5,10,5,'MoreLand Holdings',5,'Beverage');
/*!40000 ALTER TABLE `Item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ItemType`
--

DROP TABLE IF EXISTS `ItemType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ItemType` (
  `Weight` double DEFAULT NULL,
  `Size` double DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `ItemType` varchar(50) NOT NULL,
  PRIMARY KEY (`ItemType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ItemType`
--

LOCK TABLES `ItemType` WRITE;
/*!40000 ALTER TABLE `ItemType` DISABLE KEYS */;
INSERT INTO `ItemType` VALUES (1.2,6000,50.99,'Beverage'),(0.5,5000,12.99,'Book'),(0.62,5000,10.99,'Clothes'),(3,40000,50.99,'Decoration'),(50.5,10000,130.99,'Toy');
/*!40000 ALTER TABLE `ItemType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Location` (
  `Country` varchar(50) DEFAULT NULL,
  `Region` varchar(50) DEFAULT NULL,
  `City` varchar(50) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `LocationID` int NOT NULL,
  PRIMARY KEY (`LocationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
INSERT INTO `Location` VALUES ('Canada','British Columbia','Vancouver','214 Main Mall',1),('Canada','Ontario','Toronto','214 College Street',2),('United States','New York','New York','1 Liberty Island',3),('China','Shanghai','Shanghai','Zheng Jia Lu, 201422',4),('Italy','RM','Roma','Piazza della Rotonda',5),('China','Shanghai','Shanghai','Zheng Jia Lu, 201424',6),('United States','Texas','Austin','701 Brazos St',7),('France','Paris','Paris','66 Av. des Champsƒlys-Zes',8),('United States','Michigan','Wayne','38303 Michigan Ave',9),('Canada','British Columbia','Vancouver','2525 West Mall',10),('Canada','British Columbia','Vancouver','5756 University Blvd',11),('Canada','British Columbia','Langley','5333 216 St',12),('United States','South Dakota','Mobridge','1820 W Grand Crossing',13),('France','Paris','Paris','59 Av. des Champsƒlys-Zes',14),('Canada','Ontario','Toronto','214 York Street',15);
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ManufacturingPlant`
--

DROP TABLE IF EXISTS `ManufacturingPlant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ManufacturingPlant` (
  `WarehouseID` int NOT NULL,
  `WarehouseOwner` varchar(50) NOT NULL,
  `ProductionRate` int DEFAULT NULL,
  `ItemType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`WarehouseID`,`WarehouseOwner`),
  KEY `ItemType` (`ItemType`),
  CONSTRAINT `manufacturingplant_ibfk_1` FOREIGN KEY (`WarehouseID`, `WarehouseOwner`) REFERENCES `Warehouse` (`WarehouseID`, `WarehouseOwner`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `manufacturingplant_ibfk_2` FOREIGN KEY (`ItemType`) REFERENCES `ItemType` (`ItemType`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ManufacturingPlant`
--

LOCK TABLES `ManufacturingPlant` WRITE;
/*!40000 ALTER TABLE `ManufacturingPlant` DISABLE KEYS */;
INSERT INTO `ManufacturingPlant` VALUES (1,'ASB Inc.',40000,'Toy'),(2,'StoreItAll',52000,'Toy'),(3,'SafeSafe Inc.',50000,'Clothes'),(4,'StoreNet',21000,'Decoration'),(5,'MoreLand Holdings',720000,'Toy');
/*!40000 ALTER TABLE `ManufacturingPlant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order`
--

DROP TABLE IF EXISTS `Order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Order` (
  `OrderID` int NOT NULL,
  `TotalPrice` double DEFAULT NULL,
  `StartDate` timestamp NULL DEFAULT NULL,
  `CompletionDate` timestamp NULL DEFAULT NULL,
  `AccountID` int NOT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `AccountID` (`AccountID`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `Account` (`AccountID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order` DISABLE KEYS */;
INSERT INTO `Order` VALUES (1,12.99,'2021-11-13','2021-11-15',1),(2,50.99,'2021-12-23','2021-12-25',2),(3,130.99,'2022-01-10','2022-01-15',3),(4,10.99,'2021-02-14','2021-02-18',4),(5,50.99,'2021-02-15','2021-02-17',5);
/*!40000 ALTER TABLE `Order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PickupRoute`
--

DROP TABLE IF EXISTS `PickupRoute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PickupRoute` (
  `RouteID` int NOT NULL,
  `WarehouseID` int NOT NULL,
  `WarehouseOwner` varchar(50) NOT NULL,
  `OfficeID` int NOT NULL,
  PRIMARY KEY (`RouteID`),
  UNIQUE KEY `WarehouseID` (`WarehouseID`,`WarehouseOwner`),
  KEY `OfficeID` (`OfficeID`),
  CONSTRAINT `pickuproute_ibfk_1` FOREIGN KEY (`RouteID`) REFERENCES `Route` (`RouteID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pickuproute_ibfk_2` FOREIGN KEY (`WarehouseID`, `WarehouseOwner`) REFERENCES `Warehouse` (`WarehouseID`, `WarehouseOwner`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pickuproute_ibfk_3` FOREIGN KEY (`OfficeID`) REFERENCES `ShippingOffice` (`OfficeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PickupRoute`
--

LOCK TABLES `PickupRoute` WRITE;
/*!40000 ALTER TABLE `PickupRoute` DISABLE KEYS */;
INSERT INTO `PickupRoute` VALUES (11,1,'ASB Inc.',1),(12,2,'StoreItAll',2),(13,3,'SafeSafe Inc.',3),(14,4,'StoreNet',4),(15,5,'MoreLand Holdings',5);
/*!40000 ALTER TABLE `PickupRoute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Receiver`
--

DROP TABLE IF EXISTS `Receiver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Receiver` (
  `Name` varchar(50) DEFAULT NULL,
  `ReceiverID` int NOT NULL,
  `LocationID` int NOT NULL,
  `RouteID` int DEFAULT NULL,
  PRIMARY KEY (`ReceiverID`),
  UNIQUE KEY `LocationID` (`LocationID`),
  UNIQUE KEY `RouteID` (`RouteID`),
  CONSTRAINT `receiver_ibfk_1` FOREIGN KEY (`LocationID`) REFERENCES `Location` (`LocationID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `receiver_ibfk_2` FOREIGN KEY (`RouteID`) REFERENCES `DeliveryRoute` (`RouteID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Receiver`
--

LOCK TABLES `Receiver` WRITE;
/*!40000 ALTER TABLE `Receiver` DISABLE KEYS */;
INSERT INTO `Receiver` VALUES ('Kim',1,1,1),('Bob',2,2,2),('Rose',3,3,3),('John',4,4,4),('Jamie',5,5,5);
/*!40000 ALTER TABLE `Receiver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Route`
--

DROP TABLE IF EXISTS `Route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Route` (
  `RouteID` int NOT NULL,
  PRIMARY KEY (`RouteID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Route`
--

LOCK TABLES `Route` WRITE;
/*!40000 ALTER TABLE `Route` DISABLE KEYS */;
INSERT INTO `Route` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18);
/*!40000 ALTER TABLE `Route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShippingOffice`
--

DROP TABLE IF EXISTS `ShippingOffice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ShippingOffice` (
  `OfficeID` int NOT NULL,
  `LocationID` int NOT NULL,
  `StorageID` int NOT NULL,
  PRIMARY KEY (`OfficeID`),
  UNIQUE KEY `OfficeID` (`OfficeID`),
  UNIQUE KEY `LocationID` (`LocationID`),
  UNIQUE KEY `StorageID` (`StorageID`),
  CONSTRAINT `shippingoffice_ibfk_1` FOREIGN KEY (`LocationID`) REFERENCES `Location` (`LocationID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shippingoffice_ibfk_2` FOREIGN KEY (`StorageID`) REFERENCES `StorageUnit` (`StorageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShippingOffice`
--

LOCK TABLES `ShippingOffice` WRITE;
/*!40000 ALTER TABLE `ShippingOffice` DISABLE KEYS */;
INSERT INTO `ShippingOffice` VALUES (1,11,11),(2,12,12),(3,13,13),(4,14,14),(5,15,15);
/*!40000 ALTER TABLE `ShippingOffice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShippingRoute`
--

DROP TABLE IF EXISTS `ShippingRoute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ShippingRoute` (
  `RouteID` int NOT NULL,
  `SourceOfficeID` int NOT NULL,
  `DestOfficeID` int NOT NULL,
  PRIMARY KEY (`RouteID`),
  UNIQUE KEY `SourceOfficeID` (`SourceOfficeID`,`DestOfficeID`),
  KEY `DestOfficeID` (`DestOfficeID`),
  CONSTRAINT `shippingroute_ibfk_1` FOREIGN KEY (`RouteID`) REFERENCES `Route` (`RouteID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shippingroute_ibfk_2` FOREIGN KEY (`SourceOfficeID`) REFERENCES `ShippingOffice` (`OfficeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shippingroute_ibfk_3` FOREIGN KEY (`DestOfficeID`) REFERENCES `ShippingOffice` (`OfficeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShippingRoute`
--

LOCK TABLES `ShippingRoute` WRITE;
/*!40000 ALTER TABLE `ShippingRoute` DISABLE KEYS */;
INSERT INTO `ShippingRoute` VALUES (6,1,2),(16,1,3),(17,1,4),(18,1,5),(7,2,3),(8,3,4),(9,4,5),(10,5,1);
/*!40000 ALTER TABLE `ShippingRoute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StorageType`
--

DROP TABLE IF EXISTS `StorageType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `StorageType` (
  `Load` double DEFAULT NULL,
  `Capacity` double DEFAULT NULL,
  `StorageType` varchar(50) NOT NULL,
  PRIMARY KEY (`StorageType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StorageType`
--

LOCK TABLES `StorageType` WRITE;
/*!40000 ALTER TABLE `StorageType` DISABLE KEYS */;
INSERT INTO `StorageType` VALUES (400,299,'Plane–B3XX'),(600,400,'Plane–B7XX'),(5000,45000,'Ship–Big'),(1000,600,'Ship–Medium'),(500,400,'Ship–Small'),(800,800,'Warehouse–Big'),(10000,10000,'Warehouse–Small');
/*!40000 ALTER TABLE `StorageType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StorageUnit`
--

DROP TABLE IF EXISTS `StorageUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `StorageUnit` (
  `StorageID` int NOT NULL,
  `StorageType` varchar(50) DEFAULT NULL,
  `UnusedCapacity` double DEFAULT NULL,
  `UsedLoad` double DEFAULT NULL,
  PRIMARY KEY (`StorageID`),
  KEY `storageunit_ibfk_1` (`StorageType`),
  CONSTRAINT `storageunit_ibfk_1` FOREIGN KEY (`StorageType`) REFERENCES `StorageType` (`StorageType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StorageUnit`
--

LOCK TABLES `StorageUnit` WRITE;
/*!40000 ALTER TABLE `StorageUnit` DISABLE KEYS */;
INSERT INTO `StorageUnit` VALUES (1,'Warehouse–Small',3000,2000),(2,'Warehouse–Small',5000,4000),(3,'Plane–B7XX',0,40000),(4,'Warehouse–Small',5900,200),(5,'Warehouse–Small',500,600),(6,'Ship–Medium',394,200),(7,'Plane–B3XX',199,100),(8,'Ship–Small',293,400),(9,'Ship–Big',4384,40000),(10,'Warehouse–Big',3000,900000),(11,'Warehouse–Small',500,600),(12,'Ship–Medium',394,200),(13,'Plane–B3XX',199,100),(14,'Ship–Small',293,400),(15,'Ship–Big',4384,40000);
/*!40000 ALTER TABLE `StorageUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Warehouse`
--

DROP TABLE IF EXISTS `Warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Warehouse` (
  `WarehouseID` int NOT NULL,
  `WarehouseOwner` varchar(50) NOT NULL,
  `LocationID` int NOT NULL,
  `StorageID` int DEFAULT NULL,
  `RouteID` int DEFAULT NULL,
  PRIMARY KEY (`WarehouseID`,`WarehouseOwner`),
  UNIQUE KEY `LocationID` (`LocationID`),
  UNIQUE KEY `StorageID` (`StorageID`),
  UNIQUE KEY `RouteID` (`RouteID`),
  CONSTRAINT `warehouse_ibfk_1` FOREIGN KEY (`LocationID`) REFERENCES `Location` (`LocationID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `warehouse_ibfk_2` FOREIGN KEY (`StorageID`) REFERENCES `StorageUnit` (`StorageID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `warehouse_ibfk_3` FOREIGN KEY (`RouteID`) REFERENCES `PickupRoute` (`RouteID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Warehouse`
--

LOCK TABLES `Warehouse` WRITE;
/*!40000 ALTER TABLE `Warehouse` DISABLE KEYS */;
INSERT INTO `Warehouse` VALUES (1,'ASB Inc.',6,1,11),(2,'StoreItAll',7,2,12),(3,'SafeSafe Inc.',8,4,13),(4,'StoreNet',9,5,14),(5,'MoreLand Holdings',10,10,15);
/*!40000 ALTER TABLE `Warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-02 20:52:10
