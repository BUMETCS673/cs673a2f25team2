/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : localhost:3306
 Source Schema         : db_health

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 06/07/2025 08:37:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for detail
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sport_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `disease` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `notes` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of detail
-- ----------------------------
BEGIN;
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (1, 'Gymnastics', 'Heart disease', 'Moderate exercise, avoid strenuous activities, and maintain a happy mood', 'Avoid exercising in environments that are too hot or too cold');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (2, 'Tai Chi', 'Hypertension', 'Gentle movements with slow and steady breathing', 'Keep calm and avoid excessive tension');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (3, 'Running fast', 'Arthritis', 'Choose a running track or soft surface and warm up properly', 'Avoid excessive impact, pay attention to diet, and maintain a reasonable weight');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (4, 'Jogging', 'Asthma', 'Avoid exercising in smoggy weather or extreme temperatures', 'Control breathing and use proper protective gear');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (6, 'Diabolo', 'Eye disease', 'Pay attention to eye rest and protection, do not stare at the diabolo for too long', 'Avoid playing diabolo in dim lighting');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (7, 'Spinning top', 'Hypertension', 'Avoid strenuous activity and do not exercise on cliffs or steep slopes', 'Avoid exercising in environments that are too hot or too cold');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (8, 'Tug of war', 'Heart disease', 'Avoid excessive force and maintain steady breathing', 'Keep team numbers balanced and avoid having too few or too many participants');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (9, 'Volleyball', 'Sprain', 'Warm up properly and keep muscles flexible', 'Avoid hard or slippery surfaces and pay attention to landing posture');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (10, 'Kite flying', 'Allergic rhinitis', 'Avoid flying kites during high pollen seasons or in heavily polluted areas', 'Choose places with fresh air and avoid flying kites in strong winds');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (11, 'Rugby', 'Fracture', 'Use appropriate protective equipment and avoid using overly rigid balls', 'Follow proper game procedures and avoid violent collisions');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (12, 'Swimming', 'Heart disease, arrhythmia, asthma, hypertension', 'Swimming is a full-body exercise, but for people with heart disease, arrhythmia, or asthma, it can worsen symptoms or trigger sudden conditions and should be avoided. People with hypertension can swim moderately but should not overdo it.', 'Pay attention to safety to avoid drowning, and keep the pool clean to prevent infections.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (13, 'Ice skating', 'Fracture, arthritis, heart disease, hypertension', 'Ice skating is a high-risk activity that can cause fractures or arthritis; those with heart disease or hypertension should exercise caution and avoid excessive exertion.', 'Wear protective gear, monitor your physical condition, and avoid overexertion to prevent injury.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (14, 'Mountain climbing', 'Hypertension, heart disease, cerebrovascular disease', 'Choose gentle slopes and avoid steep sections; use proper breathing techniques to prevent oxygen deficiency.', 'Do not overexert yourself, stay hydrated and energized, and pay attention to weather changes; avoid climbing in fog, wind, or thunderstorms.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (15, 'Yoga', 'Spinal disease, knee injury, joint disorders', 'Choose poses suitable for your body condition; breathe naturally and smoothly; perform movements accurately to avoid unnecessary pressure or injury.', 'Avoid practicing on an empty or full stomach; maintain a quiet environment; rest after practice for recovery.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (16, 'Basketball', 'Heart disease, cerebrovascular disease, fracture', 'Maintain moderate activity and avoid overexertion; learn proper techniques to prevent injuries; follow rules and ensure safety during games.', 'Plan training sessions properly; wear protective gear; and maintain a nutritious diet.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (17, 'Tennis', 'Shoulder injury, wrist pain, dislocation', 'Use correct serving and hitting techniques; strengthen muscles to improve flexibility; rest adequately to avoid fatigue.', 'Choose proper rackets and shoes; keep the court dry to prevent slipping; have regular physical checkups.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (18, 'Badminton', 'Muscle strain', 'Increase training intensity gradually, warm up and rest sufficiently, and avoid overexertion.', 'Maintain good nutrition and sleep to prevent fatigue and hypoxia.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (19, 'Trail running', 'Knee pain', 'Choose soft surfaces and strengthen knee muscles.', 'Maintain balanced nutrition and avoid excessive weight loss or oxygen deficiency.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (20, 'Soccer', 'Knee pain', 'Choose soft surfaces and strengthen knee muscles.', 'Maintain balanced nutrition and avoid excessive weight loss or oxygen deficiency.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (21, 'Taekwondo', 'Sprain, strain', 'Increase training gradually, warm up and rest adequately, and avoid overexertion.', 'Maintain good nutrition and sleep to prevent fatigue and hypoxia.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (22, 'Running', 'Knee pain', 'Choose soft surfaces and strengthen knee muscles.', 'Maintain balanced nutrition and avoid excessive weight loss or oxygen deficiency.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (23, 'Diving board', 'Spinal injury', 'Pay attention to technical details, increase training difficulty gradually, and avoid overexertion.', 'Perform targeted spinal strength training and strengthen back muscles.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (24, 'Jump rope bands', 'Muscle strain', 'Increase training intensity gradually, warm up and rest sufficiently, and avoid overexertion.', 'Maintain good nutrition and sleep to prevent fatigue and hypoxia.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (25, 'Jump rope', 'Knee or ankle pain, muscle soreness, sports injury', 'Hold the rope handles and jump alternately within a set time for maximum repetitions.', 'Pay attention to posture and breathing while jumping rope.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (26, 'High jump', 'Sprain, muscle strain, herniated disc, arthritis', 'Also called hurdle high jump; athletes run and jump over an elevated bar, with the height increasing each round; those who fail to clear it are eliminated.', 'Pay attention to runway conditions and proper takeoff and jumping posture.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (27, 'Shuttlecock kicking', 'Sprain, strain', 'Kick the shuttlecock into the air and perform a series of movements; the person who completes the most within a set time wins.', 'Pay attention to shuttlecock quality and weather conditions.');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (28, 'Long-distance running', 'Heart disease, hypertension, asthma, chronic obstructive pulmonary disease', 'An aerobic endurance exercise where participants run a set distance within a specified time.', 'Pay attention to breathing and hydration during running.');
COMMIT;


-- ----------------------------
-- Table structure for j_body
-- ----------------------------
DROP TABLE IF EXISTS `j_body`;
CREATE TABLE `j_body` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `height` float DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `bloodSugar` float DEFAULT NULL,
  `bloodPressure` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `bloodLipid` float DEFAULT NULL,
  `heart_rate` decimal(10,0) DEFAULT NULL,
  `vision` int DEFAULT NULL,
  `sleep_duration` decimal(10,0) DEFAULT NULL,
  `sleep_quality` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `smoking` tinyint(1) DEFAULT NULL,
  `drinking` tinyint(1) DEFAULT NULL,
  `exercise` tinyint(1) DEFAULT NULL,
  `food_types` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `water_consumption` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_body
-- ----------------------------
BEGIN;
INSERT INTO `j_body` (`id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`) VALUES (33, '杭州水果捞', 19, '男', 171, 120, 3.9, '100', 0.4, 70, 0, 6, '好', 1, 1, 1, '肉类', 200);
COMMIT;

-- ----------------------------
-- Table structure for j_body_notes
-- ----------------------------
DROP TABLE IF EXISTS `j_body_notes`;
CREATE TABLE `j_body_notes` (
  `notes_id` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `height` float DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `bloodSugar` float DEFAULT NULL,
  `bloodPressure` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `bloodLipid` float DEFAULT NULL,
  `heart_rate` decimal(10,0) DEFAULT NULL,
  `vision` int DEFAULT NULL,
  `sleep_duration` decimal(10,0) DEFAULT NULL,
  `sleep_quality` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `smoking` tinyint(1) DEFAULT NULL,
  `drinking` tinyint(1) DEFAULT NULL,
  `exercise` tinyint(1) DEFAULT NULL,
  `food_types` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `water_consumption` decimal(10,0) DEFAULT NULL,
  `Date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notes_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_body_notes
-- ----------------------------
BEGIN;
INSERT INTO `j_body_notes` (`notes_id`, `id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`, `Date`) VALUES (46, 33, '杭州水果捞', 18, '男', 180, 130, 10, '110', 10, 90, 0, 8, '好', 1, 1, 1, '豆类', 100, '2025-07-05 18:42:20');
INSERT INTO `j_body_notes` (`notes_id`, `id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`, `Date`) VALUES (47, 33, '杭州水果捞', 19, '男', 171, 120, 3.9, '100', 0.4, 70, 0, 6, '好', 1, 1, 1, '肉类', 200, '2025-07-05 19:27:22');
COMMIT;

-- ----------------------------
-- Table structure for j_menu
-- ----------------------------
DROP TABLE IF EXISTS `j_menu`;
CREATE TABLE `j_menu` (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `redirect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `is_leaf` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `hidden` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_menu
-- ----------------------------
BEGIN;
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (1, 'Layout', '/sys', '/sys/user', 'sysManage', '系统管理', 'el-icon-s-order\r\n', 0, 'N', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (2, 'sys/user', 'user', NULL, 'userList', '用户管理', 'user', 1, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (3, 'sys/role', 'role', NULL, 'roleList', '角色管理', 'el-icon-user', 1, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (4, 'Layout', '/test', '/test/test1', 'test', '系统功能', 'form', 0, 'N', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (5, 'test/test1', 'test1', '', 'test1', '身体信息上传', 'form', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (6, 'test/test2', 'test2', '', 'test2', '健康评估', 'el-icon-first-aid-kit', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (7, 'test/test3', 'test3', '', 'test3', '运动知识', 'el-icon-s-opportunity\r\n', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (8, 'sys/sportDetails', 'sportDetails', '', 'sportDetails', '运动知识管理', 'el-icon-s-opportunity\r\nel-icon-s-custom', 1, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (9, 'sys/DetailsManage', 'DetailsManage', NULL, 'DetailsManage', '运动详情管理', 'el-icon-s-check', 1, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (10, 'sys/userBodyManage', 'userBodyManage', NULL, 'userBodyManage', '用户身体信息管理', 'el-icon-s-check', 1, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (11, 'test/bodyManage', 'bodyManage', NULL, 'bodyManage', '身体信息管理', 'el-icon-s-check', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (12, 'ai/assistant', 'assistant', NULL, '\'aiAssistant', 'AI助手', 'el-icon-s-help', 0, 'Y', 0);
COMMIT;

-- ----------------------------
-- Table structure for j_role
-- ----------------------------
DROP TABLE IF EXISTS `j_role`;
CREATE TABLE `j_role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_role
-- ----------------------------
BEGIN;
INSERT INTO `j_role` (`role_id`, `role_name`, `role_desc`) VALUES (1, 'admin', '超级管理员');
INSERT INTO `j_role` (`role_id`, `role_name`, `role_desc`) VALUES (3, 'normal', '普通用户');
COMMIT;

-- ----------------------------
-- Table structure for j_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `j_role_menu`;
CREATE TABLE `j_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `menu_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (179, 3, 4);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (180, 3, 5);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (181, 3, 6);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (182, 3, 7);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (183, 3, 11);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (184, 1, 1);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (185, 1, 2);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (186, 1, 3);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (187, 1, 8);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (188, 1, 9);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (189, 1, 10);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (190, 1, 4);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (191, 1, 5);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (192, 1, 6);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (193, 1, 7);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (194, 1, 11);
COMMIT;

-- ----------------------------
-- Table structure for j_user
-- ----------------------------
DROP TABLE IF EXISTS `j_user`;
CREATE TABLE `j_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_user
-- ----------------------------
BEGIN;
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (1, 'java1234', '123456', '13343243242', NULL, 'super@aliyun.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (2, 'zhangsan', '123456', NULL, NULL, 'zhangsan@gmail.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (3, 'lisi', '123456', NULL, NULL, 'lisi@gmail.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (4, 'wangwu', '123456', NULL, NULL, 'wangwu@gmail.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (5, 'zhaoer', '123456', NULL, NULL, 'zhaoer@gmail.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (6, 'songliu', '123456', '4322432434', NULL, 'songliu@gmail.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (13, 'Alice', '123456', '234532111', NULL, 'alice@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (14, 'Bob', '123456', '13564736363', NULL, 'bob@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (15, 'Charlie', '123456', '25323626', NULL, 'charlie@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (16, 'David1', '123456', '145256264', NULL, 'david@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (17, 'Emma', '123456', '152334445', NULL, 'emma@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (18, 'Frank', '123456', '1566543646', NULL, 'frank@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (19, 'Grace', '123456', '1435253535', NULL, 'grace@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (20, 'Henry', '123456', '15325363463', NULL, 'henry@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (21, 'Isabella', '123456', '15353435', NULL, 'isabella@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (22, 'Jacob', '123456', '114523424', NULL, 'jacob@example.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (32, 'text', '123456', '11111111111', NULL, '1111111111@163.com', 'https://bpic.51yuansu.com/pic2/cover/00/35/43/58119f542530c_610.jpg', 1);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (33, 'admin', '123456', '18666666666', NULL, '123@qq.com', 'https://img2.baidu.com/it/u=3618236253,1028428296&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', 0);
COMMIT;

-- ----------------------------
-- Table structure for j_user_role
-- ----------------------------
DROP TABLE IF EXISTS `j_user_role`;
CREATE TABLE `j_user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_user_role
-- ----------------------------
BEGIN;
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (12, 11, 3);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (13, 12, 1);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (45, 54, 3);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (46, 55, 3);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (47, 56, 3);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (53, 29, 3);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (56, 31, 3);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (61, 33, 1);
COMMIT;

-- ----------------------------
-- Table structure for sport_info
-- ----------------------------
DROP TABLE IF EXISTS `sport_info`;
CREATE TABLE `sport_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sport_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `suitable_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `suitable_heart_rate` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `suitable_frequency` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `recommended_speed` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sport_info
-- ----------------------------
BEGIN;
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (1, 'Trail Running', '33 minutes', '130-160 bpm', '3-4 times/week', '8 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (3, 'Yoga', '60 minutes', '90-110 bpm', '1-2 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (4, 'Badminton', '120 minutes', '130-150 bpm', '3-4 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (5, 'Jogging', '60 minutes', '130-150 bpm', '3-4 times/week', '8 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (10, 'Running', '45 minutes', '140-160 bpm', '4-5 times/week', '10 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (13, 'Tai Chi', '60 minutes', '80-100 bpm', '3-4 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (14, 'Soccer', '90 minutes', '130-150 bpm', '2-3 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (15, 'Fast Running', '30 minutes', '130-150 bpm', '5-6 times/week', '13 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (16, 'Mountain Climbing', '60 minutes', '130-150 bpm', '2-3 times/week', '5 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (18, 'Swimming', '45 minutes', '120-140 bpm', '3-4 times/week', '2.5 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (19, 'Basketball', '120 minutes', '130-150 bpm', '1-2 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (20, 'Volleyball', '1 hour', '100-120 bpm', '2-3 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (21, 'Tennis', '1.5 hours', '100-130 bpm', '2-3 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (22, 'Ice Skating', '1 hour', '100-120 bpm', '2-3 times/week', '10-15 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (23, 'Long-distance Running', '30 minutes - 1 hour', '120-160 bpm', '3-4 times/week', '10-15 km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (24, 'Rugby', '1.5-2 hours', '100-120 bpm', '2-3 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (25, 'Taekwondo', '1 hour', '120-140 bpm', '3-4 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (26, 'Gymnastics', '1-2 hours', '100-120 bpm', '3-4 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (27, 'High Jump', '1 hour', '120-160 bpm', '2-3 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (28, 'Diving Board', '1 hour', '120-160 bpm', '2-3 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (29, 'Jump Rope Bands', '30 minutes - 1 hour', '100-120 bpm', '3-4 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (30, 'Jump Rope', '30 minutes - 1 hour', '120-160 bpm', '3-4 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (31, 'Shuttlecock Kicking', '30 minutes - 1 hour', '100-120 bpm', '3-4 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (32, 'Kite Flying', '1-2 hours', '80-100 bpm', '1-2 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (33, 'Tug of War', '30 minutes - 1 hour', '100-120 bpm', '2-3 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (34, 'Spinning Top', '30 minutes - 1 hour', '80-100 bpm', '1-2 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (35, 'Diabolo', '30 minutes - 1 hour', '80-100 bpm', '1-2 times/week', 'None');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (36, 'Beanbag Throwing', '30 minutes - 1 hour', '100-120 bpm', '2-3 times/week', 'None');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
