-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: dmc
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource` (
  `id` bigint NOT NULL,
  `name` varchar(100) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `seq` int DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `method` varchar(20) DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  `type` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sogl6f9lioeptbf7s105wbx82` (`pid`),
  KEY `fk_bjlrqegc9iu81src5vlta7p00` (`type`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `resource` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (0,'系统管理',NULL,0,NULL,NULL,NULL,0),(1,'角色管理','角色列表',2,'../roleList/roleList.html',NULL,0,0),(2,'用户管理','用户列表',3,'../userList/userList.html',NULL,0,0),(3,'资源管理','管理系统中所有的菜单或功能',1,'../menu/menu.html',NULL,0,0),(4,'添加角色',NULL,3,'/role','post',1,1),(5,'删除角色',NULL,6,'/role/*','delete',1,1),(6,'编辑角色',NULL,5,'/role','put',1,1),(7,'角色授权',NULL,8,'/role/grant','post',1,1),(9,'添加用户',NULL,3,'/user','post',2,1),(12,'删除用户',NULL,6,'/user/*','delete',2,1),(13,'编辑用户',NULL,5,'/user','put',2,1),(14,'用户修改密码',NULL,11,'/user/editpwd','post',2,1),(15,'用户授权',NULL,9,'/user/grant','post',2,1),(16,'添加资源',NULL,4,'/resource','post',3,1),(17,'删除资源',NULL,7,'/resource/*','delete',3,1),(18,'编辑资源',NULL,6,'/resource','put',3,1),(19,'资源树列表',NULL,2,'/resource/tree','post',3,1),(3907913782690816,'角色详情',NULL,NULL,'/role/*','get',1,1);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `name` varchar(100) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `seq` int DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tealaj0x99w9xj8on8ax0jgjb` (`pid`),
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (0,'超管','超级管理员角色，拥有系统中所有的资源访问权限',0,NULL),(51812385216,'3.1.1号管理','角色管理，但不能授权和编辑角色',3,35201556431108),(35201556431108,'3.1号管理','角色管理，但不能对角色进行授权',2,75926432661505),(40707738321028,'2号管理','只能进行资源管理',1,NULL),(70429180313920,'1.1号管理','用户管理，不能添加和删除用户',2,111068156805379),(75926432661505,'3号管理','只能进行角色管理',1,NULL),(111068156805379,'1号管理','只能进行用户管理',1,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_resource`
--

DROP TABLE IF EXISTS `role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_resource` (
  `role_id` bigint NOT NULL,
  `resource_id` bigint NOT NULL,
  PRIMARY KEY (`resource_id`,`role_id`),
  KEY `resource_id` (`resource_id`),
  KEY `role_resource_ibfk_1` (`role_id`),
  CONSTRAINT `role_resource_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `role_resource_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_resource`
--

LOCK TABLES `role_resource` WRITE;
/*!40000 ALTER TABLE `role_resource` DISABLE KEYS */;
INSERT INTO `role_resource` VALUES (0,0),(35201556431108,0),(40707738321028,0),(70429180313920,0),(75926432661505,0),(111068156805379,0),(0,1),(35201556431108,1),(75926432661505,1),(0,2),(70429180313920,2),(111068156805379,2),(0,3),(40707738321028,3),(0,4),(35201556431108,4),(75926432661505,4),(0,5),(35201556431108,5),(75926432661505,5),(0,6),(35201556431108,6),(75926432661505,6),(0,7),(75926432661505,7),(0,9),(111068156805379,9),(0,12),(111068156805379,12),(0,13),(70429180313920,13),(111068156805379,13),(0,14),(70429180313920,14),(111068156805379,14),(0,15),(70429180313920,15),(111068156805379,15),(0,16),(40707738321028,16),(0,17),(40707738321028,17),(0,18),(40707738321028,18),(0,19),(40707738321028,19),(0,3907913782690816),(35201556431108,3907913782690816),(75926432661505,3907913782690816);
/*!40000 ALTER TABLE `role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_o3uyea7py4jnln0qxrtg1qqhq` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,'2023-10-17 17:50:05','2023-10-18 10:47:26','超管','96e79218965eb72c92a549dd5a330112','admin'),(1,'2023-10-17 18:07:06','2023-10-17 18:34:10','admin1','202cb962ac59075b964b07152d234b70','admin1'),(39599603335232,'2023-10-19 22:49:25','2023-10-19 22:49:25','3号','1a100d2c0dab19c4430e7d73762b3423','henge3'),(70403104456773,'2023-10-19 22:49:08','2023-10-19 22:49:08','2号','e3ceb5881a0a1fdaad01296d7554868d','henge2'),(71494294847874,'2023-10-20 22:23:04','2023-10-20 22:23:04','3.1号','218d17fa010f5d5edf38d3eed577513a','henge31'),(71520098205766,'2023-10-19 22:55:18','2023-10-19 22:55:18','1.1号','7fa8282ad93047a4d6fe6111c93b308a','henge11'),(106661255856320,'2023-10-19 22:47:28','2023-10-19 22:47:28','1号','96e79218965eb72c92a549dd5a330112','henge1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  KEY `role_id_2` (`role_id`),
  KEY `role_id_3` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (0,0),(71494294847874,51812385216),(0,35201556431108),(71494294847874,35201556431108),(0,40707738321028),(70403104456773,40707738321028),(0,70429180313920),(71520098205766,70429180313920),(0,75926432661505),(39599603335232,75926432661505),(0,111068156805379),(1,111068156805379),(106661255856320,111068156805379);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-20 23:50:31
