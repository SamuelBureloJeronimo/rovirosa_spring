-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: localhost    Database: rovirosa
-- ------------------------------------------------------
-- Server version	8.0.43-0ubuntu0.24.04.2

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo_pv`
--

LOCK TABLES `catalogo_pv` WRITE;
/*!40000 ALTER TABLE `catalogo_pv` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `mensaje` varchar(255) NOT NULL COMMENT 'Mensaje de texto',
  `enviado` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de envío del mensaje',
  `archivo` varchar(50) DEFAULT NULL COMMENT 'Nombre del archivo si se envió una imagen o documento',
  PRIMARY KEY (`id`),
  KEY `FK__ventas` (`vnta_id`),
  KEY `FK_chat_venta_usuarios` (`user_id`),
  CONSTRAINT `FK__ventas` FOREIGN KEY (`vnta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_chat_venta_usuarios` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_venta`
--

LOCK TABLES `chat_venta` WRITE;
/*!40000 ALTER TABLE `chat_venta` DISABLE KEYS */;
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
  `pv_id` int NOT NULL COMMENT 'ID del punto de venta',
  `ine_front` varchar(50) NOT NULL COMMENT 'INE frontal',
  `ine_back` varchar(50) NOT NULL COMMENT 'INE posterior',
  `stricks` int NOT NULL DEFAULT '0' COMMENT 'Número de stricks (máximo 3)',
  `p_cancel` int NOT NULL DEFAULT '0' COMMENT 'Número de cancelaciones (máximo 3 al mes)',
  `last_cancel` date DEFAULT NULL COMMENT 'Fecha del último pedido cancelado',
  PRIMARY KEY (`id`),
  KEY `FK_clientes_direcciones` (`dir_id`),
  KEY `FK_clientes_usuarios` (`user_id`),
  KEY `FK_pv_cliente` (`pv_id`),
  CONSTRAINT `FK_clientes_direcciones` FOREIGN KEY (`dir_id`) REFERENCES `direcciones` (`id`),
  CONSTRAINT `FK_clientes_usuarios` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_pv_cliente` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentos_categ`
--

