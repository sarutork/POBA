-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (arm64)
--
-- Host: localhost    Database: poba
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `academic_conference`
--
USE `poba`;

DROP TABLE IF EXISTS `academic_conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academic_conference` (
  `conference_id` int NOT NULL AUTO_INCREMENT,
  `thesis_id` int NOT NULL,
  `journal_id` int NOT NULL,
  `conference_topic` varchar(255) NOT NULL,
  `conference_name` varchar(255) NOT NULL,
  `conference_institution` varchar(255) NOT NULL,
  `conference_date_from` date NOT NULL,
  `conference_date_to` date NOT NULL,
  `conference_level` varchar(255) NOT NULL,
  PRIMARY KEY (`conference_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `academic_service`
--

DROP TABLE IF EXISTS `academic_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academic_service` (
  `service_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `service_status` varchar(255) NOT NULL,
  `service_order` varchar(255) NOT NULL,
  `service_date_from` date NOT NULL,
  `service_date_to` date NOT NULL,
  `note_of_service` varchar(255) NOT NULL,
  `service_position` varchar(255) NOT NULL,
  `service_institution` varchar(255) NOT NULL,
  `service_level` varchar(255) NOT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `board_resolution`
--

DROP TABLE IF EXISTS `board_resolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_resolution` (
  `bord_id` bigint NOT NULL AUTO_INCREMENT,
  `bord_no1` int NOT NULL,
  `bord_no2` int NOT NULL,
  `bord_attach` varchar(255) DEFAULT NULL,
  `bord_date` date NOT NULL,
  `bord_type` varchar(255) DEFAULT NULL,
  `note_of_board` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

-- poba.resolution definition

--
-- Table structure for table `consultant_student_report_dto`
--

DROP TABLE IF EXISTS `consultant_student_report_dto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultant_student_report_dto` (
  `staff_id` bigint NOT NULL AUTO_INCREMENT,
  `count_student` int DEFAULT NULL,
  `course` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `students_level` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `year_of_study` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `consultant_students`
--

DROP TABLE IF EXISTS `consultant_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultant_students` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `students_id` int DEFAULT NULL,
  `student_prefix` varchar(255) DEFAULT NULL,
  `year_of_study` varchar(255) DEFAULT NULL,
  `admission_status` varchar(255) DEFAULT NULL,
  `students_level` varchar(255) DEFAULT NULL,
  `course` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `student_surname` varchar(255) DEFAULT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `student_prefix_other` varchar(255) DEFAULT NULL,
  `count_student` int DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `consultant_thesis`
--

DROP TABLE IF EXISTS `consultant_thesis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultant_thesis` (
  `thesis_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) DEFAULT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `consultant_position` varchar(255) DEFAULT NULL,
  `student_prefix` varchar(255) DEFAULT NULL,
  `student_prefix_other` varchar(255) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `student_surname` varchar(255) DEFAULT NULL,
  `student_level` varchar(255) DEFAULT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `thesis_type` varchar(255) DEFAULT NULL,
  `thesis_topic` varchar(255) DEFAULT NULL,
  `thesis_consider` varchar(255) DEFAULT NULL,
  `thesis_startdate` date DEFAULT NULL,
  `thesis_enddate` date DEFAULT NULL,
  `thesis_approve` varchar(255) DEFAULT NULL,
  `thesis_success` varchar(255) DEFAULT NULL,
  `thesis_success_date` date DEFAULT NULL,
  `thesis_assessment` varchar(255) DEFAULT NULL,
  `students_id` int NOT NULL,
  PRIMARY KEY (`thesis_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forget_pw`
--

DROP TABLE IF EXISTS `forget_pw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forget_pw` (
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `journal_info`
--

DROP TABLE IF EXISTS `journal_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `journal_info` (
  `journal_id` int NOT NULL AUTO_INCREMENT,
  `thesis_id` int NOT NULL,
  `journal_style` varchar(255) NOT NULL,
  `journal_topic` varchar(255) NOT NULL,
  `journal_name` varchar(255) NOT NULL,
  `journal_public_year` varchar(255) NOT NULL,
  `journal_issue` varchar(255) NOT NULL,
  `journal_year` varchar(255) NOT NULL,
  `journal_database` varchar(255) NOT NULL,
  `journal_level` varchar(255) NOT NULL,
  PRIMARY KEY (`journal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login`
--

-- Data exporting was unselected.
-- Dumping structure for table poba.login
DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS login (
  user_id bigint NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) NOT NULL,
  reset_password varchar(1) NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT constraint_username UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mgmt_position`
--

DROP TABLE IF EXISTS `mgmt_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mgmt_position` (
  `staff_id` int NOT NULL,
  `mgmt_id` varchar(255) NOT NULL,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `mgmt_start_date` datetime NOT NULL,
  `mgmt_cu_date` datetime NOT NULL,
  `mgmt_ohec_date` datetime NOT NULL,
  `mgmt_exp_date` datetime NOT NULL,
  `mgmt_total_date` int NOT NULL,
  `mgmt_ohec_count` int NOT NULL,
  `mgmt_person_count` int NOT NULL,
  `mgmt_ohec_level` varchar(255) NOT NULL,
  `mgmt_ohec_structure` varchar(255) NOT NULL,
  `mgmt_type` varchar(255) NOT NULL,
  `mgmt_ohec_position` varchar(255) NOT NULL,
  `mgmt_ohec_accademic` varchar(255) NOT NULL,
  `mgmt_ohec_level1` varchar(255) NOT NULL,
  `mgmt_ohec_level2` varchar(255) NOT NULL,
  `mgmt_ohec_level3` varchar(255) NOT NULL,
  `mgmt_ohec_level4` varchar(255) NOT NULL,
  `mgmt_group` varchar(255) NOT NULL,
  `mgmt_subgroup` varchar(255) NOT NULL,
  `mgmt_position` varchar(255) NOT NULL,
  `mgmt_work` varchar(255) NOT NULL,
  `mgmt_contract` varchar(255) NOT NULL,
  `mgmt_scrope` varchar(255) NOT NULL,
  PRIMARY KEY (`staff_id`,`mgmt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `p_years`
--

DROP TABLE IF EXISTS `p_years`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `p_years` (
  `id` int NOT NULL AUTO_INCREMENT,
  `year` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `present_work`
--

DROP TABLE IF EXISTS `present_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `present_work` (
  `present_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `present_topic` varchar(255) NOT NULL,
  `present_name` varchar(255) NOT NULL,
  `present_institution` varchar(255) NOT NULL,
  `present_fund` varchar(255) NOT NULL,
  `present_amount` double NOT NULL,
  `present_date_start` date NOT NULL,
  `present_date_end` date NOT NULL,
  `present_level` varchar(255) NOT NULL,
  PRIMARY KEY (`present_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `press_info`
--

DROP TABLE IF EXISTS `press_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `press_info` (
  `press_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `press_name` varchar(255) NOT NULL,
  `press_date` date NOT NULL,
  `press_topic` varchar(255) NOT NULL,
  `press_site_ref` varchar(255) NOT NULL,
  `press_print` varchar(255) NOT NULL,
  `press_tv` varchar(255) NOT NULL,
  `press_satellite` varchar(255) NOT NULL,
  `press_online` varchar(255) NOT NULL,
  `guest_prefix1` varchar(255) NOT NULL,
  `guest_prefix_other1` varchar(255) NOT NULL,
  `guest_name1` varchar(255) NOT NULL,
  `guest_surname1` varchar(255) NOT NULL,
  `guest_prefix2` varchar(255) NOT NULL,
  `guest_prefix_other2` varchar(255) NOT NULL,
  `guest_name2` varchar(255) NOT NULL,
  `guest_surname2` varchar(255) NOT NULL,
  `guest_prefix3` varchar(255) NOT NULL,
  `guest_prefix_other3` varchar(255) NOT NULL,
  `guest_name3` varchar(255) NOT NULL,
  `guest_surname3` varchar(255) NOT NULL,
  PRIMARY KEY (`press_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `pers_no` int NOT NULL,
  `prefix` varchar(20) NOT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email_org` varchar(255) NOT NULL,
  `email_personal` varchar(255) NOT NULL,
  `tel` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `start_work_date` date NOT NULL,
  `start_count_work_date` date NOT NULL,
  `start_count_workohecdate` date NOT NULL,
  `to_work_date` date NOT NULL,
  `total_work_year` int NOT NULL,
  `total_work_month` int NOT NULL,
  `total_work_day` int NOT NULL,
  `total_workohec` int NOT NULL,
  `total` int NOT NULL,
  `ohec_education_level` varchar(255) NOT NULL,
  `ohec_structure` varchar(255) NOT NULL,
  `section` varchar(255) NOT NULL,
  `ohec_position_group` varchar(255) NOT NULL,
  `ohec_academic_position` varchar(255) NOT NULL,
  `structure_name_level1` varchar(255) NOT NULL,
  `structure_name_level2` varchar(255) NOT NULL,
  `structure_name_level3` varchar(255) NOT NULL,
  `structure_name_level4` varchar(255) NOT NULL,
  `staff_group` varchar(255) NOT NULL,
  `staff_sub_group` varchar(255) NOT NULL,
  `position` varchar(255) NOT NULL,
  `work` varchar(255) NOT NULL,
  `emp_contract` varchar(255) NOT NULL,
  `sub_section_scope_of_work` varchar(255) NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `project_year` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `project_institution` varchar(255) DEFAULT NULL,
  `project_type` varchar(255) DEFAULT NULL,
  `project_budget` double DEFAULT NULL,
  `project_budget_approve` double DEFAULT NULL,
  `project_person_target` int DEFAULT NULL,
  `project_person_actual` int DEFAULT NULL,
  `project_pre_date_from` date DEFAULT NULL,
  `project_pre_date_to` date DEFAULT NULL,
  `project_date_from` date DEFAULT NULL,
  `project_date_to` date DEFAULT NULL,
  `project_location` varchar(255) DEFAULT NULL,
  `project_kind` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_polsci`
--

DROP TABLE IF EXISTS `project_polsci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_polsci` (
  `polsci_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(20) NOT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `polsci_staff_type` varchar(255) NOT NULL,
  `polsci_staff_type_other` varchar(255) DEFAULT NULL,
  `polsci_year` varchar(255) NOT NULL,
  `polsci_name` varchar(255) NOT NULL,
  `polsci_pre_date_from` date NOT NULL,
  `polsci_pre_date_to` date NOT NULL,
  `polsci_date_from` date NOT NULL,
  `polsci_date_to` date NOT NULL,
  `polsci_location` varchar(255) NOT NULL,
  `polsci_status` varchar(255) NOT NULL,
  `polsci_status_other` varchar(255) DEFAULT NULL,
  `polsci_position` varchar(255) NOT NULL,
  `polsci_total_operation` int NOT NULL,
  `polsci_total_attend` int NOT NULL,
  `polsci_total_hour` int NOT NULL,
  `polsci_doc_ref` varchar(255) NOT NULL,
  `note_of_polsci` varchar(255) NOT NULL,
  PRIMARY KEY (`polsci_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `published_info`
--

DROP TABLE IF EXISTS `published_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `published_info` (
  `published_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `full_name_publisher` varchar(255) NOT NULL,
  `full_name_joiner` varchar(255) NOT NULL,
  `published_status` varchar(255) NOT NULL,
  `published_topic` varchar(255) NOT NULL,
  `published_journal` varchar(255) NOT NULL,
  `published_year` varchar(255) DEFAULT NULL,
  `published_issue` varchar(255) DEFAULT NULL,
  `published_month` varchar(255) DEFAULT NULL,
  `published_year2` varchar(255) NOT NULL,
  `published_base` varchar(255) NOT NULL,
  `published_level` varchar(255) NOT NULL,
  PRIMARY KEY (`published_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `published_join`
--

DROP TABLE IF EXISTS `published_join`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `published_join` (
  `published_join_id` int NOT NULL AUTO_INCREMENT,
  `published_id` int DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `published_fund` decimal(12,2) DEFAULT NULL,
  `published_q1` int DEFAULT NULL,
  `published_q2` int DEFAULT NULL,
  `published_q3` int DEFAULT NULL,
  `published_q4` int DEFAULT NULL,
  PRIMARY KEY (`published_join_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `researcher`
--

DROP TABLE IF EXISTS `researcher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `researcher` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `r_status` varchar(255) NOT NULL,
  `r_type` varchar(255) NOT NULL,
  `length_of_work` datetime DEFAULT NULL,
  `doc_of_work` varchar(255) DEFAULT NULL,
  `note_of_work` varchar(255) DEFAULT NULL,
  `work_end_date` date NOT NULL,
  `work_start_date` date NOT NULL,
  `sub_segment` varchar(255) DEFAULT NULL,
  `teacher_1` varchar(255) DEFAULT NULL,
  `teacher_2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reward`
--

DROP TABLE IF EXISTS `reward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reward` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `reward_id` int NOT NULL,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`,`reward_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reward_detail`
--

DROP TABLE IF EXISTS `reward_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reward_detail` (
  `reward_id` int NOT NULL AUTO_INCREMENT,
  `reward_type` varchar(255) NOT NULL,
  `reward_date` date NOT NULL,
  `reward_name` varchar(255) NOT NULL,
  `reward_topic` varchar(255) NOT NULL,
  `reward_institution` varchar(255) NOT NULL,
  `reward_level` varchar(255) NOT NULL,
  PRIMARY KEY (`reward_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` int NOT NULL AUTO_INCREMENT,
  `students_id` varchar(11) NOT NULL,
  `students_prefix` varchar(255) NOT NULL,
  `students_prefix_other` varchar(255) NOT NULL,
  `students_name` varchar(255) NOT NULL,
  `students_surname` varchar(255) NOT NULL,
  `students_year` varchar(255) NOT NULL,
  `students_how` varchar(255) NOT NULL,
  `students_level` varchar(255) NOT NULL,
  `students_course` varchar(255) NOT NULL,
  `students_tel` int NOT NULL,
  `students_telemergency` int DEFAULT NULL,
  `students_relation` varchar(255) DEFAULT NULL,
  `students_relation_other` varchar(255) DEFAULT NULL,
  `students_email` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `students_status` varchar(255) DEFAULT NULL,
  `students_success` varchar(255) NOT NULL,
  `students_outreason` varchar(255) DEFAULT NULL,
  `students_retryreason` varchar(255) DEFAULT NULL,
  `students_update` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `students_report`
--

DROP TABLE IF EXISTS `students_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_report` (
  `staff_id` int NOT NULL,
  `students_id` int NOT NULL,
  `report_type` varchar(255) DEFAULT NULL,
  `report_cu_year` varchar(255) DEFAULT NULL,
  `report_sector` varchar(255) DEFAULT NULL,
  `report_level` varchar(255) DEFAULT NULL,
  `report_thai` varchar(255) DEFAULT NULL,
  `report_foreign` varchar(255) DEFAULT NULL,
  `report_course` int DEFAULT NULL,
  `report_mua` int DEFAULT NULL,
  `report_getreal` int DEFAULT NULL,
  `report_stay` int DEFAULT NULL,
  `report_diff` varchar(255) DEFAULT NULL,
  `report_no` int DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `report_display_year` varchar(255) DEFAULT NULL,
  `report_consult_type` varchar(255) DEFAULT NULL,
  `note_of_report` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`,`students_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `study_info`
--

DROP TABLE IF EXISTS `study_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_info` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `travel_order` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `total_day` int NOT NULL,
  `total_month` int NOT NULL,
  `total_year` int NOT NULL,
  `activity_detail` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `location_type` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `fund` varchar(255) NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `table_1`
--

DROP TABLE IF EXISTS `table_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_1` (
  `cxv` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teach_info`
--

DROP TABLE IF EXISTS `teach_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teach_info` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `study_year` varchar(255) NOT NULL,
  `semester` varchar(255) NOT NULL,
  `study_type` varchar(255) NOT NULL,
  `subject_id` int NOT NULL,
  `subject_name` varchar(255) NOT NULL,
  `subject_credit` int NOT NULL,
  `current_credit` int NOT NULL,
  `teach_type` varchar(255) NOT NULL,
  `teach_day` varchar(255) NOT NULL,
  `study_start` varchar(5) DEFAULT NULL,
  `study_end` varchar(5) DEFAULT NULL,
  `teach_location` varchar(255) NOT NULL,
  `teach_room` varchar(255) NOT NULL,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `teach_status` varchar(255) NOT NULL,
  `institution_info` varchar(255) DEFAULT NULL,
  `teach_topic` varchar(255) DEFAULT NULL,
  `teach_times` int DEFAULT NULL,
  `teach_date` date DEFAULT NULL,
  `note_of_teach` varchar(255) DEFAULT NULL,
  `teach_style` varchar(255) NOT NULL,
  `teach_style_detail` varchar(255) NOT NULL,
  `total_of_students` varchar(255) NOT NULL,
  `total_students_register` varchar(255) NOT NULL,
  `midterm_exam_date` date DEFAULT NULL,
  `midterm_exam_time_start` varchar(5) DEFAULT NULL,
  `final_exam_date` date DEFAULT NULL,
  `final_exam_time_start` varchar(5) DEFAULT NULL,
  `final_exam_time_end` varchar(5) DEFAULT NULL,
  `midterm_exam_time_end` varchar(5) DEFAULT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `teach_location_other` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `textbook`
--

DROP TABLE IF EXISTS `textbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `textbook` (
  `textbook_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `textbook_type` varchar(255) NOT NULL,
  `textbook_announce` varchar(255) DEFAULT NULL,
  `textbook_contract` varchar(255) DEFAULT NULL,
  `textbook_topic` varchar(255) NOT NULL,
  `textbook_fund` varchar(255) NOT NULL,
  `textbook_amount_total` double NOT NULL,
  `textbook_date_from` date NOT NULL,
  `textbook_date_to` date NOT NULL,
  `textbook_extend_date` date DEFAULT NULL,
  `textbook_status` varchar(255) NOT NULL,
  `textbook_pb_type` varchar(255) NOT NULL,
  `textbook_issue` varchar(255) NOT NULL,
  `textbook_year` varchar(255) NOT NULL,
  `textbook_diff_text` varchar(255) DEFAULT NULL,
  `textbook_institution` varchar(255) NOT NULL,
  `textbook_ref` varchar(255) DEFAULT NULL,
  `textbook_TCI` varchar(255) NOT NULL,
  `textbook_level` varchar(255) NOT NULL,
  PRIMARY KEY (`textbook_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `textbook_phase`
--

DROP TABLE IF EXISTS `textbook_phase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `textbook_phase` (
  `textbook_phase_id` int NOT NULL AUTO_INCREMENT,
  `textbook_id` int NOT NULL,
  `textbook_amount` double NOT NULL,
  `textbook_phase` int NOT NULL,
  `textbook_withdraw` date NOT NULL,
  PRIMARY KEY (`textbook_phase_id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `training`
--

DROP TABLE IF EXISTS `training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training` (
  `training_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `training_status` varchar(255) DEFAULT NULL,
  `training_name` varchar(255) DEFAULT NULL,
  `training_location` varchar(255) DEFAULT NULL,
  `training_date_from` date DEFAULT NULL,
  `training_time_from` varchar(5) DEFAULT NULL,
  `training_date_to` date DEFAULT NULL,
  `training_time_to` varchar(5) DEFAULT NULL,
  `training_total_day` int DEFAULT NULL,
  `training_type` varchar(255) DEFAULT NULL,
  `training_announce` varchar(255) DEFAULT NULL,
  `training_budget` double DEFAULT NULL,
  `training_phase` int DEFAULT NULL,
  `training_amount` double DEFAULT NULL,
  `training_join` varchar(255) DEFAULT NULL,
  `training_thai` int DEFAULT NULL,
  `training_foreign` int DEFAULT NULL,
  `training_total_person` int DEFAULT NULL,
  `training_level` varchar(255) DEFAULT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`training_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `upload`
--

DROP TABLE IF EXISTS `upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `upload` (
  `upload_id` bigint NOT NULL AUTO_INCREMENT,
  `original_name` varchar(255) DEFAULT NULL,
  `upload_group` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `reference_key` bigint DEFAULT NULL,
  PRIMARY KEY (`upload_id`)
) ENGINE=MyISAM AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'poba'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-24 23:16:31


--published_info
ALTER TABLE published_info ADD month_other  varchar(255);
ALTER TABLE published_info ADD published_page  varchar(255);
ALTER TABLE published_info ADD published_Type varchar(255);

--present_work
ALTER TABLE present_work ADD present_country varchar(255);
ALTER TABLE present_work ADD present_fund2 varchar(255);
ALTER TABLE present_work ADD present_amount2 double;

--textbook
ALTER TABLE textbook ADD textbook_extend_date2 date;
ALTER TABLE textbook ADD textbook_extend_date3 date;


