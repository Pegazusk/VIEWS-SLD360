-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: localhost    Database: SLD360
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `antecedentes_paciente`
--

DROP TABLE IF EXISTS `antecedentes_paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `antecedentes_paciente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cartilla` int DEFAULT NULL,
  `tipo` enum('Alergia','Discapacidad','Cirugía','Enfermedad','Transfusión','Embarazo','Menstruación') NOT NULL,
  `detalle` text,
  `fecha_registro` date DEFAULT NULL,
  `edad_diagnostico` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cartilla` (`id_cartilla`),
  CONSTRAINT `antecedentes_paciente_ibfk_1` FOREIGN KEY (`id_cartilla`) REFERENCES `cartilla_vacunacion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antecedentes_paciente`
--

LOCK TABLES `antecedentes_paciente` WRITE;
/*!40000 ALTER TABLE `antecedentes_paciente` DISABLE KEYS */;
/*!40000 ALTER TABLE `antecedentes_paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartilla_vacunacion`
--

DROP TABLE IF EXISTS `cartilla_vacunacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartilla_vacunacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `fecha_creacion` date NOT NULL,
  `esquema_completo` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_paciente` (`id_paciente`),
  CONSTRAINT `cartilla_vacunacion_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartilla_vacunacion`
--

LOCK TABLES `cartilla_vacunacion` WRITE;
/*!40000 ALTER TABLE `cartilla_vacunacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartilla_vacunacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_paciente` int NOT NULL,
  `fk_doctor` int NOT NULL,
  `fk_consultorio` int NOT NULL,
  `fk_especialidad` int NOT NULL,
  `fecha_cita` datetime NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_paciente` (`fk_paciente`),
  KEY `fk_doctor` (`fk_doctor`),
  KEY `fk_consultorio` (`fk_consultorio`),
  KEY `fk_especialidad` (`fk_especialidad`),
  CONSTRAINT `cita_ibfk_1` FOREIGN KEY (`fk_paciente`) REFERENCES `paciente` (`id`),
  CONSTRAINT `cita_ibfk_2` FOREIGN KEY (`fk_doctor`) REFERENCES `doctor` (`id`),
  CONSTRAINT `cita_ibfk_3` FOREIGN KEY (`fk_consultorio`) REFERENCES `consultorio` (`id`),
  CONSTRAINT `cita_ibfk_4` FOREIGN KEY (`fk_especialidad`) REFERENCES `especialidad` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (1,1,1,1,3,'2025-06-10 11:50:00',1),(2,1,1,1,3,'2023-05-11 10:50:00',1),(3,1,1,1,3,'2025-05-10 11:50:00',1),(4,3,1,1,3,'2026-12-12 11:50:00',1),(5,3,2,2,10,'2026-09-29 11:57:00',1),(6,5,2,2,10,'2029-09-12 02:33:00',1),(7,2,2,2,10,'2078-01-01 11:11:00',1),(8,1,2,2,10,'2922-08-08 01:01:00',1),(9,3,2,2,10,'2027-01-01 07:07:00',1),(10,1,2,2,10,'2028-12-12 11:11:00',1),(11,6,3,2,12,'2025-04-05 18:30:00',1),(12,5,3,3,12,'2025-04-06 18:16:00',1);
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cita_detalle`
--

DROP TABLE IF EXISTS `cita_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita_detalle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_cita` int NOT NULL,
  `padecimiento` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cita` (`fk_cita`),
  CONSTRAINT `cita_detalle_ibfk_1` FOREIGN KEY (`fk_cita`) REFERENCES `cita` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita_detalle`
--

LOCK TABLES `cita_detalle` WRITE;
/*!40000 ALTER TABLE `cita_detalle` DISABLE KEYS */;
/*!40000 ALTER TABLE `cita_detalle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consultorio`
--

DROP TABLE IF EXISTS `consultorio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultorio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultorio`
--

LOCK TABLES `consultorio` WRITE;
/*!40000 ALTER TABLE `consultorio` DISABLE KEYS */;
INSERT INTO `consultorio` VALUES (1,'101',1),(2,'102',1),(3,'103',1);
/*!40000 ALTER TABLE `consultorio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_persona` int NOT NULL,
  `cedula` text NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,2,'12345687989',1),(2,4,'3464666466',1),(3,7,'97110867',1);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_consultorio`
--

DROP TABLE IF EXISTS `doctor_consultorio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_consultorio` (
  `fk_doctor` int NOT NULL,
  `fk_consultorio` int NOT NULL,
  KEY `fk_doctor` (`fk_doctor`),
  KEY `fk_consultorio` (`fk_consultorio`),
  CONSTRAINT `doctor_consultorio_ibfk_1` FOREIGN KEY (`fk_doctor`) REFERENCES `doctor` (`id`),
  CONSTRAINT `doctor_consultorio_ibfk_2` FOREIGN KEY (`fk_consultorio`) REFERENCES `consultorio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_consultorio`
--

LOCK TABLES `doctor_consultorio` WRITE;
/*!40000 ALTER TABLE `doctor_consultorio` DISABLE KEYS */;
INSERT INTO `doctor_consultorio` VALUES (1,1),(2,2),(3,3),(3,1),(3,2);
/*!40000 ALTER TABLE `doctor_consultorio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_especialidad`
--

DROP TABLE IF EXISTS `doctor_especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_especialidad` (
  `fk_doctor` int NOT NULL,
  `fk_especialidad` int NOT NULL,
  KEY `fk_doctor` (`fk_doctor`),
  KEY `fk_especialidad` (`fk_especialidad`),
  CONSTRAINT `doctor_especialidad_ibfk_1` FOREIGN KEY (`fk_doctor`) REFERENCES `doctor` (`id`),
  CONSTRAINT `doctor_especialidad_ibfk_2` FOREIGN KEY (`fk_especialidad`) REFERENCES `especialidad` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_especialidad`
--

LOCK TABLES `doctor_especialidad` WRITE;
/*!40000 ALTER TABLE `doctor_especialidad` DISABLE KEYS */;
INSERT INTO `doctor_especialidad` VALUES (1,3),(2,10),(3,12),(3,12);
/*!40000 ALTER TABLE `doctor_especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidad` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidad`
--

LOCK TABLES `especialidad` WRITE;
/*!40000 ALTER TABLE `especialidad` DISABLE KEYS */;
INSERT INTO `especialidad` VALUES (1,'Medicina general',1),(2,'Pediatria',1),(3,'Ginecología y obstetricia',1),(4,'Cardiología',1),(5,'Dermatología',1),(6,'Endocrinología',1),(7,'Gastroenterología',1),(8,'Geriatría',1),(9,'Ginecología',1),(10,'Hematología',1),(11,'Infectología',1),(12,'Medicina interna',1),(13,'Nefrología',1),(14,'Neumología',1),(15,'Neurología',1),(16,'Oncología',1),(17,'Oftalmología',1),(18,'Ortopedia',1),(19,'Otorrinolaringología',1),(20,'Pediatría',1),(21,'Psiquiatría',1),(22,'Reumatología',1),(23,'Urología',1);
/*!40000 ALTER TABLE `especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_persona` int NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_persona` (`fk_persona`),
  CONSTRAINT `paciente_ibfk_1` FOREIGN KEY (`fk_persona`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,7,1),(8,8,1);
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente_cat_vacuna`
--

DROP TABLE IF EXISTS `paciente_cat_vacuna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente_cat_vacuna` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente_cat_vacuna`
--

LOCK TABLES `paciente_cat_vacuna` WRITE;
/*!40000 ALTER TABLE `paciente_cat_vacuna` DISABLE KEYS */;
INSERT INTO `paciente_cat_vacuna` VALUES (1,'Hepatitis B',1),(2,'Tétanos y difteria',1),(3,'Tétanos, Difteria y tosferina',1),(4,'Neumonía por virus de la influenza A y B',1),(5,'Sarampión y Rubéola',1),(6,'Infección por el virus del papiloma humano',1),(7,'Formas graves de la COVID-19',1);
/*!40000 ALTER TABLE `paciente_cat_vacuna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente_empleo`
--

DROP TABLE IF EXISTS `paciente_empleo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente_empleo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_paciente` int NOT NULL,
  `empresa` varchar(50) DEFAULT NULL,
  `ocupacion` varchar(100) DEFAULT NULL,
  `sector` varchar(100) DEFAULT NULL,
  `riesgos` varchar(100) DEFAULT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_paciente` (`fk_paciente`),
  CONSTRAINT `paciente_empleo_ibfk_1` FOREIGN KEY (`fk_paciente`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente_empleo`
--

LOCK TABLES `paciente_empleo` WRITE;
/*!40000 ALTER TABLE `paciente_empleo` DISABLE KEYS */;
INSERT INTO `paciente_empleo` VALUES (1,1,'IMSS','Enfermero','Salud','Contagio de enfermedades ',1),(2,2,NULL,NULL,NULL,NULL,1),(3,3,'kjkj','hkjkjjk','gkjgkjgk','gkjj',1),(4,4,NULL,NULL,NULL,NULL,1),(5,5,'utl','profe','gobierno','ninguino',1),(6,6,'Instituto Mexicano del Seguro Social','Enfermero','Publico','Infecciones cruzadas',1),(7,7,NULL,NULL,NULL,NULL,1),(8,8,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `paciente_empleo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente_salud`
--

DROP TABLE IF EXISTS `paciente_salud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente_salud` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_paciente` int NOT NULL,
  `is_diabetes` tinyint(1) DEFAULT '0',
  `is_hipertension` tinyint(1) DEFAULT '0',
  `is_cancer` tinyint(1) DEFAULT '0',
  `is_embarazo` tinyint(1) DEFAULT '0',
  `fecha_embarazo` date DEFAULT NULL,
  `cantidad_embarazo` int DEFAULT '0',
  `cantidad_abortos` int DEFAULT '0',
  `cantidad_cesareas` int DEFAULT '0',
  `cantidad_hijos` int DEFAULT '0',
  `is_anticonceptivo` int DEFAULT '0',
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_paciente` (`fk_paciente`),
  CONSTRAINT `paciente_salud_ibfk_1` FOREIGN KEY (`fk_paciente`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente_salud`
--

LOCK TABLES `paciente_salud` WRITE;
/*!40000 ALTER TABLE `paciente_salud` DISABLE KEYS */;
INSERT INTO `paciente_salud` VALUES (1,1,0,0,0,0,NULL,0,0,0,0,0,1),(2,2,0,0,0,0,NULL,0,0,0,0,0,1),(3,3,1,1,1,1,NULL,0,0,0,0,0,1),(4,4,0,0,0,0,NULL,0,0,0,0,0,1),(5,5,1,1,0,1,NULL,0,0,0,0,1,1),(6,6,0,0,0,0,NULL,0,0,0,0,0,1),(7,7,0,0,0,0,NULL,0,0,0,0,0,1),(8,8,0,0,0,0,NULL,0,0,0,0,0,1);
/*!40000 ALTER TABLE `paciente_salud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente_vacuna`
--

DROP TABLE IF EXISTS `paciente_vacuna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente_vacuna` (
  `fk_paciente` int DEFAULT NULL,
  `fk_vacuna` int DEFAULT NULL,
  `fecha` date NOT NULL,
  `lote` text NOT NULL,
  `estatus` tinyint(1) DEFAULT NULL,
  KEY `fk_paciente` (`fk_paciente`),
  KEY `fk_vacuna` (`fk_vacuna`),
  CONSTRAINT `paciente_vacuna_ibfk_1` FOREIGN KEY (`fk_paciente`) REFERENCES `paciente` (`id`),
  CONSTRAINT `paciente_vacuna_ibfk_2` FOREIGN KEY (`fk_vacuna`) REFERENCES `paciente_cat_vacuna` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente_vacuna`
--

LOCK TABLES `paciente_vacuna` WRITE;
/*!40000 ALTER TABLE `paciente_vacuna` DISABLE KEYS */;
INSERT INTO `paciente_vacuna` VALUES (1,1,'2025-04-03','01',1),(6,1,'2025-04-05','22000955',1),(6,2,'2025-04-05','22000966',1),(6,1,'2020-12-12','97110867',1),(6,5,'1900-01-03','GATONEGRO',1),(5,1,'2025-01-01','97',1);
/*!40000 ALTER TABLE `paciente_vacuna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_usuario` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido_uno` varchar(100) NOT NULL,
  `apellido_dos` varchar(100) DEFAULT NULL,
  `curp` varchar(18) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `fk_estado_civil` int DEFAULT NULL,
  `fk_escolaridad` int DEFAULT NULL,
  `fk_tiposangre` int NOT NULL DEFAULT '1',
  `sexo` char(1) DEFAULT NULL,
  `correo` text,
  `telefono` bigint DEFAULT NULL,
  `esIndigena` int NOT NULL DEFAULT '0',
  `calleynumero` text,
  `colonia` text,
  `municipio` text,
  `postal` varchar(5) DEFAULT NULL,
  `entidad` text,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_usuario` (`fk_usuario`),
  KEY `fk_estado_civil` (`fk_estado_civil`),
  KEY `fk_escolaridad` (`fk_escolaridad`),
  KEY `fk_tiposangre` (`fk_tiposangre`),
  CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`fk_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `persona_ibfk_2` FOREIGN KEY (`fk_estado_civil`) REFERENCES `persona_cat_estado_civil` (`id`),
  CONSTRAINT `persona_ibfk_3` FOREIGN KEY (`fk_escolaridad`) REFERENCES `persona_cat_escolaridad` (`id`),
  CONSTRAINT `persona_ibfk_4` FOREIGN KEY (`fk_tiposangre`) REFERENCES `persona_cat_tipo_sangre` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (1,1,'Alvaro Ivan','Gomez ','Perez','GOPA970728HGTMRL02','1997-07-28',2,10,1,'H','alvaroivan1@icloud.com',8126132158,0,NULL,NULL,NULL,NULL,NULL,1),(2,2,'Humberto','Chavez','Castro',NULL,NULL,NULL,NULL,1,NULL,'humbertoutl@utleon.edu.mx',NULL,0,NULL,NULL,NULL,NULL,NULL,1),(3,3,'Adair','Ramirez','Elias',NULL,NULL,NULL,NULL,1,NULL,'adairutl@utleon.edu.mx',NULL,0,NULL,NULL,NULL,NULL,NULL,1),(4,4,'Diego','Perez','Cisneros',NULL,NULL,NULL,NULL,1,NULL,'diegoutl@utleon.edu.mx',NULL,0,NULL,NULL,NULL,NULL,NULL,1),(5,5,'Rafa','Angulo','Rocha','nghggmgmgmngmhb','1997-02-20',2,14,1,'H','gjgjgj@hgjjj.com',777777777,0,NULL,NULL,NULL,NULL,NULL,1),(6,6,'Alvaro Ivan','Gomez ','Perez','GOPA970728HGTMRL02','1997-07-28',2,10,1,'H','alvaroivan1@icloud.com',8126132158,0,'Cto. Villa cecilia 148','Fraccionamiento Candora','Leon','37668','Guanajuato',1),(7,7,'Diego Emanuel','Perez','Cisneros',NULL,NULL,NULL,NULL,1,NULL,'diegoutl@utleon.edu.mx',NULL,0,NULL,NULL,NULL,NULL,NULL,1),(8,8,'','','',NULL,NULL,NULL,NULL,1,NULL,'',NULL,0,NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona_cat_escolaridad`
--

DROP TABLE IF EXISTS `persona_cat_escolaridad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona_cat_escolaridad` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona_cat_escolaridad`
--

LOCK TABLES `persona_cat_escolaridad` WRITE;
/*!40000 ALTER TABLE `persona_cat_escolaridad` DISABLE KEYS */;
INSERT INTO `persona_cat_escolaridad` VALUES (1,'Sin escolaridad',1),(2,'Primaria incompleta',1),(3,'Primaria completa',1),(4,'Secundaria incompleta',1),(5,'Secundaria completa',1),(6,'Preparatoria/Bachillerato incompleto',1),(7,'Preparatoria/Bachillerato completo',1),(8,'Técnico superior universitario',1),(9,'Licenciatura incompleta',1),(10,'Licenciatura completa',1),(11,'Maestría incompleta',1),(12,'Maestría completa',1),(13,'Doctorado incompleto',1),(14,'Doctorado completo',1);
/*!40000 ALTER TABLE `persona_cat_escolaridad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona_cat_estado_civil`
--

DROP TABLE IF EXISTS `persona_cat_estado_civil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona_cat_estado_civil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona_cat_estado_civil`
--

LOCK TABLES `persona_cat_estado_civil` WRITE;
/*!40000 ALTER TABLE `persona_cat_estado_civil` DISABLE KEYS */;
INSERT INTO `persona_cat_estado_civil` VALUES (1,'Soltero',1),(2,'Casado',1),(3,'Divorciado',1),(4,'Unión Libre',1),(5,'Viudo',1);
/*!40000 ALTER TABLE `persona_cat_estado_civil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona_cat_tipo_sangre`
--

DROP TABLE IF EXISTS `persona_cat_tipo_sangre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona_cat_tipo_sangre` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona_cat_tipo_sangre`
--

LOCK TABLES `persona_cat_tipo_sangre` WRITE;
/*!40000 ALTER TABLE `persona_cat_tipo_sangre` DISABLE KEYS */;
INSERT INTO `persona_cat_tipo_sangre` VALUES (1,'A+',1),(2,'A-',1),(3,'B+',1),(4,'B-',1),(5,'AB+',1),(6,'AB-',1),(7,'O+',1),(8,'O-',1);
/*!40000 ALTER TABLE `persona_cat_tipo_sangre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receta`
--

DROP TABLE IF EXISTS `receta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_paciente` int NOT NULL,
  `fk_doctor` int NOT NULL,
  `fecha` date NOT NULL,
  `diagnostico` text NOT NULL,
  `indicaciones` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_paciente` (`fk_paciente`),
  KEY `fk_doctor` (`fk_doctor`),
  CONSTRAINT `receta_ibfk_1` FOREIGN KEY (`fk_paciente`) REFERENCES `paciente` (`id`),
  CONSTRAINT `receta_ibfk_2` FOREIGN KEY (`fk_doctor`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receta`
--

LOCK TABLES `receta` WRITE;
/*!40000 ALTER TABLE `receta` DISABLE KEYS */;
INSERT INTO `receta` VALUES (1,1,2,'2025-04-02','TRAUMATISMO CRANEOENCEFALICO SEVERO','NO HACER DEPORTE','2025-04-02 00:27:22'),(2,1,2,'2025-04-02','LLA','','2025-04-02 00:39:02'),(3,1,2,'2025-04-02','APENDISITIS','NO TOMAR ALCOHOL','2025-04-02 00:53:17'),(4,2,2,'2025-04-02','ESCLEROSIS MULTIPLE BILATERAL','TOMAR TODOS LOS MEDICAMENTOS EN TIEMPO Y FORMA','2025-04-02 01:03:27'),(5,5,2,'2025-04-02','GGJHHJJH','','2025-04-02 01:39:48'),(6,1,2,'2025-04-02','GATROENTERISTIS ULCERATIVA','-TOMARLO POR LAS MAÑANAS, 30 MINUTOS DESPUÉS DE DESPERTAR.','2025-04-02 01:51:46'),(7,1,2,'2025-04-02','BJKJBJK','','2025-04-02 01:53:49'),(8,1,2,'2025-04-02','PANCREATITIS','-AYUNO TRANSITORIO.\r\n-REPOSO ABSOLUTO.','2025-04-02 01:59:01'),(9,6,3,'2025-04-04','Pancreatitis','-No hacer esfuerzo fisico.\r\n-Reposo relativo.\r\n-Tomar los medicamentos por las mañanas','2025-04-04 05:34:38'),(10,5,3,'2025-04-05','HOLA MUNDO','','2025-04-05 00:17:05'),(11,5,3,'2025-04-05','HKGJGJY','','2025-04-05 00:17:54'),(12,3,3,'2025-04-07','BJKJBJK','','2025-04-07 15:42:11');
/*!40000 ALTER TABLE `receta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receta_medicamento`
--

DROP TABLE IF EXISTS `receta_medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta_medicamento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_receta` int NOT NULL,
  `medicamento` varchar(100) NOT NULL,
  `presentacion` varchar(50) DEFAULT NULL,
  `dosis` varchar(50) DEFAULT NULL,
  `frecuencia` varchar(50) DEFAULT NULL,
  `duracion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_receta` (`fk_receta`),
  CONSTRAINT `receta_medicamento_ibfk_1` FOREIGN KEY (`fk_receta`) REFERENCES `receta` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receta_medicamento`
--

LOCK TABLES `receta_medicamento` WRITE;
/*!40000 ALTER TABLE `receta_medicamento` DISABLE KEYS */;
INSERT INTO `receta_medicamento` VALUES (1,1,'TRAMADOL','TABLETAS','500MG','1 CADA 24','8 DIAS'),(2,2,'DEXAMETASONA','INYECTABLE','1 GRAMO','CADA 12 HORAS','POR 7 DIAS'),(3,3,'PARACETAMOL','ORAL','500MG','CADA 6 HORAS','10 DIAS'),(4,4,'INMUNOGLOBULINA HUMANA','INYECTABLE','2 GRAMOS','CADA 72 HORAS','6 MESES'),(5,4,'FORXYGA','TABLETAS','250MG/125MCG','CADA 24 HORAS','30 DIAS'),(6,4,'DAPAGLIFOSINA','TABLETAS','490MG','CADA 8 HORAS','10 DIAS'),(7,5,'GHJGHJGH','JGJGJK','GKJGKJGKJGKJ','GKJGKGJKGJG','KJGJKK'),(8,6,'PANTOPRAZOL','VIA ORAL','40 MG','CADA 24 HORAS','30 DIAS'),(9,7,'KJKJHKJ','HKJHKJHKJH','LKHJLKJLKJ','LKJLKJLJLK','JKLJKKL'),(10,8,'CEFTRIAXONA','INTRAVENOSO','2 GRAMOS','CADA 24 HORAS','7 DIAS'),(11,9,'Levofloxacino','Oral','500mg','1 tableta cada 8 horas','7 dias'),(12,9,'Paracetamol','Oral','500mg','1 tableta cada 6 horas','5 dias'),(13,9,'Metronidazol','Oral','250mg','1 tableta cada 24 horas','30 dias'),(14,10,'JAVA','REST','MYSQL','DIARIO','SIEMPRE'),(15,11,'PARACETAMOL','ORAL','500MG','1 C8 ','6 DIAS'),(16,12,'wqe','qweqweqe','weqwe','qweqwe','qwewqe');
/*!40000 ALTER TABLE `receta_medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_rol` int NOT NULL,
  `nick` varchar(45) NOT NULL,
  `clave` varchar(100) NOT NULL,
  `estatus` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_rol` (`fk_rol`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`fk_rol`) REFERENCES `usuario_cat_rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,'alvaroivan1@icloud.com','e10adc3949ba59abbe56e057f20f883e',1),(2,2,'humbertoutl@utleon.edu.mx','e10adc3949ba59abbe56e057f20f883e',1),(3,1,'adairutl@utleon.edu.mx','e10adc3949ba59abbe56e057f20f883e',1),(4,2,'diegoutl@utleon.edu.mx','e10adc3949ba59abbe56e057f20f883e',1),(5,1,'gjgjgj@hgjjj.com','e10adc3949ba59abbe56e057f20f883e',1),(6,1,'alvaroivan1@icloud.com','03b61dcf80edc8cd288e1072f930e772',1),(7,2,'diegoutl@utleon.edu.mx','827ccb0eea8a706c4c34a16891f84e7b',1),(8,1,'','d41d8cd98f00b204e9800998ecf8427e',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_cat_rol`
--

DROP TABLE IF EXISTS `usuario_cat_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_cat_rol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_cat_rol`
--

LOCK TABLES `usuario_cat_rol` WRITE;
/*!40000 ALTER TABLE `usuario_cat_rol` DISABLE KEYS */;
INSERT INTO `usuario_cat_rol` VALUES (1,'Paciente'),(2,'Médico'),(3,'Administrador');
/*!40000 ALTER TABLE `usuario_cat_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacunas_aplicadas`
--

DROP TABLE IF EXISTS `vacunas_aplicadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacunas_aplicadas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cartilla` int DEFAULT NULL,
  `nombre_vacuna` varchar(100) NOT NULL,
  `fecha_aplicacion` date NOT NULL,
  `dosis` int NOT NULL,
  `lote` varchar(50) DEFAULT NULL,
  `aplicador` varchar(100) DEFAULT NULL,
  `edad_aplicacion` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cartilla` (`id_cartilla`),
  CONSTRAINT `vacunas_aplicadas_ibfk_1` FOREIGN KEY (`id_cartilla`) REFERENCES `cartilla_vacunacion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacunas_aplicadas`
--

LOCK TABLES `vacunas_aplicadas` WRITE;
/*!40000 ALTER TABLE `vacunas_aplicadas` DISABLE KEYS */;
/*!40000 ALTER TABLE `vacunas_aplicadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'SLD360'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_doctores_damePacientes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_doctores_damePacientes`(IN in_idUsuario INT)
BEGIN
    DECLARE v_idPersona INT;
    DECLARE v_idDoctor INT;

    SELECT id INTO v_idPersona FROM persona WHERE fk_usuario = in_idUsuario;
    SELECT id INTO v_idDoctor FROM doctor WHERE fk_persona = v_idPersona;

    SELECT
        cita.fk_paciente AS idPaciente,
        CONCAT_WS(' ', persona.nombre, persona.apellido_uno, persona.apellido_dos) AS nombrePaciente,
        TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE()) AS edad,
        fecha_nacimiento AS nacimiento
    FROM cita
        INNER JOIN paciente ON cita.fk_paciente = paciente.id
        INNER JOIN persona ON paciente.fk_persona = persona.id
    WHERE cita.fk_doctor = v_idDoctor AND cita.estatus = 1
    GROUP BY cita.fk_paciente, persona.nombre, persona.apellido_uno, persona.apellido_dos, fecha_nacimiento;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_doctores_generales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_doctores_generales`(IN in_idUsuario INT)
BEGIN
    DECLARE v_idPersona INT;
    DECLARE v_idDoctor INT;
    DECLARE v_nombrePersona TEXT;
    DECLARE v_citasDia INT;
    DECLARE v_pacientes INT;

    SELECT id INTO v_idPersona FROM persona WHERE fk_usuario = in_idUsuario;

    SELECT
        id,
        CONCAT_WS(' ', nombre, apellido_uno, apellido_dos)
    INTO
        v_idPersona,
        v_nombrePersona
    FROM persona WHERE fk_usuario = in_idUsuario;

    SELECT id INTO v_idDoctor FROM doctor WHERE fk_persona = v_idPersona;

    SELECT COUNT(*) INTO v_citasDia FROM cita WHERE fk_doctor = v_idDoctor AND DATE(fecha_cita) = CURRENT_DATE AND estatus = 1;
    SELECT COUNT(DISTINCT fk_paciente) INTO v_pacientes FROM cita WHERE fk_doctor = v_idDoctor AND estatus = 1;

    SELECT v_nombrePersona as nombreDoctor, v_citasDia as totalCitasHoy, v_pacientes as totalPacientes;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_doctor_citas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_doctor_citas`(IN in_idUsuario INT)
BEGIN
    DECLARE v_idPersona INT;
    DECLARE v_idDoctor INT;

    SELECT id INTO v_idPersona FROM persona WHERE fk_usuario = in_idUsuario;
    SELECT id INTO v_idDoctor FROM doctor WHERE fk_persona = v_idPersona;

    SELECT
        CONCAT_WS(' ', p.nombre, p.apellido_uno, p.apellido_dos) as nombrePaciente,
        e.nombre as especialidad,
        c.fecha_cita as fecha,
        co.nombre as consultorio
    FROM cita c
        INNER JOIN paciente pa ON c.fk_paciente = pa.id
        INNER JOIN persona p ON pa.fk_persona = p.id
        INNER JOIN consultorio co ON c.fk_consultorio = co.id
        INNER JOIN especialidad e ON c.fk_especialidad = e.id
    WHERE c.fk_doctor = v_idDoctor AND DATE(fecha_cita) >= CURRENT_DATE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_doctor_registraCita` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_doctor_registraCita`(
    IN in_idPaciente INT,
    IN in_idEspecialidad INT,
    IN in_idUsuario INT,
    IN in_fecha DATETIME,
    IN in_idConsultorio INT
)
BEGIN
    DECLARE v_idPersona INT;
    DECLARE v_idDoctor INT;

    SELECT id INTO v_idPersona FROM persona WHERE fk_usuario = in_idUsuario;
    SELECT id INTO v_idDoctor FROM doctor WHERE fk_persona = v_idPersona;

    INSERT INTO cita VALUES (NULL, in_idPaciente, v_idDoctor, in_idConsultorio, in_idEspecialidad, in_fecha, 1);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_pacientes_citas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_pacientes_citas`(IN in_idUsuario INT)
BEGIN
    DECLARE v_idPersona INT;
    DECLARE v_idPaciente INT;

    SELECT id INTO v_idPersona FROM persona WHERE fk_usuario = in_idUsuario;
    SELECT id INTO v_idPaciente FROM paciente WHERE fk_persona = v_idPersona;

    SELECT
        CONCAT_WS(' ', p.nombre, p.apellido_uno, p.apellido_dos) as nombreDoctor,
        e.nombre as especialidad,
        c.fecha_cita as fecha,
        co.nombre as consultorio
    FROM cita c
        INNER JOIN doctor d ON c.fk_doctor = d.id
        INNER JOIN persona p ON d.fk_persona = p.id
        INNER JOIN consultorio co ON c.fk_consultorio = co.id
        INNER JOIN especialidad e ON c.fk_especialidad = e.id
    WHERE c.fk_paciente = v_idPaciente;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_pacientes_generales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_pacientes_generales`(IN in_idUsuario INT)
BEGIN
    DECLARE v_idPersona INT;
    DECLARE v_idPaciente INT;
    DECLARE v_nombrePersona TEXT;
    DECLARE v_curpPersona TEXT;
    DECLARE v_nacimientoPersona DATE;
    DECLARE v_esIndigenaPersona INT;
    DECLARE v_tipoSanguineoPersona INT;
    DECLARE v_tipoSanguineoTxtPersona VARCHAR(10);
    DECLARE v_estadoCivilPersona INT;
    DECLARE v_escolaridadPersna INT;
    DECLARE v_sexoPersona CHAR(1);
    DECLARE v_telefonoPersona BIGINT;

    DECLARE v_calleynumero TEXT;
    DECLARE v_colonia TEXT;
    DECLARE v_municipio TEXT;
    DECLARE v_postal VARCHAR(5);
    DECLARE v_entidadfederativa TEXT;

    DECLARE v_empresaPersona TEXT;
    DECLARE v_ocupacionPersona TEXT;
    DECLARE v_sectorPersona TEXT;
    DECLARE v_riesgosPersona TEXT;

    DECLARE v_diabetesPersona INT;
    DECLARE v_hipertensionPersona INT;
    DECLARE v_cancerPersona INT;
    DECLARE v_embarazoPersona INT;
    DECLARE v_anticonceptivoPersona INT;
    DECLARE v_totalCitas INT;
    DECLARE v_proximasCitas INT;

    SELECT id INTO v_idPersona FROM persona WHERE fk_usuario = in_idUsuario;

    SELECT
        id,
        CONCAT_WS(' ', nombre, apellido_uno, apellido_dos),
        curp,
        fecha_nacimiento,
        fk_estado_civil,
        fk_escolaridad,
        sexo,
        telefono,
        esIndigena,
        fk_tiposangre,
        calleynumero,
        colonia,
        municipio,
        postal,
        entidad
    INTO
        v_idPersona,
        v_nombrePersona,
        v_curpPersona,
        v_nacimientoPersona,
        v_estadoCivilPersona,
        v_escolaridadPersna,
        v_sexoPersona,
        v_telefonoPersona,
        v_esIndigenaPersona,
        v_tipoSanguineoPersona,
        v_calleynumero,
        v_colonia,
        v_municipio,
        v_postal,
        v_entidadfederativa
    FROM persona WHERE fk_usuario = in_idUsuario;

    SELECT nombre INTO v_tipoSanguineoTxtPersona FROM persona_cat_tipo_sangre WHERE id = v_tipoSanguineoPersona;

    SELECT id INTO v_idPaciente FROM paciente WHERE fk_persona = v_idPersona;

    SELECT empresa, ocupacion, sector, riesgos
    INTO v_empresaPersona, v_ocupacionPersona, v_sectorPersona, v_riesgosPersona
    FROM paciente_empleo WHERE fk_paciente = v_idPaciente;

    SELECT is_diabetes, is_hipertension, is_cancer, is_embarazo, is_anticonceptivo
    INTO v_diabetesPersona, v_hipertensionPersona, v_cancerPersona, v_embarazoPersona, v_anticonceptivoPersona
    FROM paciente_salud WHERE fk_paciente = v_idPaciente;

    SELECT COUNT(*) INTO v_totalCitas FROM cita WHERE fk_paciente = v_idPaciente AND estatus = 1;
    SELECT COUNT(*) INTO v_proximasCitas FROM cita WHERE fk_paciente = v_idPaciente AND estatus = 1 AND fecha_cita > NOW();

    SELECT
        v_nombrePersona as nombrePersona,
        v_curpPersona as curpPersona,
        v_nacimientoPersona as nacimientoPersona,
        v_estadoCivilPersona as estadoCivilPersona,
        v_escolaridadPersna as escolaridadPersona,
        v_sexoPersona as sexoPersona,
        v_telefonoPersona as telefonoPersona,
        v_esIndigenaPersona as esIndigenaPersona,
        v_tipoSanguineoPersona as tipoSanguineoPersona,
        v_tipoSanguineoTxtPersona as tipoSanguineoTxtPersona,
        v_calleynumero as calleYNumeroPersona,
        v_colonia as coloniaPersona,
        v_municipio as municipioPersona,
        v_postal as postalPersona,
        v_entidadfederativa as entidadFederativaPersona,
        v_empresaPersona as empresaPersona,
        v_ocupacionPersona as ocupacionPersona,
        v_sectorPersona as sectorPersona,
        v_riesgosPersona as riesgosPersona,
        v_diabetesPersona as diabetesPersona,
        v_hipertensionPersona as hipertensionPersona,
        v_cancerPersona as cancerPersona,
        v_embarazoPersona as embarazoPersona,
        v_anticonceptivoPersona as anticonceptivoPersona,
        v_proximasCitas as citasProximas,
        v_totalCitas as citasTotales;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_usuarios_registro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_usuarios_registro`(
    IN in_rol INT,
    IN in_nombre VARCHAR(100),
    IN in_apellidouno VARCHAR(100),
    IN in_apellidodos VARCHAR(100),
    IN in_correo TEXT,
    IN in_clave VARCHAR(100)
)
BEGIN
    DECLARE v_idUsuario INT;
    DECLARE v_idPersona INT;
    DECLARE v_idPaciente INT;

    INSERT INTO usuario VALUES (NULL, in_rol, in_correo, in_clave, 1);
    SET v_idUsuario = LAST_INSERT_ID();

    INSERT INTO persona (fk_usuario, nombre, apellido_uno, apellido_dos, correo)
    VALUES (v_idUsuario, in_nombre, in_apellidouno, in_apellidodos, in_correo);
    SET v_idPersona = LAST_INSERT_ID();

    IF in_rol = 1 THEN
        INSERT INTO paciente VALUES (NULL, v_idPersona, 1);
        SET v_idPaciente = LAST_INSERT_ID();

        INSERT INTO paciente_empleo VALUES (NULL, v_idPaciente, NULL, NULL, NULL, NULL, 1);
        INSERT INTO paciente_salud VALUES (NULL, v_idPaciente, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 1);
    END IF;

    IF in_rol = 2 THEN
        INSERT INTO doctor VALUES (NULL, v_idPersona, '', 1);
    END IF;

    SELECT v_idUsuario as idUsuario, v_idPersona as idPersona;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-07  9:43:45
