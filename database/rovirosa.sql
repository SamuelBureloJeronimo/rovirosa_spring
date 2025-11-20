-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.30 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para rovirosa
CREATE DATABASE IF NOT EXISTS `rovirosa` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `rovirosa`;

-- Volcando estructura para tabla rovirosa.carrito
CREATE TABLE IF NOT EXISTS `carrito` (
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
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.carrito: ~3 rows (aproximadamente)
DELETE FROM `carrito`;
INSERT INTO `carrito` (`id`, `user_id`, `catalogo_id`, `cantidad`, `created`, `updated`) VALUES
	(67, 11, 4, 1, '2025-10-23 10:06:16', '2025-10-28 10:04:22'),
	(68, 11, 5, 2, '2025-10-28 10:04:11', '2025-10-28 10:04:16'),
	(252, 32, 35, 2, '2025-11-19 23:35:37', '2025-11-19 23:35:38'),
	(254, 32, 38, 1, '2025-11-19 23:35:49', '2025-11-19 23:35:49'),
	(255, 32, 38, 1, '2025-11-19 23:38:18', '2025-11-19 23:38:18'),
	(256, 32, 39, 2, '2025-11-19 23:38:20', '2025-11-19 23:38:21'),
	(257, 32, 36, 1, '2025-11-19 23:38:33', '2025-11-19 23:38:33');

-- Volcando estructura para tabla rovirosa.catalogo_pv
CREATE TABLE IF NOT EXISTS `catalogo_pv` (
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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.catalogo_pv: ~8 rows (aproximadamente)
DELETE FROM `catalogo_pv`;
INSERT INTO `catalogo_pv` (`id`, `prd_id`, `pv_id`, `stock`, `vendidos`) VALUES
	(2, 2, 1, 3, 0),
	(4, 4, 1, 12, 0),
	(5, 5, 1, 26, 0),
	(6, 6, 1, 17, 0),
	(7, 7, 1, 8, 0),
	(8, 8, 1, 39, 0),
	(33, 1, 6, 0, 0),
	(34, 4, 6, 3, 0),
	(35, 2, 6, 98, 0),
	(36, 5, 6, 199, 0),
	(37, 6, 6, 45, 0),
	(38, 7, 6, 5, 0),
	(39, 8, 6, 65, 0),
	(40, 1763617797, 1, 100, 0),
	(41, 1763617797, 6, 100, 0),
	(42, 1763617839, 1, 100, 0),
	(43, 1763617839, 6, 100, 0),
	(44, 1763617882, 1, 100, 0),
	(45, 1763617882, 6, 100, 0),
	(46, 1763618175, 1, 120, 0),
	(47, 1763618175, 6, 120, 0),
	(48, 1763618211, 1, 120, 0),
	(49, 1763618211, 6, 120, 0),
	(50, 1763618295, 1, 120, 0),
	(51, 1763618295, 6, 120, 0),
	(52, 1763618473, 1, 80, 0),
	(53, 1763618473, 6, 90, 0),
	(54, 1763618550, 1, 80, 0),
	(55, 1763618550, 6, 90, 0),
	(56, 1763618585, 1, 80, 0),
	(57, 1763618585, 6, 90, 0),
	(58, 1763618790, 1, 120, 0),
	(59, 1763618790, 6, 120, 0),
	(60, 1763618988, 1, 132, 0),
	(61, 1763618988, 6, 180, 0);

-- Volcando estructura para tabla rovirosa.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.categorias: ~5 rows (aproximadamente)
DELETE FROM `categorias`;
INSERT INTO `categorias` (`id`, `nombre`) VALUES
	(2, 'Cerveza'),
	(3, 'Sabritas'),
	(4, 'Refrescos');

-- Volcando estructura para tabla rovirosa.chat_venta
CREATE TABLE IF NOT EXISTS `chat_venta` (
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

-- Volcando datos para la tabla rovirosa.chat_venta: ~0 rows (aproximadamente)
DELETE FROM `chat_venta`;

-- Volcando estructura para tabla rovirosa.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.clientes: ~2 rows (aproximadamente)
DELETE FROM `clientes`;
INSERT INTO `clientes` (`id`, `user_id`, `dir_id`, `ine_front`, `ine_back`, `stricks`, `p_cancel`, `last_cancel`) VALUES
	(1, 11, 14, 'ine/c60f68a4-2356-4eb3-ad88-f2423ec004a1.jpg', 'ine/d567da78-39cc-4c28-a904-74dbfb037b74.jpg', 0, 0, NULL),
	(9, 32, 36, 'ine/2d13cbf0-092a-4a45-9d68-ef154fb882cf.jpg', 'ine/9ea31a60-a94e-42b3-8089-5de9ed4a98d2.jpg', 0, 0, NULL);

-- Volcando estructura para tabla rovirosa.datos_transferencia
CREATE TABLE IF NOT EXISTS `datos_transferencia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titular` varchar(255) NOT NULL,
  `banco` varchar(255) NOT NULL,
  `clave` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.datos_transferencia: ~0 rows (aproximadamente)
DELETE FROM `datos_transferencia`;
INSERT INTO `datos_transferencia` (`id`, `titular`, `banco`, `clave`) VALUES
	(1, 'Samuel Burelos Jeronimo', 'NU Bank', '638180010199083400');

-- Volcando estructura para tabla rovirosa.descuentos_categ
CREATE TABLE IF NOT EXISTS `descuentos_categ` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categ_id` int NOT NULL COMMENT 'ID de la categoría',
  `config_id` int NOT NULL COMMENT 'ID de la configuración del descuento',
  PRIMARY KEY (`id`),
  KEY `FK_descuento_categ_categorias` (`categ_id`),
  KEY `FK_descuento_categ_descuentos_config` (`config_id`),
  CONSTRAINT `FK_descuento_categ_categorias` FOREIGN KEY (`categ_id`) REFERENCES `categorias` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_descuento_categ_descuentos_config` FOREIGN KEY (`config_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.descuentos_categ: ~0 rows (aproximadamente)
DELETE FROM `descuentos_categ`;
INSERT INTO `descuentos_categ` (`id`, `categ_id`, `config_id`) VALUES
	(5, 2, 27);

-- Volcando estructura para tabla rovirosa.descuentos_config
CREATE TABLE IF NOT EXISTS `descuentos_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('porcentaje','fijo') NOT NULL COMMENT '1 = Porcentaje, 2 = Monto Fijo',
  `valor` double NOT NULL COMMENT 'Valor del descuento (0.10 = 10% o 50 = $50)',
  `objetivo` enum('producto','categoria','marca') NOT NULL COMMENT '1 = Producto, 2 = Categoria',
  `fech_in` date NOT NULL COMMENT 'Fecha de inicio del descuento',
  `fech_fin` date DEFAULT NULL COMMENT 'Fecha de fin del descuento',
  `banner` varchar(100) DEFAULT NULL COMMENT 'Si no sube un banner no se muestra en la pantalla de inicio',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.descuentos_config: ~3 rows (aproximadamente)
DELETE FROM `descuentos_config`;
INSERT INTO `descuentos_config` (`id`, `tipo`, `valor`, `objetivo`, `fech_in`, `fech_fin`, `banner`) VALUES
	(17, 'fijo', 12, 'producto', '2025-11-15', NULL, NULL),
	(18, 'porcentaje', 10, 'producto', '2025-11-15', NULL, NULL),
	(27, 'porcentaje', 5, 'categoria', '2025-11-19', NULL, 'descuentos/5537bd52-834b-4d8a-bd1e-04382e0517ef.jpg');

-- Volcando estructura para tabla rovirosa.descuentos_marca
CREATE TABLE IF NOT EXISTS `descuentos_marca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marca_id` int DEFAULT NULL COMMENT 'ID de la marca',
  `config_id` int DEFAULT NULL COMMENT 'ID de la configuración del descuento',
  PRIMARY KEY (`id`),
  KEY `FK_descuento_marca_descuentos_marca` (`marca_id`),
  KEY `FK_descuento_marca_descuentos_config` (`config_id`),
  CONSTRAINT `FK_descuento_marca_descuentos_config` FOREIGN KEY (`config_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_descuento_marca_descuentos_marca` FOREIGN KEY (`marca_id`) REFERENCES `marcas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.descuentos_marca: ~0 rows (aproximadamente)
DELETE FROM `descuentos_marca`;

-- Volcando estructura para tabla rovirosa.descuentos_producto
CREATE TABLE IF NOT EXISTS `descuentos_producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prd_id` int NOT NULL COMMENT 'ID del producto',
  `config_id` int NOT NULL COMMENT 'ID de la configuración del descuento',
  PRIMARY KEY (`id`),
  KEY `FK_descuentos_producto_productos` (`prd_id`),
  KEY `FK_descuentos_producto_descuentos_config` (`config_id`),
  CONSTRAINT `FK_descuentos_producto_descuentos_config` FOREIGN KEY (`config_id`) REFERENCES `descuentos_config` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_descuentos_producto_productos` FOREIGN KEY (`prd_id`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.descuentos_producto: ~0 rows (aproximadamente)
DELETE FROM `descuentos_producto`;

-- Volcando estructura para tabla rovirosa.detalles_venta
CREATE TABLE IF NOT EXISTS `detalles_venta` (
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
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.detalles_venta: ~13 rows (aproximadamente)
DELETE FROM `detalles_venta`;
INSERT INTO `detalles_venta` (`id`, `vnta_id`, `prd_id`, `cant_in`, `cant_fin`, `precio_unit`, `desc_unit`) VALUES
	(111, 93, 4, 1, NULL, 239.00, 11.95),
	(114, 95, 2, 2, NULL, 17.90, 0.90),
	(115, 95, 7, 1, NULL, 33.00, 0.00),
	(116, 96, 7, 1, NULL, 33.00, 0.00),
	(117, 96, 8, 2, NULL, 34.00, 0.00),
	(118, 96, 5, 1, NULL, 239.00, 11.95);

-- Volcando estructura para tabla rovirosa.dias_laborales
CREATE TABLE IF NOT EXISTS `dias_laborales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pv_id` int NOT NULL COMMENT 'ID del punto de venta',
  `dia_semana` enum('lunes','martes','miercoles','jueves','viernes','sabado','domingo') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_horarios_punto_venta` (`pv_id`),
  CONSTRAINT `FK_horarios_punto_venta` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.dias_laborales: ~14 rows (aproximadamente)
DELETE FROM `dias_laborales`;
INSERT INTO `dias_laborales` (`id`, `pv_id`, `dia_semana`) VALUES
	(1, 1, 'lunes'),
	(2, 1, 'martes'),
	(3, 1, 'miercoles'),
	(4, 1, 'jueves'),
	(5, 1, 'viernes'),
	(6, 1, 'sabado'),
	(8, 6, 'lunes'),
	(9, 6, 'martes'),
	(10, 6, 'miercoles'),
	(11, 6, 'jueves'),
	(12, 6, 'viernes'),
	(13, 6, 'sabado'),
	(28, 1, 'domingo'),
	(29, 6, 'domingo');

-- Volcando estructura para tabla rovirosa.direcciones
CREATE TABLE IF NOT EXISTS `direcciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lat` decimal(10,8) NOT NULL COMMENT 'Latitud de la dirección',
  `lng` decimal(11,8) NOT NULL COMMENT 'Longitud de la dirección',
  `ref` varchar(255) NOT NULL COMMENT 'Referencia de la dirección',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.direcciones: ~26 rows (aproximadamente)
DELETE FROM `direcciones`;
INSERT INTO `direcciones` (`id`, `lat`, `lng`, `ref`) VALUES
	(1, 17.74748539, -92.61013526, ''),
	(2, 17.76364885, -92.59267079, ''),
	(7, 17.76405609, -92.59525008, ''),
	(8, 17.75535681, -92.58530146, ''),
	(9, 17.76455310, -92.59483297, ''),
	(10, 17.76363734, -92.59165994, ''),
	(14, 17.76084206, -92.59733415, ''),
	(15, 17.76446564, -92.59152140, ''),
	(17, 17.75950992, -92.59327040, ''),
	(18, 17.76337798, -92.59217243, ''),
	(19, 17.76255966, -92.59218810, ''),
	(20, 17.76237979, -92.59881699, ''),
	(21, 17.76284780, -92.59082261, ''),
	(22, 17.76451976, -92.59435825, ''),
	(25, 17.76223207, -92.59787038, ''),
	(26, 17.75965531, -92.60268181, ''),
	(27, 17.76523092, -92.59827881, ''),
	(29, 17.76034735, -92.60186323, 'Casa color azul con blanco.'),
	(30, 17.75886835, -92.60434033, 'Casa color azul con blanco.'),
	(31, 17.75822595, -92.60296399, 'asasasasasasasasa'),
	(32, 17.75822570, -92.60319055, 'ertrertererte'),
	(33, 17.75757141, -92.60327433, 'ASASAAS'),
	(34, 17.75843902, -92.60392840, 'ssddsfsdf'),
	(35, 17.75839031, -92.60331586, 'ASASASA'),
	(36, 17.75971507, -92.59709967, 'Mi casa esta cerca de la laguna mata de capilin afuera.'),
	(37, 17.76430707, -92.58864793, '');

-- Volcando estructura para tabla rovirosa.empresa_config
CREATE TABLE IF NOT EXISTS `empresa_config` (
  `rfc` varchar(20) NOT NULL COMMENT 'RFC de la empresa',
  `nombre` varchar(50) NOT NULL COMMENT 'Nombre o razón social de la empresa',
  `logo` varchar(255) NOT NULL COMMENT 'Logo de la empresa',
  `descrip` varchar(255) DEFAULT NULL COMMENT 'Descripción de la empresa',
  `monto_min` int NOT NULL DEFAULT '150' COMMENT 'Monto mínimo para todos los puntos de venta',
  `email_app` varchar(100) DEFAULT NULL COMMENT 'Email de la aplicación',
  `codigo_app` varchar(25) DEFAULT NULL COMMENT 'Código de la aplicación (lrhm upio jpnh tvpv)',
  PRIMARY KEY (`rfc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.empresa_config: ~0 rows (aproximadamente)
DELETE FROM `empresa_config`;
INSERT INTO `empresa_config` (`rfc`, `nombre`, `logo`, `descrip`, `monto_min`, `email_app`, `codigo_app`) VALUES
	('DRO700527V91', 'Corchito', 'f1a4d39d-b08f-4926-9779-c0346069fdef.jpg', 'Ser la mejor empresa concesionaria en la provisión de los productos más vanguardistas y de alta calidad de Grupo Modelo.', 150, 'samuelbj0608@gmail.com', 'xafj attx njcu mgqk');

-- Volcando estructura para tabla rovirosa.estados_envio
CREATE TABLE IF NOT EXISTS `estados_envio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vnta_id` int NOT NULL COMMENT 'ID de la venta',
  `estado` int NOT NULL DEFAULT '1' COMMENT '1 = Pendiente, 2 = En preparación, 3 = En ruta, 4 = Entregado, 5 = Cancelado',
  `fecha` timestamp NOT NULL DEFAULT (now()) COMMENT 'Fecha y hora del cambio de estado',
  PRIMARY KEY (`id`),
  KEY `vnta_id` (`vnta_id`) USING BTREE,
  CONSTRAINT `detalles_envio_ibfk_1` FOREIGN KEY (`vnta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.estados_envio: ~0 rows (aproximadamente)
DELETE FROM `estados_envio`;

-- Volcando estructura para tabla rovirosa.gerentes_pv
CREATE TABLE IF NOT EXISTS `gerentes_pv` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `pv_id` int DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `pv_id` (`pv_id`),
  CONSTRAINT `gerentes_pv_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gerentes_pv_ibfk_2` FOREIGN KEY (`pv_id`) REFERENCES `puntos_venta` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.gerentes_pv: ~2 rows (aproximadamente)
DELETE FROM `gerentes_pv`;
INSERT INTO `gerentes_pv` (`id`, `user_id`, `pv_id`, `activo`) VALUES
	(3, 22, 6, NULL),
	(5, 34, 6, NULL);

-- Volcando estructura para tabla rovirosa.horarios_laborales
CREATE TABLE IF NOT EXISTS `horarios_laborales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia_id` int NOT NULL COMMENT 'ID del punto de venta',
  `h_apertura` time DEFAULT NULL COMMENT 'Hora de inicio de labores',
  `h_cierre` time DEFAULT NULL COMMENT 'Hora de fin de labores',
  PRIMARY KEY (`id`),
  KEY `FK_horarios_dias_laborables` (`dia_id`),
  CONSTRAINT `FK_horarios_dias_laborables` FOREIGN KEY (`dia_id`) REFERENCES `dias_laborales` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.horarios_laborales: ~18 rows (aproximadamente)
DELETE FROM `horarios_laborales`;
INSERT INTO `horarios_laborales` (`id`, `dia_id`, `h_apertura`, `h_cierre`) VALUES
	(1, 1, '08:00:00', '18:00:00'),
	(2, 2, '08:00:00', '18:00:00'),
	(3, 3, '08:00:00', '18:00:00'),
	(4, 4, '08:00:00', '18:00:00'),
	(5, 5, '08:00:00', '18:00:00'),
	(6, 6, '08:00:00', '18:00:00'),
	(8, 8, '07:00:00', '13:00:00'),
	(9, 8, '15:00:00', '19:00:00'),
	(10, 9, '07:00:00', '13:00:00'),
	(11, 9, '15:00:00', '19:00:00'),
	(12, 10, '07:00:00', '13:00:00'),
	(13, 10, '15:00:00', '19:00:00'),
	(14, 11, '07:00:00', '13:00:00'),
	(15, 11, '15:00:00', '19:00:00'),
	(16, 12, '07:00:00', '13:00:00'),
	(17, 12, '15:00:00', '19:00:00'),
	(18, 13, '07:00:00', '13:00:00'),
	(19, 13, '15:00:00', '19:00:00');

-- Volcando estructura para tabla rovirosa.incidencias
CREATE TABLE IF NOT EXISTS `incidencias` (
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

-- Volcando datos para la tabla rovirosa.incidencias: ~0 rows (aproximadamente)
DELETE FROM `incidencias`;

-- Volcando estructura para tabla rovirosa.marcas
CREATE TABLE IF NOT EXISTS `marcas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categ_id` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `logo` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_marcas_categoria` (`categ_id`),
  CONSTRAINT `FK_marcas_categoria` FOREIGN KEY (`categ_id`) REFERENCES `categorias` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.marcas: ~19 rows (aproximadamente)
DELETE FROM `marcas`;
INSERT INTO `marcas` (`id`, `categ_id`, `nombre`, `logo`) VALUES
	(2, 2, 'Modelo', 'marcas/c5820529-6f94-4b08-9ded-7ec4a468fe94.jpg'),
	(3, 2, 'Corona', 'marcas/52ea3fb4-cef3-42a1-b2cd-27e5076d2b4c.jpg'),
	(8, 2, 'Victoria', 'marcas/6ffad478-9305-46a8-8d79-cbfbe5cf9f27.jpg'),
	(9, 2, 'Tecate', 'marcas/d256c0a3-402f-4433-bbb0-655a71f7cb8f.jpg'),
	(10, 3, 'Totopos', 'marcas/160f5e1a-8de4-41d5-848d-79c4f47f007d.jpg'),
	(12, 2, 'Barrilito', 'marcas/47453ba0-fbed-44ab-9c11-2e0c5e4dd4e9.jpg'),
	(13, 4, 'Pepsi', 'marcas/859309c4-cc42-4166-a6c8-8186dc9d0531.jpg'),
	(14, 4, 'Miranda', 'marcas/ea05982f-a001-4007-a75c-4879f0c84aa8.jpg'),
	(15, 4, 'Cocacola', 'marcas/190b4a6a-d83f-4bbf-a274-c5bc168a41a9.jpg'),
	(16, 4, 'Fanta', 'marcas/c07eef48-722c-4125-ad85-08acb01097cb.jpg');

-- Volcando estructura para tabla rovirosa.notificaciones
CREATE TABLE IF NOT EXISTS `notificaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT 'ID del usuario al que se le envía la notificación',
  `titulo` varchar(50) NOT NULL COMMENT 'Título de la notificación',
  `subtitulo` varchar(150) NOT NULL COMMENT 'Subtitulo de la notificación',
  PRIMARY KEY (`id`),
  KEY `FK_notificaciones_usuarios` (`user_id`),
  CONSTRAINT `FK_notificaciones_usuarios` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.notificaciones: ~0 rows (aproximadamente)
DELETE FROM `notificaciones`;

-- Volcando estructura para tabla rovirosa.pagos
CREATE TABLE IF NOT EXISTS `pagos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `metodo` enum('efectivo','transferencia','terminal','link_mp') NOT NULL COMMENT 'Método de pago',
  `monto` decimal(10,2) NOT NULL COMMENT 'Monto total a pagar',
  `paga_con` decimal(10,2) DEFAULT NULL COMMENT 'Monto con el que paga el cliente (solo en efectivo)',
  `fecha` timestamp NULL DEFAULT NULL COMMENT 'Fecha y hora del pago',
  `compr` varchar(100) DEFAULT NULL COMMENT 'Descripción de la compra',
  `estado` enum('pendiente','pagado','rechazado') NOT NULL DEFAULT 'pendiente',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.pagos: ~8 rows (aproximadamente)
DELETE FROM `pagos`;
INSERT INTO `pagos` (`id`, `metodo`, `monto`, `paga_con`, `fecha`, `compr`, `estado`) VALUES
	(98, 'terminal', 227.05, NULL, NULL, NULL, 'pendiente'),
	(100, 'efectivo', 67.01, NULL, NULL, NULL, 'pendiente'),
	(101, 'link_mp', 328.05, NULL, NULL, NULL, 'pendiente');

-- Volcando estructura para tabla rovirosa.personas
CREATE TABLE IF NOT EXISTS `personas` (
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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.personas: ~7 rows (aproximadamente)
DELETE FROM `personas`;
INSERT INTO `personas` (`id`, `curp`, `tel`, `nombre`, `app`, `apm`, `fech_nac`, `sexo`) VALUES
	(17, 'BUJS030806HTCRRM1', '9361165168', 'SAMUEL', 'BURELOS', 'JERONIMO', '2003-08-06', 'MASCULINO'),
	(25, 'MARA030910HTCYYNA6', '9361158941', 'JOSE ANGEL', 'MAY', 'REYES', '2003-09-10', 'MASCULINO'),
	(35, 'MAHJ030812HTCGRSA5', '9361165168', 'JOSUE', 'MAGAÑA', 'HERNANDEZ', '2003-08-12', 'MASCULINO'),
	(36, 'JACF031030HTCVRRA6', '9361335461', 'JOSE FRANCISCO', 'JAVIER', 'DE LA CRUZ', '2003-10-30', 'MASCULINO'),
	(47, 'BUJS030806HTCRRMA9', '9361224565', 'SAMUEL', 'BURELOS', 'JERONIMO', '2003-08-06', 'MASCULINO'),
	(48, 'MHJU070803HTCRRMA7', '9361145615', 'JESUS MIGUEL', 'MARQUEZ', 'MENDEZ', '2003-08-13', 'MASCULINO'),
	(49, 'NDPA040200HTCRRMA9', '9361145651', 'NESTOR', 'CRISTIAN', 'GIMÉNEZ', '2000-02-04', 'MASCULINO');

-- Volcando estructura para tabla rovirosa.productos
CREATE TABLE IF NOT EXISTS `productos` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1763618989 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.productos: ~12 rows (aproximadamente)
DELETE FROM `productos`;
INSERT INTO `productos` (`id`, `marca_id`, `imagen`, `nombre`, `precio`, `peso_kg`, `vol_m3`) VALUES
	(1, 3, 'productos/ff495738-70fe-44e8-9763-d1efcf8131df.jpg', 'Corona Extra 355ml', 25.00, 0.60000000, 0.00065000),
	(2, 3, 'productos/77ba11f2-dffd-4a40-af51-efa1ab58ebc7.jpg', 'Cerveza Corona 473 ml 4 PZS', 17.90, 2.80000000, 0.00250000),
	(4, 3, 'productos/27422288-5bd5-4e26-8c56-a49281bc002f.jpg', 'Cerveza clara Coronita Extra 24 botellas de 210 ml c/u', 239.00, 9.00000000, 0.03000000),
	(5, 8, 'productos/df70d3b4-6bf5-4f97-9b5b-5100fa3a6cc2.jpg', 'Pack de cerveza Victoria ambar con 24 botellas de 210 ml c/u', 239.00, 9.20000000, 0.03100000),
	(6, 12, 'productos/e3719da3-2e48-4e9e-8255-f0f1ec5254bd.jpg', 'Cerveza clara Barrilito 6 botellas de 325 ml c/u', 74.00, 3.90000000, 0.00750000),
	(7, 13, 'productos/81f733ff-9b8c-4556-9c9b-82d4ff88f053.jpg', 'Refresco Pepsi regular 2.5L', 33.00, 2.60000000, 0.00400000),
	(8, 14, 'productos/10bff2cb-7523-4859-90bc-d54c2dbdafb3.jpg', 'Refresco Mirinda sabor naranja botella de 2.5L', 34.00, 2.60000000, 0.00400000),
	(1763617797, 3, 'productos/d8d60726-8b27-43ba-b7c5-e087a57e976b.jpg', 'Corona Light 355ml', 24.00, 0.58000000, 0.00065000),
	(1763617839, 3, 'productos/6c8b7143-c57e-44e9-86a0-884353c7be60.jpg', 'Corona Mega 1.2L', 42.00, 1.40000000, 0.00120000),
	(1763617882, 3, 'productos/452adcf3-eab3-4081-9581-8d1399553c92.jpg', 'Corona Familiar 940ml', 38.00, 1.25000000, 0.00100000),
	(1763618175, 3, 'productos/5d749bc7-453b-4fb6-97f0-8154717532df.jpg', 'Corona Cero 355ml', 26.00, 26.00000000, 0.00065000),
	(1763618211, 2, 'productos/84706ce7-b136-42e8-ab82-a0869b219d65.jpg', 'Modelo Especial 355ml', 28.00, 0.62000000, 0.00066000),
	(1763618295, 2, 'productos/53d1bdc0-4d2f-43f6-b8c7-c0caa0f8959c.jpg', 'Cerveza Especial 6 Pack Nr 355 Ml', 135.00, 3.72000000, 0.00396000),
	(1763618473, 2, 'productos/b77b8d3b-c0b4-4a5e-bd62-4edfd6036d30.jpg', 'Negra Modelo 355ml', 29.00, 0.63000000, 0.00066000),
	(1763618550, 3, 'productos/0085c562-65b9-45d2-a2e9-9adabd06e152.jpg', 'Modelo Ámbar 355ml', 27.00, 0.62000000, 0.00062000),
	(1763618585, 2, 'productos/408acb2e-1f5f-49d1-8136-3920dbbd0492.jpg', 'Modelo Especial Lata 473ml', 23.00, 0.70000000, 0.00070000),
	(1763618790, 2, 'productos/afbc304c-62f4-456f-91e6-486f02431445.jpg', 'Modelo Especial Mega 1.2L', 45.00, 1.45000000, 0.00012000),
	(1763618988, 9, 'productos/8cd0b92a-a2db-41af-9284-adf1a2d1e476.jpg', 'Tecate Roja 355ml', 23.00, 0.58000000, 0.00065000);

-- Volcando estructura para tabla rovirosa.puntos_venta
CREATE TABLE IF NOT EXISTS `puntos_venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre del punto de venta, puede ser la dirección o un nombre comercial',
  `direc_id` int NOT NULL COMMENT 'ID de la dirección',
  `config_rfc` varchar(20) NOT NULL COMMENT 'RFC de la empresa',
  `zona_permitida` text NOT NULL COMMENT 'Zona permitida en formato JSON [{"lat":0,"lng":0},{"lat":0,"lng":0}]',
  `estado` enum('habilitado','deshabilitado') NOT NULL DEFAULT 'habilitado',
  PRIMARY KEY (`id`),
  KEY `FK_puntos_venta_direcciones` (`direc_id`),
  KEY `FK_puntos_venta_empresa_config` (`config_rfc`),
  CONSTRAINT `FK_puntos_venta_direcciones` FOREIGN KEY (`direc_id`) REFERENCES `direcciones` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_puntos_venta_empresa_config` FOREIGN KEY (`config_rfc`) REFERENCES `empresa_config` (`rfc`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.puntos_venta: ~2 rows (aproximadamente)
DELETE FROM `puntos_venta`;
INSERT INTO `puntos_venta` (`id`, `nombre`, `direc_id`, `config_rfc`, `zona_permitida`, `estado`) VALUES
	(1, 'Q92R+2P Macuspana, Tabasco.', 1, 'DRO700527V91', '[{"lat":17.743343415559103,"lng":-92.62033872419875},{"lat":17.747779232366643,"lng":-92.61099444071475},{"lat":17.750141725755075,"lng":-92.607562365531},{"lat":17.751103800667543,"lng":-92.6074515300474},{"lat":17.751498995137162,"lng":-92.60617932124474},{"lat":17.753460781455384,"lng":-92.6051099111159},{"lat":17.754526496403148,"lng":-92.60576708367665},{"lat":17.755420526400712,"lng":-92.60610879185192},{"lat":17.756561246222926,"lng":-92.60511800969064},{"lat":17.758453041790254,"lng":-92.60514122008007},{"lat":17.76159903353849,"lng":-92.60496481426591},{"lat":17.761854955272593,"lng":-92.600560837757},{"lat":17.757973057164328,"lng":-92.59774489924231},{"lat":17.757827022477436,"lng":-92.60020945233256},{"lat":17.75029537552799,"lng":-92.60545111166059},{"lat":17.748167687824296,"lng":-92.60711159963844},{"lat":17.747430662087385,"lng":-92.60767951576042},{"lat":17.747509144821926,"lng":-92.6084727634694},{"lat":17.74594162000117,"lng":-92.61006646265837},{"lat":17.74495172059465,"lng":-92.61209966170144},{"lat":17.742362070584235,"lng":-92.61928018639026},{"lat":17.742049140794354,"lng":-92.62281713444122},{"lat":17.74397580271168,"lng":-92.62329322680739},{"lat":17.746470021961947,"lng":-92.62285705820727},{"lat":17.746557356102514,"lng":-92.6211688431074}]', 'habilitado'),
	(6, 'Centro C. Francisco I. Madero 705.', 15, 'DRO700527V91', '[{"lat":17.765622938687407,"lng":-92.594417862419},{"lat":17.765622938687407,"lng":-92.590417862419},{"lat":17.763489870468206,"lng":-92.5903277297842},{"lat":17.762880745124157,"lng":-92.59163456652813},{"lat":17.757313711150985,"lng":-92.5939519235539},{"lat":17.754984262522072,"lng":-92.60578065914109},{"lat":17.763005959942483,"lng":-92.60435712287239}]', 'habilitado');

-- Volcando estructura para tabla rovirosa.repartidores
CREATE TABLE IF NOT EXISTS `repartidores` (
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.repartidores: ~2 rows (aproximadamente)
DELETE FROM `repartidores`;
INSERT INTO `repartidores` (`id`, `user_id`, `veh_id`, `lat`, `lng`, `estado`) VALUES
	(7, 21, 3, 17.762123, -92.604020, 'en_espera'),
	(8, 33, 1, 17.762093, -92.604013, 'cargando');

-- Volcando estructura para tabla rovirosa.repart_asign
CREATE TABLE IF NOT EXISTS `repart_asign` (
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.repart_asign: ~2 rows (aproximadamente)
DELETE FROM `repart_asign`;
INSERT INTO `repart_asign` (`id`, `rep_id`, `pv_id`, `fecha_inicio`, `fecha_fin`) VALUES
	(22, 7, 1, '2025-11-17', NULL),
	(23, 8, 6, '2025-11-17', NULL);

-- Volcando estructura para tabla rovirosa.rutas
CREATE TABLE IF NOT EXISTS `rutas` (
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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.rutas: ~6 rows (aproximadamente)
DELETE FROM `rutas`;
INSERT INTO `rutas` (`id`, `pv_id`, `rep_id`, `fech_in`, `fech_fin`, `estado`) VALUES
	(50, 6, 8, '2025-11-20 05:31:09', NULL, 'pendiente');

-- Volcando estructura para tabla rovirosa.rutas_detalle
CREATE TABLE IF NOT EXISTS `rutas_detalle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ruta_id` int NOT NULL COMMENT 'ID de la ruta',
  `venta_id` int NOT NULL COMMENT 'ID de la venta',
  `lat` decimal(11,8) NOT NULL COMMENT 'Latitud del punto de entrega',
  `lng` decimal(11,8) NOT NULL COMMENT 'Longitud del punto de entrega',
  `ref` varchar(255) NOT NULL COMMENT 'Referencia del punto de entrega',
  PRIMARY KEY (`id`),
  KEY `ruta_id` (`ruta_id`),
  KEY `venta_id` (`venta_id`),
  CONSTRAINT `ruta_detalle_ibfk_1` FOREIGN KEY (`ruta_id`) REFERENCES `rutas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ruta_detalle_ibfk_2` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.rutas_detalle: ~8 rows (aproximadamente)
DELETE FROM `rutas_detalle`;
INSERT INTO `rutas_detalle` (`id`, `ruta_id`, `venta_id`, `lat`, `lng`, `ref`) VALUES
	(70, 50, 93, 17.75971507, -92.59709967, 'Mi casa esta cerca de la laguna mata de capilin afuera.'),
	(72, 50, 95, 17.75971507, -92.59709967, 'Mi casa esta cerca de la laguna mata de capilin afuera.'),
	(73, 50, 96, 17.75970923, -92.59462284, 'Mi casa esta cerca de la laguna mata de capilin afuera.');

-- Volcando estructura para tabla rovirosa.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.usuarios: ~7 rows (aproximadamente)
DELETE FROM `usuarios`;
INSERT INTO `usuarios` (`id`, `per_id`, `pv_id`, `correo`, `password`, `token_fbm`, `estado`, `created`, `rol`) VALUES
	(9, 17, 6, 'admin@gmail.com', 'admin', 'fF-PWrCEQA-2A9LpjhpKFR:APA91bGDQaAjBWU7N5-hsBrDibnXjs1eiNlUZjCeOqMbVuDwU4V4pjsORz-1hTiRH09TyCPOACgBhgRY5Ju5wi9Z6AlaXPxMP4PDn6_ZAWqXqYDuWL1pAHs', 'activo', '2025-09-27 23:09:49', 'ADMIN'),
	(11, 25, 1, 'cliente@gmail.com', 'numeroPI141592', NULL, 'activo', '2025-10-17 17:31:27', 'CLIENTE'),
	(21, 35, 1, 'repartidor@gmail.com', 'repartidor', NULL, 'activo', '2025-10-29 17:16:50', 'REPARTIDOR'),
	(22, 36, 1, 'gerente@gmail.com', 'gerente', NULL, 'activo', '2025-10-29 17:20:04', 'GERENTE'),
	(32, 47, 6, 'samuelbj0608@gmail.com', 'numeroPI141592', 'dmSTaHKyRiOb-6O5WwZS34:APA91bGx75DP_yMkTcDkR1KzCAWmJa9qtlEik3hY-YeEIxmhU_MV2tCSY_CrTYL193xyWOqO9i3iNRahLKrqcLSEfi9Xofg7pOvg-SzLQoH0yhX5rS0qU_8', 'activo', '2025-11-02 18:52:36', 'CLIENTE'),
	(33, 48, 6, 'repartidor2@gmail.com', 'repartidor', NULL, 'activo', '2025-11-13 15:50:43', 'REPARTIDOR'),
	(34, 49, 6, 'gerente2@gmail.com', 'gerente', NULL, 'activo', '2025-11-19 16:02:02', 'GERENTE');

-- Volcando estructura para tabla rovirosa.vehiculos
CREATE TABLE IF NOT EXISTS `vehiculos` (
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.vehiculos: ~4 rows (aproximadamente)
DELETE FROM `vehiculos`;
INSERT INTO `vehiculos` (`id`, `placa`, `marca`, `modelo`, `activo`, `tipo`, `capacidad_kg`, `volumen_m3`, `factor_uso_max`) VALUES
	(1, 'JDIHDI773', 'Italika', '110 AT', 1, 'moto', 55.00, 0.180, 0.80),
	(3, 'TSK-92A1', 'Italika', 'FT150', 1, 'moto', 45.00, 0.160, 0.80),
	(4, 'RJB-57M9', 'Nissan', 'NP300', 1, 'camioneta', 350.00, 1.250, 0.80),
	(5, 'XMN-44Z8', 'Honda', 'Dio 110', 1, 'moto', 60.00, 0.200, 0.80);

-- Volcando estructura para tabla rovirosa.ventas
CREATE TABLE IF NOT EXISTS `ventas` (
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
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla rovirosa.ventas: ~8 rows (aproximadamente)
DELETE FROM `ventas`;
INSERT INTO `ventas` (`id`, `cliente_id`, `pv_id`, `pago_id`, `fecha_inic`, `updated`, `fecha_fin`, `estado`, `calif`) VALUES
	(93, 9, 6, 98, '2025-11-20 05:31:09', '2025-11-20 05:31:08', NULL, 'Pendiente', NULL),
	(95, 9, 6, 100, '2025-11-20 05:37:43', '2025-11-20 05:37:42', NULL, 'Pendiente', NULL),
	(96, 9, 6, 101, '2025-11-20 05:38:49', '2025-11-20 05:38:49', NULL, 'Pendiente', NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
