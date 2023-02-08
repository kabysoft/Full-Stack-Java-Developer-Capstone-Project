/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.22 : Database - onlineehealthcare
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`onlineehealthcare` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `onlineehealthcare`;

/*Table structure for table `ems_medicine` */

DROP TABLE IF EXISTS `ems_medicine`;

CREATE TABLE `ems_medicine` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_datetime` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_datetime` datetime(6) DEFAULT NULL,
  `company_name` varchar(225) DEFAULT NULL,
  `expire_date` date DEFAULT NULL,
  `name` varchar(225) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ems_medicine` */

insert  into `ems_medicine`(`id`,`created_by`,`created_datetime`,`modified_by`,`modified_datetime`,`company_name`,`expire_date`,`name`,`price`,`quantity`) values 
(1,'Admin','2022-01-26 11:08:23.612000','Admin','2022-01-26 11:08:23.612000','Burt and Koch Associates','2022-10-10','Joan Blake',526.00,394),
(2,'Admin','2022-01-29 08:49:25.284000','Admin','2022-01-29 08:49:25.284000','Avila and Bennett Trading','2022-10-10','Ramona Oneill',689.00,575);

/*Table structure for table `ems_user` */

DROP TABLE IF EXISTS `ems_user`;

CREATE TABLE `ems_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_datetime` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_datetime` datetime(6) DEFAULT NULL,
  `address` varchar(755) DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `login` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `phone_no` varchar(10) DEFAULT NULL,
  `role_name` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ems_user` */

insert  into `ems_user`(`id`,`created_by`,`created_datetime`,`modified_by`,`modified_datetime`,`address`,`email`,`gender`,`login`,`name`,`password`,`phone_no`,`role_name`) values 
(1,NULL,NULL,NULL,NULL,'Explicabo Labore te','wovabyp@mailinator.com','MALE','Admin','Rebecca Small','Pa$$w0rd!','8545253565','admin'),
(2,'root','2022-01-26 11:42:27.708000','root','2022-01-26 11:42:27.708000','Ullamco facilis est ','kyciwyni@mailinator.com','FEMALE','Omnis','Brynne Parrish','Pa$$w0rd!','8545253565','user');

/*Table structure for table `evs_cart` */

DROP TABLE IF EXISTS `evs_cart`;

CREATE TABLE `evs_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_datetime` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_datetime` datetime(6) DEFAULT NULL,
  `final_price` decimal(19,2) DEFAULT NULL,
  `medicine_id` bigint DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `medicine` bigint NOT NULL,
  `user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpn0g3o3v666hkown0xf3i4oho` (`medicine`),
  KEY `FK5ehvoiovtpobd01dpm9tspop1` (`user`),
  CONSTRAINT `FK5ehvoiovtpobd01dpm9tspop1` FOREIGN KEY (`user`) REFERENCES `ems_user` (`id`),
  CONSTRAINT `FKpn0g3o3v666hkown0xf3i4oho` FOREIGN KEY (`medicine`) REFERENCES `ems_medicine` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `evs_cart` */

/*Table structure for table `hms_user_orders` */

DROP TABLE IF EXISTS `hms_user_orders`;

CREATE TABLE `hms_user_orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_datetime` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_datetime` datetime(6) DEFAULT NULL,
  `address1` varchar(225) DEFAULT NULL,
  `address2` varchar(225) DEFAULT NULL,
  `city` varchar(225) DEFAULT NULL,
  `email` varchar(225) DEFAULT NULL,
  `name` varchar(225) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `mobile_no` varchar(225) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `state` varchar(225) DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `medicine` bigint NOT NULL,
  `month` varchar(255) DEFAULT NULL,
  `year` varchar(255) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjrnn2xlpmdjqx8jdyre82vd3j` (`medicine`),
  CONSTRAINT `FKjrnn2xlpmdjqx8jdyre82vd3j` FOREIGN KEY (`medicine`) REFERENCES `ems_medicine` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `hms_user_orders` */

insert  into `hms_user_orders`(`id`,`created_by`,`created_datetime`,`modified_by`,`modified_datetime`,`address1`,`address2`,`city`,`email`,`name`,`order_id`,`mobile_no`,`quantity`,`state`,`total`,`user_id`,`medicine`,`month`,`year`,`order_date`) values 
(11,'Omnis','2022-01-29 12:33:52.225000','Omnis','2022-01-29 12:33:52.225000','frj','rfj','rj','kyciwyni@mailinator.com','Brynne Parrish',5511,'8545253565',1,'yrfj',689.00,2,2,'JANUARY','2022',NULL),
(12,'Omnis','2022-01-29 12:33:52.282000','Omnis','2022-01-29 12:33:52.282000','frj','rfj','rj','kyciwyni@mailinator.com','Brynne Parrish',1690,'8545253565',5,'yrfj',2630.00,2,1,'JANUARY','2022',NULL),
(13,'Omnis','2022-01-29 12:36:56.953000','Omnis','2022-01-29 12:36:56.953000','dfb','df','dfbn','kyciwyni@mailinator.com','Brynne Parrish',4816,'8545253565',1,'dfbn',526.00,2,1,'JANUARY','2022','2022-01-29'),
(14,'Omnis','2022-01-29 12:36:56.989000','Omnis','2022-01-29 12:36:56.989000','dfb','df','dfbn','kyciwyni@mailinator.com','Brynne Parrish',2786,'8545253565',1,'dfbn',689.00,2,2,'JANUARY','2022','2022-01-29');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
