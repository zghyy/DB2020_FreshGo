/*
 Navicat Premium Data Transfer

 Source Server         : ubuntu_mysql
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 127.0.0.1:3306
 Source Schema         : FreshGo

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 14/07/2020 22:23:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for LTpromotion
-- ----------------------------
DROP TABLE IF EXISTS `LTpromotion`;
CREATE TABLE `LTpromotion` (
  `ltp_id` int(11) NOT NULL AUTO_INCREMENT,
  `ltp_order` int(11) NOT NULL,
  `g_id` int(11) NOT NULL,
  `g_name` varchar(20) NOT NULL,
  `ltp_price` double(10,2) NOT NULL,
  `ltp_count` int(11) NOT NULL,
  `ltp_start_date` datetime NOT NULL,
  `ltp_end_date` datetime NOT NULL,
  PRIMARY KEY (`ltp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of LTpromotion
-- ----------------------------
BEGIN;
INSERT INTO `LTpromotion` VALUES (1, 1, 2, '猪肉', 15.00, 3, '2020-01-01 00:00:00', '2021-01-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_name` varchar(20) NOT NULL,
  `a_pwd` varchar(20) NOT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES (1, '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `u_id` int(11) NOT NULL,
  `g_id` int(11) NOT NULL,
  `com_msg` varchar(30) NOT NULL,
  `com_date` datetime NOT NULL,
  `com_star` int(11) NOT NULL,
  `com_pic` longblob,
  `com_order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comments
-- ----------------------------
BEGIN;
INSERT INTO `comments` VALUES (1, 1, '111', '2020-07-14 22:22:02', 5, NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `cp_id` int(11) NOT NULL AUTO_INCREMENT,
  `cp_order` int(11) NOT NULL,
  `cp_desc` varchar(30) NOT NULL,
  `cp_amount` double(11,0) NOT NULL,
  `cp_discount` double(11,0) NOT NULL,
  `cp_start_date` datetime NOT NULL,
  `cp_end_date` datetime NOT NULL,
  PRIMARY KEY (`cp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coupon
-- ----------------------------
BEGIN;
INSERT INTO `coupon` VALUES (1, 1, '40-5', 40, 5, '2020-01-01 00:00:00', '2021-01-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for f_g_connect
-- ----------------------------
DROP TABLE IF EXISTS `f_g_connect`;
CREATE TABLE `f_g_connect` (
  `fdc_id` int(11) NOT NULL AUTO_INCREMENT,
  `fdc_order` int(11) NOT NULL,
  `g_id` int(11) NOT NULL,
  `g_name` varchar(255) NOT NULL,
  `fd_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`fdc_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of f_g_connect
-- ----------------------------
BEGIN;
INSERT INTO `f_g_connect` VALUES (1, 1, 3, '土豆', 1, '2020-01-01 00:00:00', '2020-01-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for fresh_type
-- ----------------------------
DROP TABLE IF EXISTS `fresh_type`;
CREATE TABLE `fresh_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_order` int(11) NOT NULL,
  `type_name` varchar(20) NOT NULL,
  `type_desc` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fresh_type
-- ----------------------------
BEGIN;
INSERT INTO `fresh_type` VALUES (1, 1, '蔬菜', '蔬菜');
INSERT INTO `fresh_type` VALUES (2, 2, '肉类', '肉类');
COMMIT;

-- ----------------------------
-- Table structure for full_discount_msg
-- ----------------------------
DROP TABLE IF EXISTS `full_discount_msg`;
CREATE TABLE `full_discount_msg` (
  `fd_id` int(11) NOT NULL AUTO_INCREMENT,
  `fd_order` int(11) NOT NULL,
  `fd_desc` varchar(30) NOT NULL,
  `fd_needcount` int(11) NOT NULL,
  `fd_data` double(11,0) NOT NULL,
  `fd_start_date` datetime NOT NULL,
  `fd_end_date` datetime NOT NULL,
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of full_discount_msg
-- ----------------------------
BEGIN;
INSERT INTO `full_discount_msg` VALUES (1, 1, '2-8', 2, 8, '2020-01-01 00:00:00', '2021-01-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for goods_msg
-- ----------------------------
DROP TABLE IF EXISTS `goods_msg`;
CREATE TABLE `goods_msg` (
  `g_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL,
  `g_order` int(11) NOT NULL,
  `g_name` varchar(20) NOT NULL,
  `g_price` double(10,2) NOT NULL,
  `g_vipprice` double(10,2) NOT NULL,
  `g_count` int(11) NOT NULL,
  `g_specifications` varchar(50) DEFAULT NULL,
  `g_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_msg
-- ----------------------------
BEGIN;
INSERT INTO `goods_msg` VALUES (1, 2, 1, '牛肉', 40.00, 38.00, 3, '', '');
INSERT INTO `goods_msg` VALUES (2, 2, 2, '猪肉', 20.00, 19.00, 3, '', '');
INSERT INTO `goods_msg` VALUES (3, 1, 3, '土豆', 5.00, 4.00, 3, '', '');
COMMIT;

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) NOT NULL,
  `locate_order` int(11) NOT NULL,
  `province` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `area` varchar(30) NOT NULL,
  `location_desc` varchar(50) NOT NULL,
  `linkman` varchar(10) NOT NULL,
  `phonenumber` decimal(11,0) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of location
-- ----------------------------
BEGIN;
INSERT INTO `location` VALUES (1, 1, 1, '浙江省', '杭州市', '拱墅区', '浙大城市学院', '31801201', 31801201);
COMMIT;

-- ----------------------------
-- Table structure for menu_detail
-- ----------------------------
DROP TABLE IF EXISTS `menu_detail`;
CREATE TABLE `menu_detail` (
  `m_id` int(11) NOT NULL,
  `g_id` int(11) NOT NULL,
  `g_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_detail
-- ----------------------------
BEGIN;
INSERT INTO `menu_detail` VALUES (1, 3, '土豆');
INSERT INTO `menu_detail` VALUES (1, 1, '牛肉');
COMMIT;

-- ----------------------------
-- Table structure for menu_msg
-- ----------------------------
DROP TABLE IF EXISTS `menu_msg`;
CREATE TABLE `menu_msg` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT,
  `m_order` int(11) NOT NULL,
  `m_name` varchar(20) NOT NULL,
  `m_desc` varchar(50) DEFAULT NULL,
  `m_step` varchar(100) DEFAULT NULL,
  `m_pic` longblob,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_msg
-- ----------------------------
BEGIN;
INSERT INTO `menu_msg` VALUES (1, 1, '土豆牛肉', '土豆牛肉', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `o_id` int(11) NOT NULL AUTO_INCREMENT,
  `o_order` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `o_old_price` double(10,2) NOT NULL,
  `o_new_price` double(10,2) NOT NULL,
  `o_coupon` int(11) DEFAULT NULL,
  `o_gettime` datetime NOT NULL,
  `o_status` varchar(20) NOT NULL,
  `location_detail` varchar(255) NOT NULL,
  PRIMARY KEY (`o_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
BEGIN;
INSERT INTO `orders` VALUES (1, 1, 1, 1, 10.00, 8.00, NULL, '2020-07-14 22:19:56', '下单', '浙江省 杭州市 拱墅区 浙大城市学院');
INSERT INTO `orders` VALUES (2, 2, 1, 1, 40.00, 30.00, NULL, '2020-07-14 22:20:26', '下单', '浙江省 杭州市 拱墅区 浙大城市学院');
INSERT INTO `orders` VALUES (3, 3, 1, 1, 8.00, 6.40, NULL, '2020-07-14 22:20:52', '退货', '浙江省 杭州市 拱墅区 浙大城市学院');
INSERT INTO `orders` VALUES (4, 4, 1, 1, 76.00, 71.00, 1, '2020-07-14 22:21:34', '已评价', '浙江省 杭州市 拱墅区 浙大城市学院');
COMMIT;

-- ----------------------------
-- Table structure for orders_detail
-- ----------------------------
DROP TABLE IF EXISTS `orders_detail`;
CREATE TABLE `orders_detail` (
  `od_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `goods_count` int(11) NOT NULL,
  `goods_price` double(10,2) NOT NULL,
  `order_id` int(11) NOT NULL,
  `fd_data` int(11) DEFAULT NULL,
  `fulld_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`od_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders_detail
-- ----------------------------
BEGIN;
INSERT INTO `orders_detail` VALUES (1, 3, 2, 4.00, 1, 8, 1);
INSERT INTO `orders_detail` VALUES (2, 2, 2, 15.00, 2, NULL, NULL);
INSERT INTO `orders_detail` VALUES (3, 3, 2, 3.20, 3, 8, 1);
INSERT INTO `orders_detail` VALUES (4, 1, 2, 38.00, 4, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
  `pur_id` int(11) NOT NULL,
  `a_id` int(11) NOT NULL,
  `g_id` int(11) NOT NULL,
  `g_name` varchar(20) NOT NULL,
  `pur_num` int(11) NOT NULL,
  `pur_status` varchar(20) NOT NULL,
  PRIMARY KEY (`pur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purchase
-- ----------------------------
BEGIN;
INSERT INTO `purchase` VALUES (1, 1, 1, '牛肉', 5, '入库');
INSERT INTO `purchase` VALUES (2, 1, 2, '猪肉', 5, '入库');
INSERT INTO `purchase` VALUES (3, 1, 3, '土豆', 5, '入库');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_name` varchar(20) NOT NULL,
  `u_sex` varchar(10) NOT NULL,
  `u_pwd` varchar(20) NOT NULL,
  `u_phone` decimal(13,0) NOT NULL,
  `u_email` varchar(30) NOT NULL,
  `u_city` varchar(20) NOT NULL,
  `u_createdate` datetime NOT NULL,
  `u_isvip` varchar(10) NOT NULL,
  `u_vip_end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES (1, 'test1', '男', 'test1', 1, '1', '1', '2020-07-14 22:19:01', 'y', '2020-08-13 22:20:41');
COMMIT;

-- ----------------------------
-- View structure for fulldiscountandgoodsmsg
-- ----------------------------
DROP VIEW IF EXISTS `fulldiscountandgoodsmsg`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `fulldiscountandgoodsmsg` AS select `gm`.`g_id` AS `g_id`,`fdm`.`fd_id` AS `fd_id`,`fdm`.`fd_data` AS `fd_data`,`fdm`.`fd_needcount` AS `fd_needcount`,`fdm`.`fd_start_date` AS `start_date`,`fdm`.`fd_end_date` AS `end_date` from ((`goods_msg` `gm` join `full_discount_msg` `fdm`) join `f_g_connect` `fgc`) where ((`gm`.`g_id` = `fgc`.`g_id`) and (`fdm`.`fd_id` = `fgc`.`fd_id`));

-- ----------------------------
-- View structure for locationmsg
-- ----------------------------
DROP VIEW IF EXISTS `locationmsg`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `locationmsg` AS select `location`.`location_id` AS `l_id`,concat(`location`.`province`,' ',`location`.`city`,' ',`location`.`area`,' ',`location`.`location_desc`) AS `l_detail` from `location`;

-- ----------------------------
-- View structure for menugoodsdetail
-- ----------------------------
DROP VIEW IF EXISTS `menugoodsdetail`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `menugoodsdetail` AS select `mm`.`m_id` AS `m_id`,`mm`.`m_name` AS `m_name`,`mm`.`m_desc` AS `desc`,`gm`.`g_id` AS `g_id`,`gm`.`g_name` AS `gname` from ((`menu_msg` `mm` join `menu_detail` `md`) join `goods_msg` `gm`) where ((`mm`.`m_id` = `md`.`m_id`) and (`gm`.`g_id` = `md`.`g_id`));

SET FOREIGN_KEY_CHECKS = 1;
