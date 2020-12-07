-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.5.5-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 easybuy 的数据库结构
DROP DATABASE IF EXISTS `easybuy`;
CREATE DATABASE IF NOT EXISTS `easybuy` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `easybuy`;

-- 导出  表 easybuy.easybuy_comment 结构
DROP TABLE IF EXISTS `easybuy_comment`;
CREATE TABLE IF NOT EXISTS `easybuy_comment` (
  `EC_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EC_CONTENT` varchar(200) DEFAULT NULL,
  `EC_CREATE_TIME` varchar(10) DEFAULT NULL,
  `EC_REPLY` varchar(200) DEFAULT NULL,
  `EC_REPLY_TIME` varchar(10) DEFAULT NULL,
  `EC_NICK_NAME` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`EC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1224 DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.easybuy_comment 的数据：~5 rows (大约)
DELETE FROM `easybuy_comment`;
/*!40000 ALTER TABLE `easybuy_comment` DISABLE KEYS */;
INSERT INTO `easybuy_comment` (`EC_ID`, `EC_CONTENT`, `EC_CREATE_TIME`, `EC_REPLY`, `EC_REPLY_TIME`, `EC_NICK_NAME`) VALUES
	(655, '刚订了台IPod，很是期待啊', '2013-03-22', '货已发出，请注意收货时开箱检查货物是否有问题!', '2013-05-15', '小乖'),
	(680, '佳能D50现在可以多长时间发货呢', '2012-12-24', '一般在订单确认后的第3天发货', '2013-06-24', '无极'),
	(1217, '最近买的化妆品感觉很好用，谢谢卖家', '2013-05-21', '不客气，欢迎下次光临', '2013-08-14', '大家米'),
	(1222, 'aa', '2013-08-14', NULL, NULL, '管理员'),
	(1223, '012345678901234567890123456789', '2013-08-14', NULL, NULL, '管理员');
/*!40000 ALTER TABLE `easybuy_comment` ENABLE KEYS */;

-- 导出  表 easybuy.easybuy_news 结构
DROP TABLE IF EXISTS `easybuy_news`;
CREATE TABLE IF NOT EXISTS `easybuy_news` (
  `EN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EN_TITLE` varchar(40) DEFAULT NULL,
  `EN_CONTENT` varchar(1000) DEFAULT NULL,
  `EN_CREATE_TIME` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`EN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=676 DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.easybuy_news 的数据：~9 rows (大约)
DELETE FROM `easybuy_news`;
/*!40000 ALTER TABLE `easybuy_news` DISABLE KEYS */;
INSERT INTO `easybuy_news` (`EN_ID`, `EN_TITLE`, `EN_CONTENT`, `EN_CREATE_TIME`) VALUES
	(531, '会员特惠月开始了', '会员特惠月开始了', '2010-12-22'),
	(597, '迎双旦促销大酬宾', '迎双旦促销大酬宾', '2010-12-24'),
	(649, '加入会员，赢千万大礼包', '加入会员，赢千万大礼包', '2010-12-22'),
	(650, '新年不夜天，通宵也是开张了', '新年不夜天，通宵也是开张了', '2011-05-22'),
	(651, '积分兑换开始了', '积分兑换开始了', '2010-12-22'),
	(653, '团购阿迪1折起', '团购阿迪1折起', '2010-12-22'),
	(654, '配货通知', '配货通知', '2013-11-22'),
	(664, '最新酷睿笔记本', 'IBME系列全场促销中，最新酷睿双核处理器，保证CPU更高效的运转。', '2013-08-05'),
	(675, 'aa', '012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789', '2013-08-14');
/*!40000 ALTER TABLE `easybuy_news` ENABLE KEYS */;

-- 导出  表 easybuy.easybuy_order 结构
DROP TABLE IF EXISTS `easybuy_order`;
CREATE TABLE IF NOT EXISTS `easybuy_order` (
  `EO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EO_USER_ID` varchar(10) DEFAULT NULL,
  `EO_USER_NAME` varchar(20) DEFAULT NULL,
  `EO_USER_ADDRESS` varchar(200) DEFAULT NULL,
  `EO_CREATE_TIME` timestamp NULL DEFAULT NULL,
  `EO_COST` double DEFAULT NULL,
  `EO_STATUS` int(11) DEFAULT NULL,
  `EO_TYPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`EO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.easybuy_order 的数据：~7 rows (大约)
DELETE FROM `easybuy_order`;
/*!40000 ALTER TABLE `easybuy_order` DISABLE KEYS */;
INSERT INTO `easybuy_order` (`EO_ID`, `EO_USER_ID`, `EO_USER_NAME`, `EO_USER_ADDRESS`, `EO_CREATE_TIME`, `EO_COST`, `EO_STATUS`, `EO_TYPE`) VALUES
	(8, 'admin', '管理员', '北京市海淀区成府路207号', '2013-05-23 00:00:00', 100, 4, 1),
	(9, 'admin', '管理员', '北京市海淀区成府路207号', '2013-05-23 00:00:00', 200, 4, 1),
	(10, 'admin', '管理员', '北京市海淀区成府路207号', '2013-05-23 00:00:00', 300, 3, 1),
	(11, 'admin', '管理员', '北京市海淀区成府路207号', '2013-05-24 00:00:00', 830, 4, 1),
	(12, 'admin', '管理员', '河北', '2013-05-24 08:35:10', 2288, 1, 1),
	(13, 'admin', '管理员', '北京市海淀区成府路207号', '2020-10-11 17:00:16', 100, 1, 1),
	(14, 'admin', '管理员', '北京市海淀区成府路207号', '2020-10-11 17:04:37', 200, 1, 1);
/*!40000 ALTER TABLE `easybuy_order` ENABLE KEYS */;

-- 导出  表 easybuy.easybuy_order_detail 结构
DROP TABLE IF EXISTS `easybuy_order_detail`;
CREATE TABLE IF NOT EXISTS `easybuy_order_detail` (
  `EOD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EO_ID` int(11) DEFAULT NULL,
  `EP_ID` int(11) DEFAULT NULL,
  `EOD_QUANTITY` int(11) DEFAULT NULL,
  `EOD_COST` double DEFAULT NULL,
  PRIMARY KEY (`EOD_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=808 DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.easybuy_order_detail 的数据：~8 rows (大约)
DELETE FROM `easybuy_order_detail`;
/*!40000 ALTER TABLE `easybuy_order_detail` DISABLE KEYS */;
INSERT INTO `easybuy_order_detail` (`EOD_ID`, `EO_ID`, `EP_ID`, `EOD_QUANTITY`, `EOD_COST`) VALUES
	(800, 8, 591, 1, 100),
	(801, 9, 592, 1, 200),
	(802, 10, 593, 1, 300),
	(803, 11, 639, 2, 830),
	(804, 12, 643, 1, 300),
	(805, 12, 660, 1, 1988),
	(806, 13, 591, 1, 100),
	(807, 14, 592, 1, 200);
/*!40000 ALTER TABLE `easybuy_order_detail` ENABLE KEYS */;

-- 导出  表 easybuy.easybuy_product 结构
DROP TABLE IF EXISTS `easybuy_product`;
CREATE TABLE IF NOT EXISTS `easybuy_product` (
  `EP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EP_NAME` varchar(20) DEFAULT NULL,
  `EP_DESCRIPTION` varchar(100) DEFAULT NULL,
  `EP_PRICE` double DEFAULT NULL,
  `EP_STOCK` int(11) DEFAULT NULL,
  `EPC_ID` int(11) DEFAULT NULL,
  `EPC_CHILD_ID` int(11) DEFAULT NULL,
  `EP_FILE_NAME` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`EP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=700 DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.easybuy_product 的数据：~23 rows (大约)
DELETE FROM `easybuy_product`;
/*!40000 ALTER TABLE `easybuy_product` DISABLE KEYS */;
INSERT INTO `easybuy_product` (`EP_ID`, `EP_NAME`, `EP_DESCRIPTION`, `EP_PRICE`, `EP_STOCK`, `EPC_ID`, `EPC_CHILD_ID`, `EP_FILE_NAME`) VALUES
	(591, '画册', '画册', 100, 9983, 542, 546, '591.png'),
	(592, '饭盒', '韩国风格', 200, 1978, 626, 626, '592.jpg'),
	(593, '护肤王', '女士专用', 300, 29984, 545, 548, '596.jpg'),
	(601, '奶粉', '三元', 98, 189, 626, 626, '601.jpg'),
	(639, '旅行包', '旅行包', 415, 7, 545, 628, '639.jpg'),
	(643, '项链', '饰品', 300, 2990, 545, 548, '643.jpg'),
	(645, '丝袜', '女士', 50, 500, 632, 638, '645.jpg'),
	(660, '显示器', '三星', 1988, 1999, 628, 628, '660.jpg'),
	(663, '液晶屏', '纯平液晶显示器', 590, 2, 628, 628, '663.jpg'),
	(667, '阿甘正传', '阿甘正传', 50, 120, 542, 631, '667.jpg'),
	(669, '哈利波特', '哈利波特', 78, 90, 542, 631, '669.jpg'),
	(680, '裤子', '裤子', 45, 55, 632, 638, '680.jpg'),
	(681, '电饼铛', '电饼铛', 78, 150, 545, 626, '681.jpg'),
	(682, '电饼盛', '电饼盛', 124, 500, 545, 626, '682.jpg'),
	(683, '烤铛', '烤铛', 340, 20, 545, 626, '683.jpg'),
	(685, '阿凡达', '阿凡达', 34, 445, 542, NULL, '685.jpg'),
	(693, '化妆刷', 'Estee Lauder雅诗兰黛金色水晶化妆刷四件套刷 (限量特卖)支持货到付款', 58, 21, 545, 548, '693.jpg'),
	(694, '睫毛膏', 'Maybelline美宝莲 巨密睫毛膏9.2ml送睫毛夹超值装正规品牌授权', 71.2, 23, 545, 548, '694.jpg'),
	(695, '指甲套装', '日美美容9件指甲套装（77005） 指甲钳刀口锋利、防锈能力强', 89.5, 24, 545, 548, '695.jpg'),
	(696, '梳子', 'Silky Beauty 丝妍 化妆梳子', 19.9, 25, 545, 548, '696.jpg'),
	(697, '粉扑', 'Silky Beauty 丝妍 化妆粉扑(耐油磨边2个装)', 9.9, 26, 545, 548, '697.jpg'),
	(698, '棉签', 'Silky Beauty 丝妍 美容棉签 ', 7.9, 27, 545, 548, '698.jpg'),
	(699, '随身镜', 'Anna Sui安娜苏魔幻随身镜', 165, 28, 5445, 548, '699.jpg');
/*!40000 ALTER TABLE `easybuy_product` ENABLE KEYS */;

-- 导出  表 easybuy.easybuy_product_category 结构
DROP TABLE IF EXISTS `easybuy_product_category`;
CREATE TABLE IF NOT EXISTS `easybuy_product_category` (
  `EPC_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EPC_NAME` varchar(20) DEFAULT NULL,
  `EPC_PARENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`EPC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=639 DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.easybuy_product_category 的数据：~10 rows (大约)
DELETE FROM `easybuy_product_category`;
/*!40000 ALTER TABLE `easybuy_product_category` DISABLE KEYS */;
INSERT INTO `easybuy_product_category` (`EPC_ID`, `EPC_NAME`, `EPC_PARENT_ID`) VALUES
	(542, '图书', 542),
	(545, '生活用品', 545),
	(546, '少儿图书', 542),
	(548, '化妆品', 545),
	(626, '厨房用品', 545),
	(628, '家用电器', 545),
	(631, '青年图书', 542),
	(632, '服饰', 632),
	(637, '上装', 632),
	(638, '下装', 632);
/*!40000 ALTER TABLE `easybuy_product_category` ENABLE KEYS */;

-- 导出  表 easybuy.easybuy_user 结构
DROP TABLE IF EXISTS `easybuy_user`;
CREATE TABLE IF NOT EXISTS `easybuy_user` (
  `EU_USER_ID` varchar(10) NOT NULL,
  `EU_USER_NAME` varchar(20) DEFAULT NULL,
  `EU_PASSWORD` varchar(20) DEFAULT NULL,
  `EU_SEX` varchar(1) DEFAULT NULL,
  `EU_BIRTHDAY` varchar(10) DEFAULT NULL,
  `EU_IDENTITY_CODE` varchar(60) DEFAULT NULL,
  `EU_EMAIL` varchar(80) DEFAULT NULL,
  `EU_MOBILE` varchar(11) DEFAULT NULL,
  `EU_ADDRESS` varchar(200) DEFAULT NULL,
  `EU_STATUS` int(11) DEFAULT NULL,
  `EU_LOGIN` float DEFAULT NULL,
  PRIMARY KEY (`EU_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.easybuy_user 的数据：~6 rows (大约)
DELETE FROM `easybuy_user`;
/*!40000 ALTER TABLE `easybuy_user` DISABLE KEYS */;
INSERT INTO `easybuy_user` (`EU_USER_ID`, `EU_USER_NAME`, `EU_PASSWORD`, `EU_SEX`, `EU_BIRTHDAY`, `EU_IDENTITY_CODE`, `EU_EMAIL`, `EU_MOBILE`, `EU_ADDRESS`, `EU_STATUS`, `EU_LOGIN`) VALUES
	('admin', '管理员', 'admin', 'T', '1983-02-14', '130406198302141869', 'hello@bdqn.com', '15812345678', '北京市海淀区成府路207号', 2, 1),
	('hello', '天天好心情', 'hello', 'f', NULL, '', 'hello@163.com', '13545678901', '河北省邯郸市建设大街54号', 1, 0),
	('jack', '杰克', 'jack', 'T', NULL, '', 'hello@163.com', '13040612354', '河北省邯郸市和平路39号', 1, 0),
	('ss', '普通用户', 'ss', 'F', NULL, NULL, 'hello@163.com', '15954891666', '北京市海淀区成府路207号', 1, 0),
	('tom', '汤姆', 'tom', 'T', NULL, '', 'hello@163.com', '18715971356', '河南省安阳市和平路39号', 1, 0),
	('www', '大家米', 'www', 'T', NULL, '', 'hello@163.com', '13040688888', '河南省安阳市和平路39号;北京市海淀区成府路207号', 1, 0);
/*!40000 ALTER TABLE `easybuy_user` ENABLE KEYS */;

-- 导出  表 easybuy.result 结构
DROP TABLE IF EXISTS `result`;
CREATE TABLE IF NOT EXISTS `result` (
  `stuno` char(10) DEFAULT NULL,
  `subjectName` char(10) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  easybuy.result 的数据：~6 rows (大约)
DELETE FROM `result`;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
INSERT INTO `result` (`stuno`, `subjectName`, `score`, `age`) VALUES
	('1', 'java', 80, 11),
	('1', 'c', 78, 12),
	('2', 'c', 60, 13),
	('3', 'java', 75, 14),
	('2', 'java', 75, 15),
	('4', 'java', 90, 16);
/*!40000 ALTER TABLE `result` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
