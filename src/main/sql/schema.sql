-- 数据库初始化脚本
-- 创建数据库
CREATE DATABASE seckill;
use seckill;
-- 创建seckill表
CREATE TABLE seckill(
 `seckill_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT  COMMENT '商品库存',
  `start_time`  datetime NOT NULL COMMENT  '秒杀开启时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time),
)ENGINE=InnoDB  AUTO_INCREMENT=1000 DEFAULT CHARASET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
INSERT INTO
    seckill(name, number, start_time, end_time)
  VALUES
      ('1000元秒杀iphone6',100,'2016-07-04 00:00:00','2016-07-05 00:00:00'),
      ('500元秒杀ipad2',200,'2016-07-04 00:00:00','2016-07-05 00:00:00'),
      ('200元秒杀小米4',300,'2016-07-04 00:00:00','2016-07-05 00:00:00'),
      ('100元秒杀小米note',400,'2016-07-04 00:00:00','2016-07-05 00:00:00');

-- 秒杀成功明细表
CREATE TABLE success_killed (
    `seckill_id` bigint NOT NULL COMMENT 'id',
    `user_phone` bigint NOT NULL COMMENT '用户手机号',
    `state`tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效，0：成功，1：已付款',
    `create_time` TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (seckill_id,user_phone),
    KEY idx_create_time (create_time)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT= '秒杀成功明细表';

mysql -uroot -p