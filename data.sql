-- Create database schema.
CREATE SCHEMA `energygame`;

USE `energygame`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'jiaxiang.shan','8106417f482b5b3a30a433f4a31704bf',1);
UNLOCK TABLES;

--
-- Table structure for table `gameResult`
--

DROP TABLE IF EXISTS `gameResult`;
CREATE TABLE `gameResult` (
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(10) DEFAULT NULL,
    `score` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='gameResult table for energy game';

--
-- Dumping data for table `gameResult`
--

LOCK TABLES `gameResult` WRITE;
INSERT INTO `gameResult` VALUES (1, 'Test', 10000000),
(2, 'Bill', 20000000),
(3, 'Tim', 50000000);
UNLOCK TABLES;

-- Create user for the database.gameresult
CREATE USER 'energyGameUser'@'localhost' IDENTIFIED WITH mysql_native_password BY '123';
GRANT ALL PRIVILEGES ON energygame.* TO 'energyGameUser'@'localhost'; 