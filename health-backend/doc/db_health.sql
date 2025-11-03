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
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (1, '体操', '心脏病', '适度运动，不要剧烈运动，保持心情愉快', '避免在气温过高或过低的环境下运动');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (2, '太极拳', '高血压', '轻柔的动作，缓慢而稳定的呼吸', '保持心情平稳，不要过度紧张');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (3, '快跑', '关节炎', '选择跑步道或软质地面进行，适当热身', '避免过度的冲击力，注意饮食，保持合理体重');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (4, '慢跑', '哮喘', '避免在雾霾天气、气温过高或过低的环境下运动', '控制呼吸，使用合适的防护装备');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (6, '抖空竹', '眼疾', '注意眼睛的休息和保护，不要盯着空竹太久', '避免在光线昏暗的环境下玩抖空竹');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (7, '抽陀螺', '高血压', '避免剧烈运动，不要在悬崖陡坡等高度场所运动', '避免在气温过高或过低的环境下运动');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (8, '拔河', '心脏病', '注意不要过度用力，保持呼吸稳定', '人数相对平衡，避免人数不足或过多');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (9, '排球', '扭伤', '注意热身，保持肌肉灵活', '避免在硬地面和湿滑地面上运动，注意落地姿势');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (10, '放风筝', '过敏性鼻炎', '避免在花粉高发季节和污染严重的地区放风筝', '选择空气清新的场地，不要在风力过大的环境下放风筝');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (11, '橄榄球', '骨折', '选择合适的防护装备，不要使用过度生硬的球', '规范比赛流程，避免激烈对抗');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (12, '游泳', '心脏病、心律失常、哮喘、高血压', '游泳是一项全身性运动，但对于患有心脏病、心律失常、哮喘等疾病的人来说，游泳容易加重病情，甚至引发突发病情，因此应该避免。而患有高血压的人可以适当游泳，但是游泳时间和强度不宜过大。', '游泳时一定要注意安全，避免溺水事故的发生；同时注意保持游泳池的清洁卫生，避免感染泳池病毒。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (13, '滑冰', '骨折、关节炎、心脏病、高血压', '滑冰是一项高风险运动，容易导致骨折、关节炎等损伤，对于患有心脏病、高血压等疾病的人来说，也需要注意保护自己，避免剧烈运动。', '滑冰时要穿好防护装备，注意自己的身体状况，避免过度运动造成身体损伤。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (14, '爬山', '高血压、心脏病、脑血管疾病', '选择较平缓的山路，避免过于陡峭的路段；掌握正确的呼吸方法，避免缺氧；', '切勿贪多嚼不烂，随时可以补充水分和能量；注意天气变化，避免在大雾、大风、雷雨天气攀登；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (15, '瑜伽', '脊椎病、膝盖受伤、关节病变', '选择适合自己身体情况的瑜伽动作；呼吸要自然流畅，不要憋气或过度呼吸；动作要准确，避免造成不必要的压力或伤害；', '练习前应注意饮食、排便，不宜在空腹或饱腹时练习；练习时注意周围环境，保持安静的氛围；练习后应适当休息，以便身体恢复；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (16, '篮球', '心脏病、脑血管疾病、骨折', '保持适度的身体活动，不要过度用力；掌握正确的篮球技巧，避免不必要的伤害；在比赛中要遵守规则，注意安全；', '合理安排练球时间和强度，不要过度训练；练球时应注意防护措施，如佩戴护具等；注意饮食，保证充足的营养摄入；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (17, '网球', '肩部受伤、腕关节疼痛、脱臼', '掌握正确的发球姿势和击球技巧；加强肌肉的训练，提高关节的灵活性；注意休息，避免过度疲劳；', '选择合适的球拍和鞋子，避免不必要的伤害；保持场地干燥，防止滑倒；定期进行身体检查，及时发现问题；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (18, '羽毛球', '肌肉劳损', '逐渐增加训练量，充足的热身与休息，避免过度用力', '注意营养、充足的睡眠，避免疲劳、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (19, '越野跑', '膝盖疼痛', '选择软质路面，加强膝关节周围肌肉的锻炼', '注意营养、饮食的平衡，避免过度减重、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (20, '足球', '膝盖疼痛', '选择软质路面，加强膝关节周围肌肉的锻炼', '注意营养、饮食的平衡，避免过度减重、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (21, '跆拳道', '扭伤、拉伤', '逐渐增加训练量，充足的热身与休息，避免过度用力', '注意营养、充足的睡眠，避免疲劳、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (22, '跑步', '膝盖疼痛', '选择软质路面，加强膝关节周围肌肉的锻炼', '注意营养、饮食的平衡，避免过度减重、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (23, '跳板', '脊椎损伤', '注意技术细节，逐渐增加训练难度，避免过度用力', '可选择针对脊柱的力量训练，加强腰背肌群的锻炼');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (24, '跳皮筋', '肌肉劳损', '逐渐增加训练量，充足的热身与休息，避免过度用力', '注意营养、充足的睡眠，避免疲劳、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (25, '跳绳', '膝盖或脚踝疼痛、肌肉酸痛、运动损伤', '手持跳绳，双脚交替跳跃，以规定时间内跳跃次数最多者获胜。', '需要注意跳绳时的姿势和呼吸。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (26, '跳高', '扭伤、肌肉拉伤、腰椎间盘突出、关节炎', '又称跳高栏，运动员在助跑后跳过竞赛高度悬挂的水平杠，以完成跳高动作，高度逐渐升高，未能过去的选手则被淘汰。', '需要注意跳高时的跑道、助跑和跳跃姿势。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (27, '踢毽子', '扭伤、拉伤', '将毽子踢起来并在空中进行一系列动作，规定时间内完成动作最多的人获胜。', '需要注意毽子的质量和天气情况。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (28, '长跑', '心脏病、高血压、哮喘、慢性阻塞性肺疾病', '一项有氧耐力运动，比赛时要跑过规定的路程，规定时间内到达终点的人获胜。', '需要注意长跑时的呼吸和水分补给。');
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
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (1, '越野跑', '33分钟', '130-160次/分', '3-4次/周', '8km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (3, '瑜伽', '60分钟', '90-110次/分', '1-2次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (4, '羽毛球', '120分钟', '130-150次/分', '3-4次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (5, '慢跑', '60分钟', '130-150次/分', '3-4次/周', '8km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (10, '跑步', '45分钟', '140-160次/分', '4-5次/周', '10km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (13, '太极拳', '60分钟', '80-100次/分', '3-4次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (14, '足球', '90分钟', '130-150次/分', '2-3次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (15, '快跑', '30分钟', '130-150次/分', '5-6次/周', '13km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (16, '爬山', '60分钟', '130-150次/分', '2-3次/周', '5km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (18, '游泳', '45分钟', '120-140次/分', '3-4次/周', '2.5km/h');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (19, '篮球', '120分钟', '130-150次/分', '1-2次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (20, '排球', '1小时', '100-120次/分钟', '2-3次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (21, '网球', '1.5小时', '100-130次/分钟', '2-3次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (22, '滑冰', '1小时', '100-120次/分钟', '2-3次/周', '10-15千米/小时');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (23, '长跑', '30分钟-1小时', '120-160次/分钟', '3-4次/周', '10-15千米/小时');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (24, '橄榄球', '1.5-2小时', '100-120次/分钟', '2-3次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (25, '跆拳道', '1小时', '120-140次/分钟', '3-4次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (26, '体操', '1-2小时', '100-120次/分钟', '3-4次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (27, '跳高', '1小时', '120-160次/分钟', '2-3次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (28, '跳板', '1小时', '120-160次/分钟', '2-3次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (29, '跳皮筋', '30分钟-1小时', '100-120次/分钟', '3-4次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (30, '跳绳', '30分钟-1小时', '120-160次/分钟', '3-4次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (31, '踢毽子', '30分钟-1小时', '100-120次/分钟', '3-4次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (32, '放风筝', '1-2小时', '80-100次/分钟', '1-2次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (33, '拔河', '30分钟-1小时', '100-120次/分钟', '2-3次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (34, '抽陀螺', '30分钟-1小时', '80-100次/分钟', '1-2次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (35, '抖空竹', '30分钟-1小时', '80-100次/分钟', '1-2次/周', '无');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (36, '扔沙袋', '30分钟-1小时', '100-120次/分钟', '2-3次/周', '无');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
