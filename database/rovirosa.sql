-- MySQL dump 10.13  Distrib 8.0.44, for Linux (x86_64)
--
-- Host: localhost    Database: rovirosa
-- ------------------------------------------------------
-- Server version	8.0.44-0ubuntu0.24.04.2

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
-- Table structure for table `carrito`
--

DROP TABLE IF EXISTS `carrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrito` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `catalogo_id` int NOT NULL,
  `cantidad` int NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_carrito_user` (`user_id`),
  KEY `fk_carrito_catalogo` (`catalogo_id`),
  CONSTRAINT `fk_carrito_catalogo` FOREIGN KEY (`catalogo_id`) REFERENCES `catalogo_pv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_carrito_user` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=426 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrito`
--

LOCK TABLES `carrito` WRITE;
/*!40000 ALTER TABLE `carrito` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogo_pv`
--

DROP TABLE IF EXISTS `catalogo_pv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogo_pv` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prd_id` int NOT NULL COMMENT 'ID del producto',
  `pv_id` int NOT NULL COMMENT 'ID del punto de venta',
  `stock` int NOT NULL COMMENT 'Cantidad disponible en el punto de venta',
  `vendidos` int NOT NULL DEFAULT '0' COMMENT 'Cantidad de productos vendidos',
  PRIMARY KEY (`id`),
  KEY `FK_catalogo_productos` (`prd_id`),
  KEY `FK_catalogo_pv` (`pv_id`),
  CONSTRAINT `FK_catalogo_productos` FOREIGN KEY (`prd_id`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_catalogo_pv` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo_pv`
--

LOCK TABLES `catalogo_pv` WRITE;
/*!40000 ALTER TABLE `catalogo_pv` DISABLE KEYS */;
INSERT INTO `catalogo_pv` VALUES (2,2,1,0,0),(4,4,1,85,0),(5,5,1,75,0),(6,6,1,58,0),(7,7,1,97,0),(8,8,1,34,0),(33,1,6,99,0),(34,4,6,87,0),(35,2,6,79,0),(36,5,6,183,0),(37,6,6,43,0),(38,7,6,47,0),(39,8,6,63,0),(40,1763617797,1,96,0),(41,1763617797,6,98,0),(42,1763617839,1,90,0),(43,1763617839,6,94,0),(44,1763617882,1,89,0),(45,1763617882,6,93,0),(46,1763618175,1,117,0),(47,1763618175,6,113,0),(48,1763618211,1,111,0),(49,1763618211,6,113,0),(50,1763618295,1,113,0),(51,1763618295,6,113,0),(52,1763618473,1,65,0),(53,1763618473,6,76,0),(54,1763618550,1,75,0),(55,1763618550,6,86,0),(56,1763618585,1,65,0),(57,1763618585,6,80,0),(58,1763618790,1,118,0),(59,1763618790,6,119,0),(60,1763618988,1,130,0),(61,1763618988,6,178,0),(64,1765454750,1,30,0),(65,1765454750,6,40,0),(66,1765455376,1,120,0),(67,1765455376,6,140,0);
/*!40000 ALTER TABLE `catalogo_pv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (2,'Cerveza'),(3,'Sabritas'),(4,'Refrescos');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_venta`
--

DROP TABLE IF EXISTS `chat_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vnta_id` int NOT NULL COMMENT 'ID de la venta',
  `user_id` int NOT NULL COMMENT 'ID del usuario que envía el mensaje (cliente o repartidor)',
  `mensaje` varchar(255) DEFAULT NULL COMMENT 'Mensaje de texto',
  `enviado` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de envío del mensaje',
  `archivo` varchar(255) DEFAULT NULL COMMENT 'Nombre del archivo si se envió una imagen o documento',
  PRIMARY KEY (`id`),
  KEY `FK__ventas` (`vnta_id`),
  KEY `FK_chat_venta_usuarios` (`user_id`),
  CONSTRAINT `FK__ventas` FOREIGN KEY (`vnta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_chat_venta_usuarios` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_venta`
--

LOCK TABLES `chat_venta` WRITE;
/*!40000 ALTER TABLE `chat_venta` DISABLE KEYS */;
INSERT INTO `chat_venta` VALUES (55,143,21,'hola','2025-12-10 07:29:02','chat_ventas/venta_143/c9174842-d6b4-4b4d-8c1d-751486f56b50.jpg'),(56,146,21,'hoal','2025-12-10 07:30:06',NULL),(57,148,35,'buenas, no ha subido su comprobante ','2025-12-10 08:36:58',NULL),(58,148,35,'hola','2025-12-10 08:37:35',NULL),(59,144,40,'hola ','2025-12-10 08:38:21',NULL),(60,160,39,'gj','2025-12-11 09:45:40',NULL),(61,160,35,'estoy cerca de su domicilio ','2025-12-11 09:46:14',NULL);
/*!40000 ALTER TABLE `chat_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT 'ID del usuario',
  `dir_id` int NOT NULL COMMENT 'ID de la dirección',
  `ine_front` varchar(50) NOT NULL COMMENT 'INE frontal',
  `ine_back` varchar(50) NOT NULL COMMENT 'INE posterior',
  `stricks` int NOT NULL DEFAULT '0' COMMENT 'Número de stricks (máximo 3)',
  `p_cancel` int NOT NULL DEFAULT '0' COMMENT 'Número de cancelaciones (máximo 3 al mes)',
  `last_cancel` date DEFAULT NULL COMMENT 'Fecha del último pedido cancelado',
  PRIMARY KEY (`id`),
  KEY `FK_clientes_direcciones` (`dir_id`),
  KEY `FK_clientes_usuarios` (`user_id`),
  CONSTRAINT `FK_clientes_direcciones` FOREIGN KEY (`dir_id`) REFERENCES `direcciones` (`id`),
  CONSTRAINT `FK_clientes_usuarios` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (12,39,40,'ine/6b11cc4b-5f79-43eb-b7e4-b435f26e3fcd.jpg','ine/29f0b7d0-7fac-4310-8bed-c3e7ca53746a.jpg',0,0,NULL),(13,40,41,'ine/7157c8d0-7984-4b3b-8ca3-1c7aad3faa86.jpg','ine/10d69434-af45-402e-a12b-5d3cffd1c52e.jpg',0,0,NULL);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datos_transferencia`
--

DROP TABLE IF EXISTS `datos_transferencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `datos_transferencia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titular` varchar(255) NOT NULL,
  `banco` varchar(255) NOT NULL,
  `clave` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datos_transferencia`
--

LOCK TABLES `datos_transferencia` WRITE;
/*!40000 ALTER TABLE `datos_transferencia` DISABLE KEYS */;
INSERT INTO `datos_transferencia` VALUES (1,'Samuel Burelos Jeronimo','NU Bank','638180010199083400');
/*!40000 ALTER TABLE `datos_transferencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuentos_categ`
--

DROP TABLE IF EXISTS `descuentos_categ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuentos_categ` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categ_id` int NOT NULL COMMENT 'ID de la categoría',
  `config_id` int NOT NULL COMMENT 'ID de la configuración del descuento',
  PRIMARY KEY (`id`),
  KEY `FK_descuento_categ_categorias` (`categ_id`),
  KEY `FK_descuento_categ_descuentos_config` (`config_id`),
  CONSTRAINT `FK_descuento_categ_categorias` FOREIGN KEY (`categ_id`) REFERENCES `categorias` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_descuento_categ_descuentos_config` FOREIGN KEY (`config_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentos_categ`
--

LOCK TABLES `descuentos_categ` WRITE;
/*!40000 ALTER TABLE `descuentos_categ` DISABLE KEYS */;
INSERT INTO `descuentos_categ` VALUES (5,2,27);
/*!40000 ALTER TABLE `descuentos_categ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuentos_config`
--

DROP TABLE IF EXISTS `descuentos_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuentos_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('porcentaje','fijo') NOT NULL COMMENT '1 = Porcentaje, 2 = Monto Fijo',
  `valor` double NOT NULL COMMENT 'Valor del descuento (0.10 = 10% o 50 = $50)',
  `objetivo` enum('producto','categoria','marca') NOT NULL COMMENT '1 = Producto, 2 = Categoria',
  `fech_in` date NOT NULL COMMENT 'Fecha de inicio del descuento',
  `fech_fin` date DEFAULT NULL COMMENT 'Fecha de fin del descuento',
  `banner` varchar(100) DEFAULT NULL COMMENT 'Si no sube un banner no se muestra en la pantalla de inicio',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentos_config`
--

LOCK TABLES `descuentos_config` WRITE;
/*!40000 ALTER TABLE `descuentos_config` DISABLE KEYS */;
INSERT INTO `descuentos_config` VALUES (17,'fijo',12,'producto','2025-11-15',NULL,NULL),(18,'porcentaje',10,'producto','2025-11-15',NULL,NULL),(27,'porcentaje',5,'categoria','2025-11-19',NULL,'descuentos/5537bd52-834b-4d8a-bd1e-04382e0517ef.jpg');
/*!40000 ALTER TABLE `descuentos_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuentos_marca`
--

DROP TABLE IF EXISTS `descuentos_marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuentos_marca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marca_id` int DEFAULT NULL COMMENT 'ID de la marca',
  `config_id` int DEFAULT NULL COMMENT 'ID de la configuración del descuento',
  PRIMARY KEY (`id`),
  KEY `FK_descuento_marca_descuentos_marca` (`marca_id`),
  KEY `FK_descuento_marca_descuentos_config` (`config_id`),
  CONSTRAINT `FK_descuento_marca_descuentos_config` FOREIGN KEY (`config_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_descuento_marca_descuentos_marca` FOREIGN KEY (`marca_id`) REFERENCES `marcas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentos_marca`
--

LOCK TABLES `descuentos_marca` WRITE;
/*!40000 ALTER TABLE `descuentos_marca` DISABLE KEYS */;
/*!40000 ALTER TABLE `descuentos_marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuentos_producto`
--

DROP TABLE IF EXISTS `descuentos_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuentos_producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prd_id` int NOT NULL COMMENT 'ID del producto',
  `config_id` int NOT NULL COMMENT 'ID de la configuración del descuento',
  PRIMARY KEY (`id`),
  KEY `FK_descuentos_producto_productos` (`prd_id`),
  KEY `FK_descuentos_producto_descuentos_config` (`config_id`),
  CONSTRAINT `FK_descuentos_producto_descuentos_config` FOREIGN KEY (`config_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_descuentos_producto_productos` FOREIGN KEY (`prd_id`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentos_producto`
--

LOCK TABLES `descuentos_producto` WRITE;
/*!40000 ALTER TABLE `descuentos_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `descuentos_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalles_venta`
--

DROP TABLE IF EXISTS `detalles_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalles_venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vnta_id` int NOT NULL COMMENT 'ID de la venta',
  `prd_id` int NOT NULL COMMENT 'ID del producto',
  `cant_in` int NOT NULL COMMENT 'Cantidad inicial',
  `cant_fin` int DEFAULT NULL COMMENT 'Cantidad final (en caso de que el cliente no pague todo se ajusta la cantidad)',
  `precio_unit` decimal(10,2) NOT NULL COMMENT 'Precio unitario al momento de la venta',
  `desc_unit` decimal(10,2) NOT NULL COMMENT 'Descuento aplicado por unidad en decimal (0.10) = 10%',
  PRIMARY KEY (`id`),
  KEY `FK_detalles_venta_producto` (`prd_id`),
  KEY `FK_detalles_venta_ventas` (`vnta_id`),
  CONSTRAINT `FK_detalles_venta_producto` FOREIGN KEY (`prd_id`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_detalles_venta_ventas` FOREIGN KEY (`vnta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_venta`
--

LOCK TABLES `detalles_venta` WRITE;
/*!40000 ALTER TABLE `detalles_venta` DISABLE KEYS */;
INSERT INTO `detalles_venta` VALUES (234,143,1763618211,6,NULL,28.00,1.40),(235,144,1763618988,2,NULL,23.00,1.15),(236,145,1,2,NULL,25.00,1.25),(237,145,2,3,NULL,17.90,0.90),(238,146,7,2,NULL,33.00,0.00),(239,146,8,1,NULL,34.00,0.00),(240,147,1763617882,2,NULL,38.00,1.90),(241,147,1763618175,1,NULL,26.00,1.30),(242,147,1763618550,1,NULL,27.00,1.35),(243,148,5,1,NULL,239.00,11.95),(244,148,1763618550,2,NULL,27.00,1.35),(245,149,6,2,NULL,74.00,3.70),(246,150,1763618585,3,NULL,23.00,1.15),(247,151,7,1,NULL,33.00,0.00),(248,151,8,2,NULL,34.00,0.00),(249,151,1763618295,2,NULL,135.00,6.75),(250,151,1763618585,1,NULL,23.00,1.15),(251,151,1763618790,1,NULL,45.00,2.25),(252,151,1763618473,1,NULL,29.00,1.45),(253,152,1,2,NULL,25.00,1.25),(254,152,2,1,NULL,17.90,0.90),(255,152,1763618550,2,NULL,27.00,1.35),(256,152,1763618175,1,NULL,26.00,1.30),(257,152,1763617882,2,NULL,38.00,1.90),(258,153,4,2,NULL,239.00,11.95),(259,153,1763618550,1,NULL,27.00,1.35),(260,153,5,2,NULL,239.00,11.95),(261,154,8,2,NULL,34.00,0.00),(262,154,7,1,NULL,33.00,0.00),(263,155,2,2,NULL,17.90,0.90),(264,155,4,2,NULL,239.00,11.95),(265,156,5,1,NULL,239.00,11.95),(266,157,4,1,NULL,239.00,11.95),(267,157,2,3,NULL,17.90,0.90),(268,158,1,1,NULL,25.00,1.25),(269,158,2,1,NULL,17.90,0.90),(270,159,2,1,NULL,17.90,0.90),(271,160,5,1,NULL,239.00,11.95),(272,161,5,1,NULL,239.00,11.95),(273,161,1763617839,1,NULL,42.00,2.10),(274,161,1765454750,1,NULL,24.00,0.00);
/*!40000 ALTER TABLE `detalles_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dias_laborales`
--

DROP TABLE IF EXISTS `dias_laborales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dias_laborales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pv_id` int NOT NULL COMMENT 'ID del punto de venta',
  `dia_semana` enum('lunes','martes','miercoles','jueves','viernes','sabado','domingo') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_horarios_punto_venta` (`pv_id`),
  CONSTRAINT `FK_horarios_punto_venta` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dias_laborales`
--

LOCK TABLES `dias_laborales` WRITE;
/*!40000 ALTER TABLE `dias_laborales` DISABLE KEYS */;
INSERT INTO `dias_laborales` VALUES (1,1,'lunes'),(2,1,'martes'),(3,1,'miercoles'),(4,1,'jueves'),(5,1,'viernes'),(6,1,'sabado'),(8,6,'lunes'),(9,6,'martes'),(10,6,'miercoles'),(11,6,'jueves'),(12,6,'viernes'),(13,6,'sabado'),(28,1,'domingo'),(29,6,'domingo');
/*!40000 ALTER TABLE `dias_laborales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direcciones`
--

DROP TABLE IF EXISTS `direcciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direcciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lat` decimal(10,8) NOT NULL COMMENT 'Latitud de la dirección',
  `lng` decimal(11,8) NOT NULL COMMENT 'Longitud de la dirección',
  `ref` varchar(255) NOT NULL COMMENT 'Referencia de la dirección',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direcciones`
--

LOCK TABLES `direcciones` WRITE;
/*!40000 ALTER TABLE `direcciones` DISABLE KEYS */;
INSERT INTO `direcciones` VALUES (1,17.74748539,-92.61013526,''),(15,17.76446564,-92.59152140,''),(39,17.76224554,-92.60406914,'Casa blanca'),(40,17.76503095,-92.59523421,'Cerca de mata de capulín'),(41,17.75895281,-92.59916286,'gbhj');
/*!40000 ALTER TABLE `direcciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa_config`
--

DROP TABLE IF EXISTS `empresa_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa_config` (
  `rfc` varchar(20) NOT NULL COMMENT 'RFC de la empresa',
  `nombre` varchar(50) NOT NULL COMMENT 'Nombre o razón social de la empresa',
  `logo` varchar(255) NOT NULL COMMENT 'Logo de la empresa',
  `descrip` varchar(255) DEFAULT NULL COMMENT 'Descripción de la empresa',
  `monto_min` int NOT NULL DEFAULT '150' COMMENT 'Monto mínimo para todos los puntos de venta',
  `comision` int NOT NULL DEFAULT '150',
  `email_app` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Email de la aplicación',
  `codigo_app` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Código de la aplicación (lrhm upio jpnh tvpv)',
  PRIMARY KEY (`rfc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa_config`
--

LOCK TABLES `empresa_config` WRITE;
/*!40000 ALTER TABLE `empresa_config` DISABLE KEYS */;
INSERT INTO `empresa_config` VALUES ('DRO700527V91','Corchito','f1a4d39d-b08f-4926-9779-c0346069fdef.jpg','Ser la mejor empresa concesionaria en la provisión de los productos más vanguardistas y de alta calidad de Grupo Modelo.',150,15,'samuelbj0608@gmail.com','xafj attx njcu mgqk');
/*!40000 ALTER TABLE `empresa_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estados_envio`
--

DROP TABLE IF EXISTS `estados_envio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estados_envio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vnta_id` int NOT NULL COMMENT 'ID de la venta',
  `estado` int NOT NULL DEFAULT '1' COMMENT '1 = Pendiente, 2 = En preparación, 3 = En ruta, 4 = Entregado, 5 = Cancelado',
  `fecha` timestamp NOT NULL DEFAULT (now()) COMMENT 'Fecha y hora del cambio de estado',
  PRIMARY KEY (`id`),
  KEY `vnta_id` (`vnta_id`) USING BTREE,
  CONSTRAINT `detalles_envio_ibfk_1` FOREIGN KEY (`vnta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados_envio`
--

LOCK TABLES `estados_envio` WRITE;
/*!40000 ALTER TABLE `estados_envio` DISABLE KEYS */;
/*!40000 ALTER TABLE `estados_envio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gerentes_pv`
--

DROP TABLE IF EXISTS `gerentes_pv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gerentes_pv` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `pv_id` int DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `pv_id` (`pv_id`),
  CONSTRAINT `gerentes_pv_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gerentes_pv_ibfk_2` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gerentes_pv`
--

LOCK TABLES `gerentes_pv` WRITE;
/*!40000 ALTER TABLE `gerentes_pv` DISABLE KEYS */;
INSERT INTO `gerentes_pv` VALUES (6,36,1,NULL),(7,42,6,NULL);
/*!40000 ALTER TABLE `gerentes_pv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horarios_laborales`
--

DROP TABLE IF EXISTS `horarios_laborales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horarios_laborales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia_id` int NOT NULL COMMENT 'ID del punto de venta',
  `h_apertura` time DEFAULT NULL COMMENT 'Hora de inicio de labores',
  `h_cierre` time DEFAULT NULL COMMENT 'Hora de fin de labores',
  PRIMARY KEY (`id`),
  KEY `FK_horarios_dias_laborables` (`dia_id`),
  CONSTRAINT `FK_horarios_dias_laborables` FOREIGN KEY (`dia_id`) REFERENCES `dias_laborales` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios_laborales`
--

LOCK TABLES `horarios_laborales` WRITE;
/*!40000 ALTER TABLE `horarios_laborales` DISABLE KEYS */;
INSERT INTO `horarios_laborales` VALUES (1,1,'08:00:00','18:00:00'),(2,2,'08:00:00','18:00:00'),(3,3,'03:00:00','18:00:00'),(4,4,'08:00:00','18:00:00'),(5,5,'08:00:00','18:00:00'),(6,6,'08:00:00','18:00:00'),(8,8,'07:00:00','13:00:00'),(9,8,'15:00:00','19:00:00'),(10,9,'07:00:00','13:00:00'),(11,9,'15:00:00','19:00:00'),(12,10,'07:00:00','13:00:00'),(13,10,'15:00:00','19:00:00'),(14,11,'07:00:00','13:00:00'),(15,11,'15:00:00','19:00:00'),(16,12,'07:00:00','13:00:00'),(17,12,'15:00:00','19:00:00'),(18,13,'07:00:00','13:00:00'),(19,13,'15:00:00','19:00:00');
/*!40000 ALTER TABLE `horarios_laborales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidencias`
--

DROP TABLE IF EXISTS `incidencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidencias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ruta_id` int NOT NULL,
  `rep_id` int NOT NULL,
  `tipo` enum('retraso','desvio_ruta','falla_vehiculo','choque','robo','error_direccion') NOT NULL,
  `descrip` text,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lat` decimal(10,6) DEFAULT NULL,
  `lng` decimal(10,6) DEFAULT NULL,
  `estado` enum('pendiente','en_revision','resuelto','cancelado') DEFAULT 'pendiente',
  PRIMARY KEY (`id`),
  KEY `ruta_id` (`ruta_id`),
  KEY `rep_id` (`rep_id`),
  CONSTRAINT `incidencias_ibfk_1` FOREIGN KEY (`ruta_id`) REFERENCES `rutas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `incidencias_ibfk_2` FOREIGN KEY (`rep_id`) REFERENCES `repartidores` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencias`
--

LOCK TABLES `incidencias` WRITE;
/*!40000 ALTER TABLE `incidencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marcas`
--

DROP TABLE IF EXISTS `marcas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marcas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categ_id` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `logo` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_marcas_categoria` (`categ_id`),
  CONSTRAINT `FK_marcas_categoria` FOREIGN KEY (`categ_id`) REFERENCES `categorias` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marcas`
--

LOCK TABLES `marcas` WRITE;
/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;
INSERT INTO `marcas` VALUES (2,2,'Modelo','marcas/c5820529-6f94-4b08-9ded-7ec4a468fe94.jpg'),(3,2,'Corona','marcas/52ea3fb4-cef3-42a1-b2cd-27e5076d2b4c.jpg'),(8,2,'Victoria','marcas/6ffad478-9305-46a8-8d79-cbfbe5cf9f27.jpg'),(9,2,'Tecate','marcas/d256c0a3-402f-4433-bbb0-655a71f7cb8f.jpg'),(10,3,'Totopos','marcas/160f5e1a-8de4-41d5-848d-79c4f47f007d.jpg'),(12,2,'Barrilito','marcas/47453ba0-fbed-44ab-9c11-2e0c5e4dd4e9.jpg'),(13,4,'Pepsi','marcas/859309c4-cc42-4166-a6c8-8186dc9d0531.jpg'),(14,4,'Miranda','marcas/ea05982f-a001-4007-a75c-4879f0c84aa8.jpg'),(16,4,'Fanta','marcas/c07eef48-722c-4125-ad85-08acb01097cb.jpg'),(20,3,'Sabritas','marcas/465d178d-3d18-4013-a666-0ceba5da30ea.jpg'),(21,3,'Doritos','marcas/dbace182-e14f-4ef2-b691-cc69b413cdb0.jpg');
/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT 'ID del usuario al que se le envía la notificación',
  `titulo` varchar(50) NOT NULL COMMENT 'Título de la notificación',
  `subtitulo` varchar(150) NOT NULL COMMENT 'Subtitulo de la notificación',
  `fecha` timestamp NOT NULL DEFAULT (now()) COMMENT 'Fecha de creación',
  `leido` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Fecha de creación',
  PRIMARY KEY (`id`),
  KEY `FK_notificaciones_usuarios` (`user_id`),
  CONSTRAINT `FK_notificaciones_usuarios` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificaciones`
--

LOCK TABLES `notificaciones` WRITE;
/*!40000 ALTER TABLE `notificaciones` DISABLE KEYS */;
INSERT INTO `notificaciones` VALUES (51,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 03:13:33',0),(52,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 03:13:36',0),(53,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 04:10:41',0),(54,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 04:19:01',0),(55,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 04:19:02',0),(56,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 04:19:54',0),(57,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 04:19:55',0),(58,39,'Un cliente ha enviado un nuevo mensaje','hola','2025-12-10 07:29:02',0),(59,40,'Un cliente ha enviado un nuevo mensaje','hoal','2025-12-10 07:30:06',1),(60,39,'Un cliente ha enviado un nuevo mensaje','buenas, no ha subido su comprobante ','2025-12-10 08:36:58',0),(61,39,'Un cliente ha enviado un nuevo mensaje','hola','2025-12-10 08:37:35',0),(62,35,'Un repartidor te ha enviado un nuevo mensaje','hola ','2025-12-10 08:38:21',0),(63,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido.','2025-12-10 08:44:00',0),(64,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido.','2025-12-10 08:50:30',0),(65,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido.','2025-12-10 08:51:14',0),(66,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido.','2025-12-10 08:52:41',0),(67,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido.','2025-12-10 08:54:58',0),(68,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 09:24:36',0),(69,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-10 10:29:23',0),(70,21,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-11 08:40:43',0),(71,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-11 08:41:24',0),(72,35,'🚚 - Nuevo pedido asignado','Se te ha asignado un nuevo pedido cerca de tu ruta actual.','2025-12-11 09:30:06',0),(73,35,'Un repartidor te ha enviado un nuevo mensaje','gj','2025-12-11 09:45:40',0),(74,39,'Un cliente ha enviado un nuevo mensaje','estoy cerca de su domicilio ','2025-12-11 09:46:14',0);
/*!40000 ALTER TABLE `notificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `metodo` enum('efectivo','transferencia','terminal','link_mp') NOT NULL COMMENT 'Método de pago',
  `monto` decimal(10,2) NOT NULL COMMENT 'Monto total a pagar',
  `paga_con` decimal(10,2) DEFAULT NULL COMMENT 'Monto con el que paga el cliente (solo en efectivo)',
  `fecha` timestamp NULL DEFAULT NULL COMMENT 'Fecha y hora del pago',
  `compr` varchar(100) DEFAULT NULL COMMENT 'Descripción de la compra',
  `estado` enum('pendiente','pagado','rechazado') NOT NULL DEFAULT 'pendiente',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (148,'transferencia',17.01,NULL,NULL,'comprobantes/c7950f52-db79-4672-97c5-ff50c776c40d.jpg','pagado'),(149,'transferencia',159.60,NULL,NULL,'comprobantes/2442cf31-5d8e-4baf-8f69-7b0165463ef6.jpg','pagado'),(150,'efectivo',43.70,50.00,NULL,NULL,'pagado'),(151,'link_mp',98.52,NULL,NULL,NULL,'pagado'),(152,'terminal',100.00,NULL,NULL,NULL,'pagado'),(153,'efectivo',122.55,200.00,NULL,NULL,'pagado'),(154,'transferencia',278.35,NULL,NULL,NULL,'pagado'),(155,'link_mp',140.60,NULL,NULL,NULL,'pagado'),(156,'link_mp',65.55,NULL,NULL,NULL,'pendiente'),(157,'terminal',449.65,NULL,NULL,NULL,'pagado'),(158,'transferencia',212.70,NULL,NULL,'comprobantes/2b2dcca5-6893-4c8e-a539-387eac785ff3.jpg','pagado'),(159,'link_mp',933.85,NULL,NULL,NULL,'pagado'),(160,'link_mp',101.00,NULL,NULL,NULL,'pagado'),(161,'terminal',488.11,NULL,NULL,NULL,'pagado'),(162,'transferencia',227.05,NULL,NULL,NULL,'pagado'),(163,'terminal',278.07,NULL,NULL,NULL,'pagado'),(164,'terminal',40.75,NULL,NULL,NULL,'pagado'),(165,'terminal',17.01,NULL,NULL,NULL,'pendiente'),(166,'transferencia',227.05,NULL,NULL,'comprobantes/ae4849d9-9f76-4c82-a851-c3437f1efd14.jpg','pagado'),(167,'transferencia',290.95,NULL,NULL,'comprobantes/65d70e11-e54f-4772-aba5-e0be7ac56d9d.jpg','pagado');
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `curp` varchar(18) NOT NULL,
  `tel` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `app` varchar(50) NOT NULL,
  `apm` varchar(50) NOT NULL,
  `fech_nac` date NOT NULL,
  `sexo` enum('MASCULINO','FEMENINO') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `curp` (`curp`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` VALUES (17,'admin','9361165168','admin','admin','admin','2003-08-06','MASCULINO'),(35,'MAHJ030812HTRGRSA5','9361165168','JOSUE','MAGAÑA','HERNANDEZ','2003-08-12','MASCULINO'),(48,'MHJU070803HTCRRV1','9361145615','JESUS MIGUEL','MARQUEZ','MENDEZ','2003-08-13','MASCULINO'),(50,'VJMS960012HTCRRMA8','93611456159','VICENTE','JIMENEZ','PEREZ','1994-01-12','MASCULINO'),(51,'JKBJ010312HTCRRMK9','9361153424','JUAN','VELÁZQUEZ','JIMÉNEZ ','1991-11-06','MASCULINO'),(55,'BUAA691014HCSRLL05','569885','ALFREDO','BURELOS','ALEJO','1969-10-14','MASCULINO'),(56,'JEHD740217MCSRRR08','9361145632','DORA MARIA','JERONIMO','HERNANDEZ','1974-02-17','MASCULINO'),(57,'NJAS030401HTCRRMA8','978117291','SANTIAGO','PEREZ','CORNELIO','2002-08-14','MASCULINO'),(58,'JICM030101HTCRRMP8','9361165436','MANUEL','JIMENEZ','PEREZ','2002-05-09','MASCULINO');
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marca_id` int DEFAULT NULL COMMENT 'ID de la marca',
  `imagen` varchar(100) NOT NULL COMMENT 'Imagen del producto',
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre del producto',
  `precio` decimal(6,2) NOT NULL COMMENT 'Precio del producto',
  `peso_kg` decimal(10,8) NOT NULL COMMENT 'Peso en kg',
  `vol_m3` decimal(10,8) NOT NULL COMMENT 'Volumen en m3',
  PRIMARY KEY (`id`),
  KEY `FK_productos_marcas` (`marca_id`),
  CONSTRAINT `FK_productos_marcas` FOREIGN KEY (`marca_id`) REFERENCES `marcas` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1765455377 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,3,'productos/ff495738-70fe-44e8-9763-d1efcf8131df.jpg','Corona Extra 355ml',25.00,0.60000000,0.00065000),(2,3,'productos/77ba11f2-dffd-4a40-af51-efa1ab58ebc7.jpg','Cerveza Corona 473 ml 4 PZS',17.90,2.80000000,0.00250000),(4,3,'productos/27422288-5bd5-4e26-8c56-a49281bc002f.jpg','Cerveza clara Coronita Extra 24 botellas de 210 ml c/u',239.00,9.00000000,0.03000000),(5,8,'productos/df70d3b4-6bf5-4f97-9b5b-5100fa3a6cc2.jpg','Pack de cerveza Victoria ambar con 24 botellas de 210 ml c/u',239.00,9.20000000,0.03100000),(6,12,'productos/e3719da3-2e48-4e9e-8255-f0f1ec5254bd.jpg','Cerveza clara Barrilito 6 botellas de 325 ml c/u',74.00,3.90000000,0.00750000),(7,13,'productos/81f733ff-9b8c-4556-9c9b-82d4ff88f053.jpg','Refresco Pepsi regular 2.5L',33.00,2.60000000,0.00400000),(8,14,'productos/10bff2cb-7523-4859-90bc-d54c2dbdafb3.jpg','Refresco Mirinda sabor naranja botella de 2.5L',34.00,2.60000000,0.00400000),(1763617797,3,'productos/d8d60726-8b27-43ba-b7c5-e087a57e976b.jpg','Corona Light 355ml',24.00,0.58000000,0.00065000),(1763617839,3,'productos/6c8b7143-c57e-44e9-86a0-884353c7be60.jpg','Corona Mega 1.2L',42.00,1.40000000,0.00120000),(1763617882,3,'productos/452adcf3-eab3-4081-9581-8d1399553c92.jpg','Corona Familiar 940ml',38.00,1.25000000,0.00100000),(1763618175,3,'productos/5d749bc7-453b-4fb6-97f0-8154717532df.jpg','Corona Cero 355ml',26.00,0.40000000,0.00065000),(1763618211,2,'productos/84706ce7-b136-42e8-ab82-a0869b219d65.jpg','Modelo Especial 355ml',32.00,0.62000000,0.00066000),(1763618295,2,'productos/53d1bdc0-4d2f-43f6-b8c7-c0caa0f8959c.jpg','Cerveza Especial 6 Pack Nr 355 Ml',135.00,3.72000000,0.00396000),(1763618473,2,'productos/b77b8d3b-c0b4-4a5e-bd62-4edfd6036d30.jpg','Negra Modelo 355ml',29.00,0.63000000,0.00066000),(1763618550,3,'productos/0085c562-65b9-45d2-a2e9-9adabd06e152.jpg','Modelo Ámbar 355ml',27.00,0.62000000,0.00062000),(1763618585,2,'productos/408acb2e-1f5f-49d1-8136-3920dbbd0492.jpg','Modelo Especial Lata 473ml',23.00,0.70000000,0.00070000),(1763618790,2,'productos/afbc304c-62f4-456f-91e6-486f02431445.jpg','Modelo Especial Mega 1.2L',45.00,1.45000000,0.00012000),(1763618988,9,'productos/8cd0b92a-a2db-41af-9284-adf1a2d1e476.jpg','Tecate Roja 355ml',23.00,0.58000000,0.00065000),(1765454750,20,'productos/3dc092f2-f4c6-4c8e-9729-f6d81ebc5473.jpg','Sabritas originales 160g',24.00,0.16000000,0.00012000),(1765455376,21,'productos/f7fc4ca2-0d4e-4db9-af01-a58e22549ebd.jpg','Doritos 167g',18.00,0.16700000,0.00001200);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puntos_venta`
--

DROP TABLE IF EXISTS `puntos_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puntos_venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre del punto de venta, puede ser la dirección o un nombre comercial',
  `direc_id` int NOT NULL COMMENT 'ID de la dirección',
  `config_rfc` varchar(20) NOT NULL COMMENT 'RFC de la empresa',
  `zona_permitida` text NOT NULL COMMENT 'Zona permitida en formato JSON [{"lat":0,"lng":0},{"lat":0,"lng":0}]',
  `estado` enum('habilitado','deshabilitado') NOT NULL DEFAULT 'habilitado',
  `color` varchar(25) NOT NULL DEFAULT '#00ff11ff' COMMENT 'Color representativo del punto de venta',
  PRIMARY KEY (`id`),
  KEY `FK_puntos_venta_direcciones` (`direc_id`),
  KEY `FK_puntos_venta_empresa_config` (`config_rfc`),
  CONSTRAINT `FK_puntos_venta_direcciones` FOREIGN KEY (`direc_id`) REFERENCES `direcciones` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_puntos_venta_empresa_config` FOREIGN KEY (`config_rfc`) REFERENCES `empresa_config` (`rfc`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntos_venta`
--

LOCK TABLES `puntos_venta` WRITE;
/*!40000 ALTER TABLE `puntos_venta` DISABLE KEYS */;
INSERT INTO `puntos_venta` VALUES (1,'Q92R+2P Macuspana, Tabasco.',1,'DRO700527V91','[{\"lat\":17.743343415559103,\"lng\":-92.62033872419875},{\"lat\":17.747779232366643,\"lng\":-92.61099444071475},{\"lat\":17.750141725755075,\"lng\":-92.607562365531},{\"lat\":17.751103800667543,\"lng\":-92.6074515300474},{\"lat\":17.751498995137162,\"lng\":-92.60617932124474},{\"lat\":17.753460781455384,\"lng\":-92.6051099111159},{\"lat\":17.754526496403148,\"lng\":-92.60576708367665},{\"lat\":17.755420526400712,\"lng\":-92.60610879185192},{\"lat\":17.756561246222926,\"lng\":-92.60511800969064},{\"lat\":17.758453041790254,\"lng\":-92.60514122008007},{\"lat\":17.76159903353849,\"lng\":-92.60496481426591},{\"lat\":17.761854955272593,\"lng\":-92.600560837757},{\"lat\":17.757973057164328,\"lng\":-92.59774489924231},{\"lat\":17.757827022477436,\"lng\":-92.60020945233256},{\"lat\":17.75029537552799,\"lng\":-92.60545111166059},{\"lat\":17.748167687824296,\"lng\":-92.60711159963844},{\"lat\":17.747430662087385,\"lng\":-92.60767951576042},{\"lat\":17.747509144821926,\"lng\":-92.6084727634694},{\"lat\":17.74594162000117,\"lng\":-92.61006646265837},{\"lat\":17.74495172059465,\"lng\":-92.61209966170144},{\"lat\":17.742362070584235,\"lng\":-92.61928018639026},{\"lat\":17.742049140794354,\"lng\":-92.62281713444122},{\"lat\":17.74397580271168,\"lng\":-92.62329322680739},{\"lat\":17.746470021961947,\"lng\":-92.62285705820727},{\"lat\":17.746557356102514,\"lng\":-92.6211688431074}]','habilitado','#61ffc2ff'),(6,'Centro C. Francisco I. Madero 705.',15,'DRO700527V91','[{\"lat\":17.765622938687407,\"lng\":-92.594417862419},{\"lat\":17.765622938687407,\"lng\":-92.590417862419},{\"lat\":17.763489870468206,\"lng\":-92.5903277297842},{\"lat\":17.762880745124157,\"lng\":-92.59163456652813},{\"lat\":17.757313711150985,\"lng\":-92.5939519235539},{\"lat\":17.754984262522072,\"lng\":-92.60578065914109},{\"lat\":17.763005959942483,\"lng\":-92.60435712287239}]','habilitado','#ddff63ff');
/*!40000 ALTER TABLE `puntos_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repart_asign`
--

DROP TABLE IF EXISTS `repart_asign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repart_asign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rep_id` int NOT NULL COMMENT 'ID del repartidor',
  `pv_id` int NOT NULL COMMENT 'ID del punto de venta',
  `fecha_inicio` date NOT NULL COMMENT 'Fecha en la que se inició la asignación',
  `fecha_fin` date DEFAULT NULL COMMENT 'Fecha en la que se terminó la asignación',
  PRIMARY KEY (`id`),
  KEY `repartidor_id` (`rep_id`),
  KEY `pv_id` (`pv_id`),
  CONSTRAINT `repartidor_asignacion_ibfk_1` FOREIGN KEY (`rep_id`) REFERENCES `repartidores` (`id`),
  CONSTRAINT `repartidor_asignacion_ibfk_2` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repart_asign`
--

LOCK TABLES `repart_asign` WRITE;
/*!40000 ALTER TABLE `repart_asign` DISABLE KEYS */;
INSERT INTO `repart_asign` VALUES (22,7,1,'2025-11-17',NULL),(28,9,6,'2025-12-08',NULL),(29,10,1,'2025-12-11',NULL);
/*!40000 ALTER TABLE `repart_asign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repartidores`
--

DROP TABLE IF EXISTS `repartidores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repartidores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `veh_id` int DEFAULT NULL,
  `lat` decimal(10,6) DEFAULT NULL,
  `lng` decimal(10,6) DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `estado` enum('en_espera','cargando','en_ruta','descansando') DEFAULT 'en_espera',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `veh_id` (`veh_id`),
  CONSTRAINT `repartidores_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `repartidores_ibfk_2` FOREIGN KEY (`veh_id`) REFERENCES `vehiculos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repartidores`
--

LOCK TABLES `repartidores` WRITE;
/*!40000 ALTER TABLE `repartidores` DISABLE KEYS */;
INSERT INTO `repartidores` VALUES (7,21,3,17.747311,-92.609919,'2025-12-11 09:27:33','en_ruta'),(8,33,1,17.762138,-92.604028,NULL,'en_espera'),(9,35,5,17.747313,-92.609902,'2025-12-11 09:44:42','en_espera'),(10,41,6,NULL,NULL,NULL,'en_espera');
/*!40000 ALTER TABLE `repartidores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutas`
--

DROP TABLE IF EXISTS `rutas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pv_id` int DEFAULT NULL,
  `rep_id` int DEFAULT NULL COMMENT 'ID del repartidor',
  `fech_in` timestamp NOT NULL DEFAULT (now()) COMMENT 'Fecha de creación de la ruta',
  `fech_fin` timestamp NULL DEFAULT NULL COMMENT 'Fecha de fin de la ruta',
  `estado` enum('pendiente','en_ruta','finalizada') NOT NULL DEFAULT 'pendiente',
  PRIMARY KEY (`id`),
  KEY `repartidor_id` (`rep_id`),
  KEY `FK_rutas_puntos_venta` (`pv_id`),
  CONSTRAINT `FK_rutas_puntos_venta` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `rutas_ibfk_1` FOREIGN KEY (`rep_id`) REFERENCES `repartidores` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas`
--

LOCK TABLES `rutas` WRITE;
/*!40000 ALTER TABLE `rutas` DISABLE KEYS */;
INSERT INTO `rutas` VALUES (88,1,7,'2025-12-10 03:13:33','2025-12-10 08:44:00','finalizada'),(89,6,9,'2025-12-10 03:13:36','2025-12-10 08:50:30','finalizada'),(90,1,7,'2025-12-10 08:44:00','2025-12-10 08:51:14','finalizada'),(91,6,9,'2025-12-10 08:50:30','2025-12-10 08:54:58','finalizada'),(92,1,7,'2025-12-10 08:51:14','2025-12-10 08:52:41','finalizada'),(93,1,7,'2025-12-10 08:52:41','2025-12-10 08:54:02','finalizada'),(94,6,9,'2025-12-10 08:54:58','2025-12-10 09:53:08','finalizada'),(95,1,7,'2025-12-10 09:24:36','2025-12-10 09:52:41','finalizada'),(96,6,9,'2025-12-10 10:29:23','2025-12-11 08:26:05','finalizada'),(97,1,7,'2025-12-11 08:40:43',NULL,'en_ruta'),(98,6,9,'2025-12-11 08:41:24','2025-12-11 09:47:59','finalizada');
/*!40000 ALTER TABLE `rutas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutas_detalle`
--

DROP TABLE IF EXISTS `rutas_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutas_detalle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ruta_id` int DEFAULT NULL COMMENT 'ID de la ruta',
  `venta_id` int NOT NULL COMMENT 'ID de la venta',
  `lat` decimal(11,8) NOT NULL COMMENT 'Latitud del punto de entrega',
  `lng` decimal(11,8) NOT NULL COMMENT 'Longitud del punto de entrega',
  `ref` varchar(255) NOT NULL COMMENT 'Referencia del punto de entrega',
  PRIMARY KEY (`id`),
  KEY `ruta_id` (`ruta_id`),
  KEY `venta_id` (`venta_id`),
  CONSTRAINT `ruta_detalle_ibfk_1` FOREIGN KEY (`ruta_id`) REFERENCES `rutas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ruta_detalle_ibfk_2` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas_detalle`
--

LOCK TABLES `rutas_detalle` WRITE;
/*!40000 ALTER TABLE `rutas_detalle` DISABLE KEYS */;
INSERT INTO `rutas_detalle` VALUES (118,88,143,17.76014060,-92.60143318,'Cerca de mata de capulín'),(119,89,144,17.76129561,-92.59495467,'gbhj'),(120,89,145,17.76478980,-92.59368362,'Cerca de mata de capulín'),(121,88,146,17.75893612,-92.60166946,'gbhj'),(122,88,147,17.75820665,-92.60178603,'gbhj'),(123,89,148,17.76094912,-92.59712249,'Cerca de mata de capulín'),(124,89,149,17.76333016,-92.59202765,'Cerca de mata de capulín'),(125,88,150,17.75895281,-92.59916286,'gbhj'),(126,91,151,17.76392967,-92.59823215,'Cerca de mata de capulín'),(127,94,152,17.76503095,-92.59523421,'Cerca de mata de capulín'),(128,90,153,17.75895281,-92.59916286,'gbhj'),(129,90,154,17.76096886,-92.60075238,'gbhj'),(130,92,155,17.75919646,-92.60164902,'gbhj'),(131,93,156,17.76027211,-92.60010470,'gbhj'),(132,95,157,17.75895281,-92.59916286,'gbhj'),(133,96,158,17.76503095,-92.59523421,'Cerca de mata de capulín'),(134,97,159,17.75895281,-92.59916286,'gbhj'),(135,98,160,17.76503095,-92.59523421,'Cerca de mata de capulín'),(136,98,161,17.76503095,-92.59523421,'Cerca de mata de capulín');
/*!40000 ALTER TABLE `rutas_detalle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `per_id` int NOT NULL,
  `pv_id` int DEFAULT NULL COMMENT 'ID del punto de venta (Para los gerentes representa el punto de venta que administran, para los clientes el punto de venta predeterminado y para los repartidores el punto de venta al que están asignados)',
  `correo` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token_fbm` varchar(255) DEFAULT NULL COMMENT 'Token de Firebase para notificaciones push',
  `estado` enum('activo','suspendido') NOT NULL DEFAULT 'activo' COMMENT '0 = Activa, 1 = Suspendida',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `rol` enum('ADMIN','GERENTE','CLIENTE','REPARTIDOR') NOT NULL DEFAULT 'CLIENTE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  KEY `FK_usuarios_personas` (`per_id`),
  KEY `FK_usuarios_puntos_venta` (`pv_id`),
  CONSTRAINT `FK_usuarios_personas` FOREIGN KEY (`per_id`) REFERENCES `personas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_usuarios_puntos_venta` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (9,17,1,'admin@gmail.com','admin',NULL,'activo','2025-09-27 23:09:49','ADMIN'),(21,35,1,'repartidor@gmail.com','repartidor',NULL,'activo','2025-10-29 17:16:50','REPARTIDOR'),(33,48,1,'repartidor2@gmail.com','repartidor',NULL,'activo','2025-11-13 15:50:43','REPARTIDOR'),(35,50,6,'repartidor3@gmail.com','repartidor','eO4momCOSeieKqlFe9RQpo:APA91bEGQuiqQkE0pCAoHY1yRlaemQQiCyzjHkvi1S663VCXWnVCvs18Dq9SC5E-jiciQEwpdBSKUOZ66MC7pTYeyBVvGuUYTCzo3OenRD0Snx2qn2izvTc','activo','2025-11-30 22:31:49','REPARTIDOR'),(36,51,1,'gerente@gmail.com','gerente',NULL,'activo','2025-12-06 20:56:09','GERENTE'),(39,55,6,'cliente@gmail.com','cliente',NULL,'activo','2025-12-09 23:57:39','CLIENTE'),(40,56,1,'cliente1@gmail.com','cliente',NULL,'activo','2025-12-10 00:07:01','CLIENTE'),(41,57,1,'repartidor4@gmail.com','repartidor',NULL,'activo','2025-12-10 22:27:12','REPARTIDOR'),(42,58,6,'gerente2@gmail.com','gerente',NULL,'activo','2025-12-11 08:38:32','GERENTE');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehiculos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `placa` varchar(15) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  `tipo` enum('moto','auto','camioneta','bicicleta') NOT NULL,
  `capacidad_kg` decimal(6,2) NOT NULL DEFAULT '0.00',
  `volumen_m3` decimal(6,3) NOT NULL DEFAULT '0.000',
  `factor_uso_max` decimal(4,2) NOT NULL DEFAULT '0.80' COMMENT 'Porcentaje máximo de uso permitido',
  PRIMARY KEY (`id`),
  UNIQUE KEY `placa` (`placa`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
INSERT INTO `vehiculos` VALUES (1,'JDIHDI773','Italika','110 AT',1,'moto',55.00,0.180,0.80),(3,'TSK-92A1','Italika','FT150',1,'moto',45.00,0.160,0.80),(5,'XMN-44Z8','Honda','Dio 110',1,'moto',60.00,0.200,0.80),(6,'KJJ1KS991','Italika','150',1,'moto',49.00,0.190,0.80);
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int DEFAULT NULL COMMENT 'ID del cliente',
  `pv_id` int DEFAULT NULL COMMENT 'ID del punto de venta',
  `pago_id` int NOT NULL COMMENT 'ID del pago',
  `fecha_inic` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de la creación de la venta',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Fecha y hora de la actualización de la venta',
  `fecha_fin` timestamp NULL DEFAULT NULL COMMENT 'Fecha y hora de la finalización de la venta',
  `estado` enum('Pendiente','En_camino','Entregado','Cancelado') NOT NULL DEFAULT 'Pendiente' COMMENT 'Estado de la venta',
  `calif` int DEFAULT NULL COMMENT 'Calificación del usuario por la venta',
  PRIMARY KEY (`id`),
  KEY `FK_ventas_pagos` (`pago_id`),
  KEY `FK_ventas_puntos_venta` (`pv_id`),
  KEY `FK_ventas_clientes` (`cliente_id`),
  CONSTRAINT `FK_ventas_clientes` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `FK_ventas_pagos` FOREIGN KEY (`pago_id`) REFERENCES `pagos` (`id`),
  CONSTRAINT `FK_ventas_puntos_venta` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (143,12,1,149,'2025-12-10 03:13:33','2025-12-10 07:29:17','2025-12-10 07:29:17','Entregado',NULL),(144,13,6,150,'2025-12-10 03:13:36','2025-12-10 08:49:48','2025-12-10 08:49:49','Entregado',NULL),(145,12,6,151,'2025-12-10 04:10:06','2025-12-10 08:50:07','2025-12-10 08:50:08','Entregado',NULL),(146,13,1,152,'2025-12-10 04:10:41','2025-12-10 07:30:12','2025-12-10 07:30:12','Entregado',NULL),(147,13,1,153,'2025-12-10 04:19:01','2025-12-10 08:44:00','2025-12-10 08:02:48','Entregado',NULL),(148,12,6,154,'2025-12-10 04:19:02','2025-12-10 08:49:29','2025-12-10 08:49:30','Entregado',NULL),(149,12,6,155,'2025-12-10 04:19:54','2025-12-10 08:50:24','2025-12-10 08:50:24','Entregado',NULL),(150,13,1,156,'2025-12-10 04:19:55','2025-12-10 08:44:00','2025-12-10 07:50:19','Entregado',NULL),(151,12,6,157,'2025-12-10 08:26:12','2025-12-10 08:54:50','2025-12-10 08:54:50','Entregado',NULL),(152,12,6,158,'2025-12-10 08:27:08','2025-12-10 09:53:03','2025-12-10 09:53:04','Entregado',NULL),(153,13,1,159,'2025-12-10 08:28:02','2025-12-10 08:51:08','2025-12-10 08:51:09','Entregado',NULL),(154,13,1,160,'2025-12-10 08:28:54','2025-12-10 08:50:53','2025-12-10 08:50:53','Entregado',NULL),(155,13,1,161,'2025-12-10 08:46:35','2025-12-10 08:52:33','2025-12-10 08:52:34','Entregado',NULL),(156,13,1,162,'2025-12-10 08:48:03','2025-12-10 08:53:52','2025-12-10 08:53:52','Entregado',NULL),(157,13,1,163,'2025-12-10 09:24:36','2025-12-10 09:52:37','2025-12-10 09:52:37','Entregado',NULL),(158,12,6,164,'2025-12-10 10:29:23','2025-12-11 14:25:59','2025-12-11 08:26:00','Entregado',NULL),(159,13,1,165,'2025-12-11 08:40:43','2025-12-11 15:35:30',NULL,'En_camino',NULL),(160,12,6,166,'2025-12-11 08:41:24','2025-12-11 15:47:53','2025-12-11 09:47:54','Entregado',NULL),(161,12,6,167,'2025-12-11 09:30:06','2025-12-11 15:47:26','2025-12-11 09:47:26','Entregado',NULL);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-12 13:50:15