LOCK TABLES `descuentos_categ` WRITE;
/*!40000 ALTER TABLE `descuentos_categ` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentos_config`
--

LOCK TABLES `descuentos_config` WRITE;
/*!40000 ALTER TABLE `descuentos_config` DISABLE KEYS */;
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
  KEY `FK_descuento_marca_descuentos_config` (`config_id`),
  KEY `FK_descuento_marca_descuentos_config_2` (`marca_id`),
  CONSTRAINT `FK_descuento_marca_descuentos_config` FOREIGN KEY (`config_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_descuento_marca_descuentos_config_2` FOREIGN KEY (`marca_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_venta`
--

LOCK TABLES `detalles_venta` WRITE;
/*!40000 ALTER TABLE `detalles_venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalles_venta` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direcciones`
--

LOCK TABLES `direcciones` WRITE;
/*!40000 ALTER TABLE `direcciones` DISABLE KEYS */;
INSERT INTO `direcciones` VALUES (1,17.75008764,-92.60813767,''),(2,17.76364885,-92.59267079,'');
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
  `email_app` varchar(100) DEFAULT NULL COMMENT 'Email de la aplicación',
  `codigo_app` varchar(25) DEFAULT NULL COMMENT 'Código de la aplicación (lrhm upio jpnh tvpv)',
  PRIMARY KEY (`rfc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa_config`
--

LOCK TABLES `empresa_config` WRITE;
/*!40000 ALTER TABLE `empresa_config` DISABLE KEYS */;
INSERT INTO `empresa_config` VALUES ('DRO700527V91','Corchito','f1a4d39d-b08f-4926-9779-c0346069fdef.jpg','Ser la mejor empresa concesionaria en la provisión de los productos más vanguardistas y de alta calidad de Grupo Modelo.',150,'samuelbj0608@gmail.com','2343243j43n43dfdf');
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
  `pv_id` int NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `pv_id` (`pv_id`),
  CONSTRAINT `gerentes_pv_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gerentes_pv_ibfk_2` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gerentes_pv`
--

LOCK TABLES `gerentes_pv` WRITE;
/*!40000 ALTER TABLE `gerentes_pv` DISABLE KEYS */;
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
  `pv_id` int NOT NULL COMMENT 'ID del punto de venta',
  `dia_semana` enum('lunes','martes','miercoles','jueves','viernes','sabado','domingo') NOT NULL,
  `h_apertura` time NOT NULL,
  `h_cierre` time NOT NULL,
  `estado` enum('abierto','cerrado') NOT NULL DEFAULT 'abierto',
  PRIMARY KEY (`id`),
  KEY `FK_horarios_punto_venta` (`pv_id`),
  CONSTRAINT `FK_horarios_punto_venta` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios_laborales`
--

LOCK TABLES `horarios_laborales` WRITE;
/*!40000 ALTER TABLE `horarios_laborales` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marcas`
--

LOCK TABLES `marcas` WRITE;
/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;
INSERT INTO `marcas` VALUES (1,2,'Heineken','marcas/7e7a0bc7-dd58-4d30-aef9-4da875e77984.jpg'),(2,2,'Modelo','marcas/c5820529-6f94-4b08-9ded-7ec4a468fe94.jpg'),(3,2,'Corona','marcas/52ea3fb4-cef3-42a1-b2cd-27e5076d2b4c.jpg'),(4,2,'Dos equis','marcas/5093838c-765d-476d-9a4d-b69c9e55f67d.jpg'),(5,2,'Tecate','marcas/d256c0a3-402f-4433-bbb0-655a71f7cb8f.jpg'),(6,2,'Bohemia','marcas/34283cfa-c72a-4a99-93ff-46aeb32c8118.jpg'),(7,2,'Sol','marcas/00e2b4e5-19a1-4a01-94b3-59c951c6eece.jpg'),(8,2,'Victoria','marcas/6ffad478-9305-46a8-8d79-cbfbe5cf9f27.jpg'),(9,2,'Carta blanca','marcas/e5d1f35a-5b25-4a37-b178-9221da4d73ba.jpg'),(10,3,'Totopos','marcas/160f5e1a-8de4-41d5-848d-79c4f47f007d.jpg'),(11,2,'Estrella galicia','marcas/080408e1-508c-47a6-bb2e-e0ea3c3834e0.jpg'),(12,2,'Barrilito','marcas/47453ba0-fbed-44ab-9c11-2e0c5e4dd4e9.jpg'),(13,4,'Pepsi','marcas/859309c4-cc42-4166-a6c8-8186dc9d0531.jpg'),(14,4,'Miranda','marcas/ea05982f-a001-4007-a75c-4879f0c84aa8.jpg'),(15,4,'Cocacola','marcas/190b4a6a-d83f-4bbf-a274-c5bc168a41a9.jpg'),(16,4,'Fanta','marcas/c07eef48-722c-4125-ad85-08acb01097cb.jpg');
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
  PRIMARY KEY (`id`),
  KEY `FK_notificaciones_usuarios` (`user_id`),
  CONSTRAINT `FK_notificaciones_usuarios` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificaciones`
--

LOCK TABLES `notificaciones` WRITE;
/*!40000 ALTER TABLE `notificaciones` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
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
  UNIQUE KEY `curp` (`curp`),
  UNIQUE KEY `tel` (`tel`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` VALUES (17,'BUJS030806HTCRRMA9','9361165168','SAMUEL','BURELOS','JERONIMO','2003-08-06','MASCULINO');
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
  `marca_id` int NOT NULL COMMENT 'ID de la marca',
  `imagen` varchar(100) NOT NULL COMMENT 'Imagen del producto',
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre del producto',
  `precio` decimal(10,2) NOT NULL COMMENT 'Precio del producto',
  `peso_kg` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT 'Peso en kg',
  `vol_m3` decimal(6,3) NOT NULL DEFAULT '0.000' COMMENT 'Volumen en m3',
  PRIMARY KEY (`id`),
  KEY `FK_productos_marcas` (`marca_id`),
  CONSTRAINT `FK_productos_marcas` FOREIGN KEY (`marca_id`) REFERENCES `marcas` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,3,'productos/a291ed0e-b3ad-4cdc-acbe-a8827c17abdf.jpg','Cerveza Modelo 1/2 Corona',23.80,1,1),(2,3,'productos/0baa68a9-5649-4801-872d-8e1fcee6d0eb.jpg','Cerveza Corona 473 ml 4 PZS',92.89,1,1),(3,10,'productos/6e84a880-6676-42c9-a229-c36c23d6b467.jpg','Totopos Del Hogar 280 gr.',30.50,1,1),(4,3,'productos/27422288-5bd5-4e26-8c56-a49281bc002f.jpg','Cerveza clara Coronita Extra 24 botellas de 210 ml c/u',239.00,1,1),(5,8,'productos/df70d3b4-6bf5-4f97-9b5b-5100fa3a6cc2.jpg','Pack de cerveza Victoria ambar con 24 botellas de 210 ml c/u',239.00,1,1),(6,12,'productos/e3719da3-2e48-4e9e-8255-f0f1ec5254bd.jpg','Cerveza clara Barrilito 6 botellas de 325 ml c/u',74.00,1,1),(7,13,'productos/81f733ff-9b8c-4556-9c9b-82d4ff88f053.jpg','Refresco Pepsi regular 2.5L',33.00,1,1),(8,14,'productos/bc28e764-a74b-415d-a58e-330a2681c6ea.jpg','Refresco Mirinda sabor naranja botella de 2.5L',34.00,1,1),(9,14,'productos/0b776c07-a712-457b-8337-141b8dea97ef.jpg','rtwrwet',34545.00,1,1);
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
  `direc_id` int NOT NULL COMMENT 'ID de la dirección',
  `config_rfc` varchar(20) NOT NULL COMMENT 'RFC de la empresa',
  `zona_permitida` text NOT NULL COMMENT 'Zona permitida en formato JSON [{"lat":0,"lng":0},{"lat":0,"lng":0}]',
  `estado` enum('habilitado','deshabilitado') NOT NULL DEFAULT 'habilitado',
  PRIMARY KEY (`id`),
  KEY `FK_puntos_venta_direcciones` (`direc_id`),
  KEY `FK_puntos_venta_empresa_config` (`config_rfc`),
  CONSTRAINT `FK_puntos_venta_direcciones` FOREIGN KEY (`direc_id`) REFERENCES `direcciones` (`id`),
  CONSTRAINT `FK_puntos_venta_empresa_config` FOREIGN KEY (`config_rfc`) REFERENCES `empresa_config` (`rfc`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntos_venta`
--

LOCK TABLES `puntos_venta` WRITE;
/*!40000 ALTER TABLE `puntos_venta` DISABLE KEYS */;
INSERT INTO `puntos_venta` VALUES (1,1,'DRO700527V91','[{\"lat\":17.743343415559103,\"lng\":-92.62033872419875},{\"lat\":17.753403183674283,\"lng\":-92.60526040990995},{\"lat\":17.760062495806025,\"lng\":-92.60687627420029},{\"lat\":17.76159903353849,\"lng\":-92.60496481426591},{\"lat\":17.757801381469186,\"lng\":-92.59774489924231},{\"lat\":17.757827022477436,\"lng\":-92.60020945233256},{\"lat\":17.749308191624962,\"lng\":-92.60617217339379},{\"lat\":17.747395101075504,\"lng\":-92.60693133436885},{\"lat\":17.74558681052853,\"lng\":-92.60912853787121},{\"lat\":17.74495172059465,\"lng\":-92.61209966170144},{\"lat\":17.742362070584235,\"lng\":-92.61928018639026},{\"lat\":17.742049140794354,\"lng\":-92.62281713444122},{\"lat\":17.74397580271168,\"lng\":-92.62329322680739},{\"lat\":17.746470021961947,\"lng\":-92.62285705820727},{\"lat\":17.746557356102514,\"lng\":-92.6211688431074}]','habilitado');
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
  `activo` tinyint(1) DEFAULT '1' COMMENT '1 = Activo, 0 = Inactivo',
  PRIMARY KEY (`id`),
  KEY `repartidor_id` (`rep_id`),
  KEY `pv_id` (`pv_id`),
  CONSTRAINT `repartidor_asignacion_ibfk_1` FOREIGN KEY (`rep_id`) REFERENCES `repartidores` (`id`),
  CONSTRAINT `repartidor_asignacion_ibfk_2` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repart_asign`
--

LOCK TABLES `repart_asign` WRITE;
/*!40000 ALTER TABLE `repart_asign` DISABLE KEYS */;
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
  `estado` enum('en_espera','cargando','en_ruta','descansando') DEFAULT 'en_espera',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `veh_id` (`veh_id`),
  CONSTRAINT `repartidores_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `repartidores_ibfk_2` FOREIGN KEY (`veh_id`) REFERENCES `vehiculos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repartidores`
--

LOCK TABLES `repartidores` WRITE;
/*!40000 ALTER TABLE `repartidores` DISABLE KEYS */;
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
  `rep_id` int NOT NULL COMMENT 'ID del repartidor',
  `fech_in` timestamp NOT NULL DEFAULT (now()) COMMENT 'Fecha de creación de la ruta',
  `fech_fin` timestamp NULL DEFAULT NULL COMMENT 'Fecha de fin de la ruta',
  `estado` enum('pendiente','en_ruta','finalizada') NOT NULL DEFAULT 'pendiente',
  PRIMARY KEY (`id`),
  KEY `repartidor_id` (`rep_id`),
  CONSTRAINT `rutas_ibfk_1` FOREIGN KEY (`rep_id`) REFERENCES `repartidores` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas`
--

LOCK TABLES `rutas` WRITE;
/*!40000 ALTER TABLE `rutas` DISABLE KEYS */;
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
  `ruta_id` int NOT NULL COMMENT 'ID de la ruta',
  `venta_id` int NOT NULL COMMENT 'ID de la venta',
  `lat` decimal(11,8) NOT NULL COMMENT 'Latitud del punto de entrega',
  `lng` decimal(11,8) NOT NULL COMMENT 'Longitud del punto de entrega',
  PRIMARY KEY (`id`),
  KEY `ruta_id` (`ruta_id`),
  KEY `venta_id` (`venta_id`),
  CONSTRAINT `ruta_detalle_ibfk_1` FOREIGN KEY (`ruta_id`) REFERENCES `rutas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ruta_detalle_ibfk_2` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas_detalle`
--

LOCK TABLES `rutas_detalle` WRITE;
/*!40000 ALTER TABLE `rutas_detalle` DISABLE KEYS */;
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
  `correo` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `estado` enum('activo','suspendido') NOT NULL DEFAULT 'activo' COMMENT '0 = Activa, 1 = Suspendida',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `rol` enum('ADMIN','GERENTE','CLIENTE','REPARTIDOR') NOT NULL DEFAULT 'CLIENTE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  KEY `FK_usuarios_personas` (`per_id`),
  CONSTRAINT `FK_usuarios_personas` FOREIGN KEY (`per_id`) REFERENCES `personas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (9,17,'admin@gmail.com','admin','activo','2025-09-27 23:09:49','ADMIN');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
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
  `pago_id` int NOT NULL COMMENT 'ID del pago',
  `fecha_inic` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de la creación de la venta',
  `fecha_fin` timestamp NULL DEFAULT NULL COMMENT 'Fecha y hora de la finalización de la venta',
  `calif` int DEFAULT NULL COMMENT 'Calificación del usuario por la venta',
  PRIMARY KEY (`id`),
  KEY `FK_ventas_pagos` (`pago_id`),
  KEY `FK_ventas_clientes` (`cliente_id`),
  CONSTRAINT `FK_ventas_clientes` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `FK_ventas_pagos` FOREIGN KEY (`pago_id`) REFERENCES `pagos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
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

-- Dump completed on 2025-10-07 20:30:05
