/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50719
Source Host           : 10.20.2.116:3306
Source Database       : schedule

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-10-18 14:45:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_BLOB_TRIGGERS`;
CREATE TABLE `qrtz_BLOB_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_BLOB_TRIGGERS_ibfk_1`
  FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Trigger 作为 Blob 类型存储';

-- ----------------------------
-- Table structure for qrtz_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_CALENDARS`;
CREATE TABLE `qrtz_CALENDARS` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '以 Blob 类型存储 Quartz 的 Calendar 信息';

-- ----------------------------
-- Table structure for qrtz_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_CRON_TRIGGERS`;
CREATE TABLE `qrtz_CRON_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(120) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '存储 Cron Trigger，包括 Cron 表达式和时区信息';

-- ----------------------------
-- Table structure for qrtz_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_FIRED_TRIGGERS`;
CREATE TABLE `qrtz_FIRED_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(20) NOT NULL,
  `sched_time` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(5) DEFAULT NULL,
  `requests_recovery` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT '存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息';

-- ----------------------------
-- Table structure for qrtz_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_JOB_DETAILS`;
CREATE TABLE `qrtz_JOB_DETAILS` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(5) NOT NULL,
  `is_nonconcurrent` varchar(5) NOT NULL,
  `is_update_data` varchar(5) NOT NULL,
  `requests_recovery` varchar(5) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '存储每一个已配置的 Job 的详细信息';

-- ----------------------------
-- Table structure for qrtz_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_LOCKS`;
CREATE TABLE `qrtz_LOCKS` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '存储程序的悲观锁的信息 ';

-- ----------------------------
-- Table structure for qrtz_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `qrtz_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '存储已暂停的 Trigger 组的信息';

-- ----------------------------
-- Table structure for qrtz_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_SCHEDULER_STATE`;
CREATE TABLE `qrtz_SCHEDULER_STATE` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(20) NOT NULL,
  `checkin_interval` bigint(20) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '存储少量的有关 Scheduler 的状态信息，和别的 Scheduler 实例';

-- ----------------------------
-- Table structure for qrtz_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_SIMPLE_TRIGGERS`;
CREATE TABLE `qrtz_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(20) NOT NULL,
  `repeat_interval` bigint(20) NOT NULL,
  `times_triggered` bigint(20) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '存储简单的 Trigger，包括重复次数，间隔，以及已触的次数';

-- ----------------------------
-- Table structure for qrtz_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_SIMPROP_TRIGGERS`;
CREATE TABLE `qrtz_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(5) DEFAULT NULL,
  `bool_prop_2` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_TRIGGERS`;
CREATE TABLE `qrtz_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(20) DEFAULT NULL,
  `prev_fire_time` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(20) NOT NULL,
  `end_time` bigint(20) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(6) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '存储已配置的 Trigger 的信息';
