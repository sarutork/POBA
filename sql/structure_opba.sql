-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.17-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for poba
DROP DATABASE IF EXISTS `poba`;
CREATE DATABASE IF NOT EXISTS `poba` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `poba`;

-- Dumping structure for table poba.academic_conference
DROP TABLE IF EXISTS `academic_conference`;
CREATE TABLE IF NOT EXISTS `academic_conference` (
  `conference_id` int(11) NOT NULL AUTO_INCREMENT,
  `thesis_id` int(11) NOT NULL,
  `journal_id` int(11) NOT NULL,
  `conference_topic` varchar(255) NOT NULL,
  `conference_name` varchar(255) NOT NULL,
  `conference_institution` varchar(255) NOT NULL,
  `conference_date_from` date NOT NULL,
  `conference_date_to` date NOT NULL,
  `conference_level` varchar(255) NOT NULL,
  `conference_doc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`conference_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.academic_service
DROP TABLE IF EXISTS `academic_service`;
CREATE TABLE IF NOT EXISTS `academic_service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.board_resolution
DROP TABLE IF EXISTS `board_resolution`;
CREATE TABLE IF NOT EXISTS `board_resolution` (
  `bord_id` int(11) NOT NULL,
  `bord_no` int(11) DEFAULT NULL,
  `bord_date` datetime DEFAULT NULL,
  `bord_type` varchar(255) DEFAULT NULL,
  `bord_attach` varchar(255) DEFAULT NULL,
  `note_of_board` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.consultant_students
DROP TABLE IF EXISTS `consultant_students`;
CREATE TABLE IF NOT EXISTS `consultant_students` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) DEFAULT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `students_id` int(11) DEFAULT NULL,
  `student_prefix` varchar(255) DEFAULT NULL,
  `student_prefix_other` varchar(255) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `student_surname` varchar(255) DEFAULT NULL,
  `year_of_study` varchar(255) DEFAULT NULL,
  `admission_status` varchar(255) DEFAULT NULL,
  `students_level` varchar(255) DEFAULT NULL,
  `course` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.consultant_thesis
DROP TABLE IF EXISTS `consultant_thesis`;
CREATE TABLE IF NOT EXISTS `consultant_thesis` (
  `thesis_id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) DEFAULT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `consultant_position` varchar(255) DEFAULT NULL,
  `students_id` int(11) DEFAULT NULL,
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
  PRIMARY KEY (`thesis_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.forget_pw
DROP TABLE IF EXISTS `forget_pw`;
CREATE TABLE IF NOT EXISTS `forget_pw` (
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.journal_info
DROP TABLE IF EXISTS `journal_info`;
CREATE TABLE IF NOT EXISTS `journal_info` (
  `journal_id` int(11) NOT NULL AUTO_INCREMENT,
  `thesis_id` int(11) NOT NULL,
  `journal_style` varchar(255) NOT NULL,
  `journal_topic` varchar(255) NOT NULL,
  `journal_name` varchar(255) NOT NULL,
  `journal_public_year` varchar(255) NOT NULL,
  `journal_issue` varchar(255) NOT NULL,
  `journal_year` varchar(255) NOT NULL,
  `journal_database` varchar(255) NOT NULL,
  `journal_level` varchar(255) NOT NULL,
  PRIMARY KEY (`journal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.login
DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.mgmt_position
DROP TABLE IF EXISTS `mgmt_position`;
CREATE TABLE IF NOT EXISTS `mgmt_position` (
  `staff_id` int(11) NOT NULL,
  `mgmt_id` varchar(255) NOT NULL,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `mgmt_start_date` datetime NOT NULL,
  `mgmt_cu_date` datetime NOT NULL,
  `mgmt_ohec_date` datetime NOT NULL,
  `mgmt_exp_date` datetime NOT NULL,
  `mgmt_total_date` int(11) NOT NULL,
  `mgmt_ohec_count` int(11) NOT NULL,
  `mgmt_person_count` int(11) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.present_work
DROP TABLE IF EXISTS `present_work`;
CREATE TABLE IF NOT EXISTS `present_work` (
  `present_id` int(11) NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.press_info
DROP TABLE IF EXISTS `press_info`;
CREATE TABLE IF NOT EXISTS `press_info` (
  `press_id` int(11) NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.profile
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `persNo` int NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.
-- Dumping structure for table poba.project
DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.project_polsci
DROP TABLE IF EXISTS `project_polsci`;
CREATE TABLE IF NOT EXISTS `project_polsci` (
  `polsci_id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(20) NOT NULL,
  `prefix_other` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `polsci_staff_type` varchar(255) NOT NULL,
  `polsci_staff_type_other` varchar(255),
  `polsci_year` varchar(255) NOT NULL,
  `polsci_name` varchar(255) NOT NULL,
  `polsci_pre_date_from` date NOT NULL,
  `polsci_pre_date_to` date NOT NULL,
  `polsci_date_from` date NOT NULL,
  `polsci_date_to` date NOT NULL,
  `polsci_location` varchar(255) NOT NULL,
  `polsci_status` varchar(255) NOT NULL,
  `polsci_status_other` varchar(255),
  `polsci_position` varchar(255) NOT NULL,
  `polsci_total_operation` int(11) NOT NULL,
  `polsci_total_attend` int(11) NOT NULL,
  `polsci_total_hour` int(11) NOT NULL,
  `polsci_doc_ref` varchar(255) NOT NULL,
  `note_of_polsci` varchar(255) NOT NULL,
  PRIMARY KEY (`polsci_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.published_info
DROP TABLE IF EXISTS `published_info`;
CREATE TABLE IF NOT EXISTS `published_info` (
  `published_id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255),
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `full_name_publisher` varchar(255) NOT NULL,
  `full_name_joiner` varchar(255) NOT NULL,
  `published_status` varchar(255) NOT NULL,
  `published_topic` varchar(255) NOT NULL,
  `published_journal` varchar(255) NOT NULL,
  `published_year` varchar(255) NOT NULL,
  `published_issue` varchar(255) NOT NULL,
  `published_month` varchar(255) NOT NULL,
  `published_year2` varchar(255) NOT NULL,
  `published_base` varchar(255) NOT NULL,
  `published_level` varchar(255) NOT NULL,
  PRIMARY KEY (`published_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.published_join
DROP TABLE IF EXISTS `published_join`;
CREATE TABLE IF NOT EXISTS `published_join` (
  `published_join_id` int(11) NOT NULL AUTO_INCREMENT,
  `published_id` int(11),
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255),
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `published_fund` decimal(12,2) NOT NULL,
  `published_q1` int(11) DEFAULT NULL,
  `published_q2` int(11) DEFAULT NULL,
  `published_q3` int(11) DEFAULT NULL,
  `published_q4` int(11) DEFAULT NULL,
  PRIMARY KEY (`published_join_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.researcher
DROP TABLE IF EXISTS `researcher`;
CREATE TABLE IF NOT EXISTS `researcher` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `r_status` varchar(255) NOT NULL,
  `r_type` varchar(255) NOT NULL,
  `work_start_date` date NOT NULL,
  `work_end_date` date NOT NULL,
  `doc_of_work` varchar(255) DEFAULT NULL,
  `note_of_work` varchar(255) DEFAULT NULL,
  `teacher_1` varchar(255) DEFAULT NULL,
  `teacher_2` varchar(255) DEFAULT NULL,
  `sub_segment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.reward
DROP TABLE IF EXISTS `reward`;
CREATE TABLE IF NOT EXISTS `reward` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `reward_id` int(11) NOT NULL,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255),
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  PRIMARY KEY (`staff_id`,`reward_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.reward_detail
DROP TABLE IF EXISTS `reward_detail`;
CREATE TABLE IF NOT EXISTS `reward_detail` (
  `reward_id` int(11) NOT NULL AUTO_INCREMENT,
  `reward_type` varchar(255) NOT NULL,
  `reward_date` date NOT NULL,
  `reward_name` varchar(255) NOT NULL,
  `reward_topic` varchar(255) NOT NULL,
  `reward_institution` varchar(255) NOT NULL,
  `reward_level` varchar(255) NOT NULL,
  PRIMARY KEY (`reward_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.students
DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
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
  `students_tel` int(11) NOT NULL,
  `students_telemergency` int(11) DEFAULT NULL,
  `students_relation` varchar(255) DEFAULT NULL,
  `students_relation_other` varchar(255) DEFAULT NULL,
  `students_email` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255),
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `students_status` varchar(255) DEFAULT NULL,
  `students_success` varchar(255) NOT NULL,
  `students_outreason` varchar(255) DEFAULT NULL,
  `students_retryreason` varchar(255) DEFAULT NULL,
  `students_update` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.students_report
DROP TABLE IF EXISTS `students_report`;
CREATE TABLE IF NOT EXISTS `students_report` (
  `staff_id` int(11) NOT NULL,
  `students_id` int(11) NOT NULL,
  `report_type` varchar(255) DEFAULT NULL,
  `report_cu_year` varchar(255) DEFAULT NULL,
  `report_sector` varchar(255) DEFAULT NULL,
  `report_level` varchar(255) DEFAULT NULL,
  `report_thai` varchar(255) DEFAULT NULL,
  `report_foreign` varchar(255) DEFAULT NULL,
  `report_course` int(11) DEFAULT NULL,
  `report_mua` int(11) DEFAULT NULL,
  `report_getreal` int(11) DEFAULT NULL,
  `report_stay` int(11) DEFAULT NULL,
  `report_diff` varchar(255) DEFAULT NULL,
  `report_no` int(11) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `report_display_year` varchar(255) DEFAULT NULL,
  `report_consult_type` varchar(255) DEFAULT NULL,
  `note_of_report` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_id`,`students_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.study_info
DROP TABLE IF EXISTS `study_info`;
CREATE TABLE IF NOT EXISTS `study_info` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `travel_order` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `total_day` int(10) NOT NULL,
  `total_month` int(10) NOT NULL,
  `total_year` int(10) NOT NULL,
  `activity_detail` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `location_type` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `fund` varchar(255) NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.table_1
DROP TABLE IF EXISTS `table_1`;
CREATE TABLE IF NOT EXISTS `table_1` (
  `cxv` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.teach_info
DROP TABLE IF EXISTS `teach_info`;
CREATE TABLE IF NOT EXISTS `teach_info` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `study_year` varchar(255) NOT NULL,
  `semester` varchar(255) NOT NULL,
  `study_type` varchar(255) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `subject_name` varchar(255) NOT NULL,
  `subject_credit` int(11) NOT NULL,
  `current_credit` int(11) NOT NULL,
  `teach_type` varchar(255) NOT NULL,
  `teach_day` varchar(255) NOT NULL,
  `study_start` date NULL DEFAULT NULL,
  `study_end` date NULL DEFAULT NULL,
  `teach_location` varchar(255) NOT NULL,
  `teachLocationOther` varchar(255),
  `teach_room` varchar(255) NOT NULL,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255),
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `teach_status` varchar(255) NOT NULL,
  `institution_info` varchar(255) DEFAULT NULL,
  `teach_topic` varchar(255) DEFAULT NULL,
  `teach_times` int(11) DEFAULT NULL,
  `teach_date` datetime DEFAULT NULL,
  `note_of_teach` varchar(255) DEFAULT NULL,
  `teach_style` varchar(255) NOT NULL,
  `teach_style_detail` varchar(255) NOT NULL,
  `total_of_students` varchar(255) NOT NULL,
  `total_students_register` varchar(255) NOT NULL,
  `midterm_exam_date` date DEFAULT NULL,
  `midterm_exam_time` time DEFAULT NULL,
  `final_exam_date` date DEFAULT NULL,
  `final_exam_time` time DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.textbook
DROP TABLE IF EXISTS `textbook`;
CREATE TABLE IF NOT EXISTS `textbook` (
  `textbook_id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) NOT NULL,
  `prefix_other` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `textbook_type` varchar(255) NOT NULL,
  `textbook_announce` varchar(255) NOT NULL,
  `textbook_contract` varchar(255) NOT NULL,
  `textbook_topic` varchar(255) NOT NULL,
  `textbook_fund` varchar(255) NOT NULL,
  `textbook_amount_total` int(11) NOT NULL,
  `textbook_date_from` date NOT NULL,
  `textbook_date_to` date NOT NULL,
  `textbook_extend_date` date NOT NULL,
  `textbook_status` varchar(255) NOT NULL,
  `textbook_pb_type` varchar(255) NOT NULL,
  `textbook_issue` varchar(255) NOT NULL,
  `textbook_year` varchar(255) NOT NULL,
  `textbook_diff_text` varchar(255) NOT NULL,
  `textbook_institution` varchar(255) NOT NULL,
  `textbook_ref` varchar(255) NOT NULL,
  `textbook_TCI` varchar(255) NOT NULL,
  `textbook_level` varchar(255) NOT NULL,
  `textbook_q1` varchar(255) DEFAULT NULL,
  `textbook_q2` varchar(255) DEFAULT NULL,
  `textbook_q3` varchar(255) DEFAULT NULL,
  `textbook_q4` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`textbook_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table poba.training
DROP TABLE IF EXISTS `training`;
CREATE TABLE IF NOT EXISTS `training` (
  `training_id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `training_status` varchar(255) DEFAULT NULL,
  `training_name` varchar(255) DEFAULT NULL,
  `training_location` varchar(255) DEFAULT NULL,
  `training_date_from` date DEFAULT NULL,
  `training_time_from` varchar(5) DEFAULT NULL,
  `training_date_to` date DEFAULT NULL,
  `training_time_to` varchar(5) DEFAULT NULL,
  `training_total_day` int(11) DEFAULT NULL,
  `training_type` varchar(255) DEFAULT NULL,
  `training_announce` varchar(255) DEFAULT NULL,
  `training_budget` double DEFAULT NULL,
  `training_phase` int(11) DEFAULT NULL,
  `training_amount` double DEFAULT NULL,
  `training_join` varchar(255) DEFAULT NULL,
  `training_thai` int(11) DEFAULT NULL,
  `training_foreign` int(11) DEFAULT NULL,
  `training_total_person` int(11) DEFAULT NULL,
  `training_level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping structure for table poba.upload
DROP TABLE IF EXISTS `upload`;
CREATE TABLE IF NOT EXISTS `upload` (
  `upload_id` int NOT NULL AUTO_INCREMENT,
  `upload_group` varchar(35) NOT NULL,
  `reference_key` int NOT NULL,
  `original_name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`upload_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


-- poba.textbook_phase definition
CREATE TABLE `textbook_phase` (
  `textbook_phase_id` int NOT NULL AUTO_INCREMENT,
  `textbook_id` int NOT NULL,
  `textbook_amount` int NOT NULL,
  `textbook_phase` int NOT NULL,
  `textbook_withdraw` date NOT NULL,
  PRIMARY KEY (`textbook_phase_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE poba.p_years (
	id INT auto_increment NOT NULL,
	`year` varchar(4) NOT NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `p_years`;
CREATE TABLE IF NOT EXISTS `p_years` (
  `id` int NOT NULL AUTO_INCREMENT,
  `year` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;