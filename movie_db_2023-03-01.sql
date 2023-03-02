# ************************************************************
# Sequel Ace SQL dump
# Version 20046
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: 127.0.0.1 (MySQL 8.0.32)
# Database: movie_db
# Generation Time: 2023-03-01 03:07:10 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table movie
# ------------------------------------------------------------

DROP TABLE IF EXISTS `movie`;

CREATE TABLE `movie` (
  `movie_id` int NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `genre` varchar(45) DEFAULT NULL,
  `year` year DEFAULT NULL,
  `info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `rating` float DEFAULT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;

INSERT INTO `movie` (`movie_id`, `title`, `genre`, `year`, `info`, `rating`)
VALUES
	(1,'The Shawshank Redemption','Drama','1994','Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.',4.5),
	(2,'The Godfather','Crime, Drama','1972','An organized crime dynasty\'s aging patriarch transfers control of his clandestine empire to his reluctant son.',NULL),
	(3,'The Dark Knight','Action, Crime, Drama','2008','When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.',NULL),
	(4,'The Godfather: Part II','Crime, Drama','1974','The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.',NULL),
	(5,'12 Angry Men','Crime, Drama','1957','A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.',NULL),
	(6,'The Lord of the Rings: The Return of the King','Action, Adventure, Drama','2003','Gandalf and Aragorn lead the World of Men against Sauron\'s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.',NULL),
	(7,'Pulp Fiction','Crime, Drama','1994','The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.',NULL),
	(8,'Schindler\'s List','Biography, Drama, History','1993','In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.',NULL),
	(9,'Inception','Action, Adventure, Sci-Fi','2010','A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.',NULL),
	(10,'Fight Club','Drama','1999','An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.',NULL);

/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table review
# ------------------------------------------------------------

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `review_id` int NOT NULL,
  `movie_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `stars` int DEFAULT NULL,
  `review_content` varchar(45) DEFAULT NULL,
  `review_time` datetime DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `fk_movid_id_idx` (`movie_id`),
  KEY `fk_user_id_idx` (`user_id`),
  CONSTRAINT `fk_movid_id` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;

INSERT INTO `review` (`review_id`, `movie_id`, `user_id`, `stars`, `review_content`, `review_time`)
VALUES
	(1,1,1,5,'nice, 5 star','2023-02-25 00:00:00'),
	(2,2,1,4,'good, 4 star','2023-02-25 00:00:01'),
	(3,1,2,4,'asdf','2023-02-25 15:10:24');

/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `add_time` BEFORE INSERT ON `review` FOR EACH ROW begin
if ISNULL(NEW.review_time) then
	set NEW.review_time = NOW();
end if;
end */;;
DELIMITER ;
/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE */;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `avatar_link` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_admin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`user_id`, `user_name`, `password`, `avatar_link`, `is_admin`)
VALUES
	(1,'guoxiaoyan','xiaoyanguo','https://cdn.jsdelivr.net/gh/Tangent617/Tangent617.github.io@main/images/avatar.png',1),
	(2,'jiangxuzhao','xuzhaojiang',NULL,1),
	(3,'wuxiang','xiangwu',NULL,1),
	(4,'dodobird','ygkldnh',NULL,0);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Dumping routines (PROCEDURE) for database 'movie_db'
--
DELIMITER ;;

# Dump of PROCEDURE cal_rating
# ------------------------------------------------------------

/*!50003 DROP PROCEDURE IF EXISTS `cal_rating` */;;
/*!50003 SET SESSION SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION"*/;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `cal_rating`(in movie_id_in integer)
BEGIN
	declare avg_star float;
	select AVG(stars) into avg_star from review where movie_id = movie_id_in;
	update movie set rating = avg_star where movie_id = movie_id_in;
END */;;

/*!50003 SET SESSION SQL_MODE=@OLD_SQL_MODE */;;
DELIMITER ;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
