DROP DATABASE IF EXISTS `taxvice`;

CREATE DATABASE IF NOT EXISTS `taxvice`;

USE `taxvice`;

CREATE TABLE IF NOT EXISTS `user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `is_admin` BOOLEAN NOT NULL,
    `login` VARCHAR(10) NOT NULL UNIQUE,
    `password` CHAR(32) NOT NULL,
    `name` VARCHAR(100) NULL,
    -- percents [0..100]
    `discount` SMALLINT DEFAULT 0,
    --
    CONSTRAINT `pk_user` PRIMARY KEY (`id`),
    CONSTRAINT `ck_user_discount` CHECK (
        `discount` >= 0
        AND `discount` < 100
    ),
    INDEX `ix_user_login` USING BTREE (`login`)
);

CREATE TABLE IF NOT EXISTS `state` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(10) NOT NULL,
    `description_long` varchar(100),
    --
    CONSTRAINT `pk_state` PRIMARY KEY (`id`),
    INDEX `ix_state_description` USING BTREE (`description`)
);

CREATE TABLE `class` (
    id INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) NOT NULL,
    `rate` SMALLINT NOT NULL,
    `description` VARCHAR(100),
    CONSTRAINT `pk_class` PRIMARY KEY (`id`),
    CONSTRAINT `ck_class_rate` CHECK (`rate` >= 0 AND `rate` <= 10)
);

CREATE TABLE IF NOT EXISTS `car` (
    `id` INT NOT NULL AUTO_INCREMENT,
    -- car_number
    `number` VARCHAR(10) NOT NULL,
    `capacity` SMALLINT NOT NULL,
    `mark` SMALLINT NULL,
    `state_id` INT NOT NULL,
    `class_id` int NOT NULL,
    CONSTRAINT `pk_car` PRIMARY KEY (`id`),
    CONSTRAINT `fk_car_state` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
    CONSTRAINT `fk_car_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`),
    CONSTRAINT `ck_car_capacity` CHECK (
        `capacity` > 0
        AND `capacity` < 50
    ),
    CONSTRAINT `ck_car_mark` CHECK (
        `mark` >= 0
        AND `mark` <= 5
    ),
    INDEX `ix_car_number` USING BTREE (`number`),
    INDEX `ix_car_capacity` USING BTREE (`capacity`),
    INDEX `ix_car_mark` USING BTREE (`mark`)
);

CREATE TABLE IF NOT EXISTS `feature` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `rating` SMALLINT,
    --
    CONSTRAINT `pk_feature` PRIMARY KEY (`id`),
    CONSTRAINT `ck_feature_rating` CHECK (
        `rating` >= 0
        AND `RATING` <= 5
    )
);

CREATE TABLE `locale`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(5) NOT NULL,
    `name` VARCHAR(100) NULL,
    --
    CONSTRAINT `pk_locale` PRIMARY KEY (`id`),
    INDEX `ix_locale_code` USING BTREE (`name`)
);

CREATE TABLE IF NOT EXISTS `feature_localization` (
    `feature_id` INT NOT NULL,
    `locale_id` INT NOT NULL,
    `description` VARCHAR(15) NOT NULL,
    `description_long` VARCHAR(100),
    --
    CONSTRAINT `pk_feature_localization` PRIMARY KEY (`feature_id`, `locale_id`),
    CONSTRAINT `fk_feature_localization_feature` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`id`),
    CONSTRAINT `fk_feature_localization_locale` FOREIGN KEY (`locale_id`) REFERENCES `locale` (`id`),
    INDEX `ix_feature_localization_description` USING BTREE (`description`),
    INDEX `ix_feature_localization_description_long` USING BTREE (`description_long`)
);

CREATE TABLE IF NOT EXISTS `car_feature` (
    `car_id` INT NOT NULL,
    `feature_id` INT NOT NULL,
    --
    CONSTRAINT `pk_car_feature` PRIMARY KEY (`car_id`, `feature_id`),
    CONSTRAINT `fk_car_feature_car` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_car_feature_feature` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `order` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT NOW(),
    `start_at` DATETIME NOT NULL DEFAULT NOW(),
    -- `end_at` aproximate calculated time of end of trip
    `end_at` DATETIME,
    `price` DECIMAL(10, 2),
    `route_discount` SMALLINT DEFAULT 0,
    `user_discount` SMALLINT DEFAULT 0,
    -- `cach` > 0 determine trip completed
    `cash` DECIMAL(10, 2),
    --
    CONSTRAINT `pk_order` PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `ck_order_price` CHECK (`price` > 0),
    CONSTRAINT `ck_order_route_discount` CHECK (
        `route_discount` >= 0
        AND `route_discount` <= 10
    ),
    CONSTRAINT `ck_order_user_discount` CHECK (
        `user_discount` >= 0
        AND `user_discount` <= 50
    ),
    CONSTRAINT `ck_order_cash` CHECK (`cash` > 0),
    INDEX `ix_order_created_at` USING BTREE (`created_at`),
    INDEX `ix_order_start_at` USING BTREE (`start_at`),
    INDEX `ix_order_end_at` USING BTREE (`end_at`)
);

CREATE TABLE `route` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `point_order` INT NOT NULL,
    `point_name` VARCHAR(10) NOT NULL,
    --
    CONSTRAINT `pk_route` PRIMARY KEY (`id`),
    CONSTRAINT `fk_route_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `executor` (
    `order_id` INT NOT NULL,
    `car_id` INT NOT NULL,
    --
    CONSTRAINT `pk_executor` PRIMARY KEY (`order_id`, `car_id`),
    CONSTRAINT `fk_executor_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_executor_car` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
);

CREATE TABLE IF NOT EXISTS `proposal` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `proposal_id` INT NOT NULL,
    `car_id` INT NOT NULL,
    `time_to_wait` TIME NULL,
    `created_at` timestamp not null default now(),
    --
    CONSTRAINT `pk_proposal` PRIMARY KEY (`id`),
    CONSTRAINT `fk_proposal_order` FOREIGN KEY (`order_id`) REFERENCES `order`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_proposal_car` FOREIGN KEY (`car_id`) REFERENCES `car`(`id`)
);

-- ------------------ INIT -----------------------------------------------

USE `taxvice`;

-- USER
-- ""(empty string) ==> "MD5" ==> "d41d8cd98f00b204e9800998ecf8427e"

INSERT INTO `user`(`is_admin`, `login`, `password`, `name`, `discount`)
VALUES(TRUE,'a','d41d8cd98f00b204e9800998ecf8427e','administrator of the system',0);

INSERT INTO`user`(`is_admin`,`login`,`password`,`name`,`discount`)
VALUES(false,'c','d41d8cd98f00b204e9800998ecf8427e','customer #1',10);

-- STATE

INSERT INTO`state`(`description`, `description_long`)
VALUES('active', 'vehicle avalible to get orders');

INSERT INTO`state`(`description`, `description_long`)
VALUES('inactive', 'vehicle can''t get orders');

INSERT INTO`state`(`description`, `description_long`)
VALUES('order', 'vehicle in process of ordering(cannot be ordered elsewhere)');

-- CLASS

INSERT INTO`class` (`name`, `rate`, `description`)
VALUES('econom', 0, 'small uncofortable car');

INSERT INTO`class` (`name`, `rate`, `description`)
VALUES('standard',1,'standard car(sedan) for usual use');

INSERT INTO`class` (`name`, `rate`, `description`)
VALUES('relax', 3, 'car for comfortable trip');

INSERT INTO`class` (`name`, `rate`, `description`)
VALUES('business', 4, 'business car for busy people');

INSERT INTO`class` (`name`, `rate`, `description`)
VALUES('vip', 5, 'VIP');

INSERT INTO`class` (`name`, `rate`, `description`)
VALUES('driver',10,'car with addition driver for drunk people');

-- FEATURE

INSERT INTO`feature`(`rating`) VALUES(1);

INSERT INTO `feature`(`rating`) VALUES (2);

INSERT INTO `feature`(`rating`) VALUES (3);

INSERT INTO `feature`(`rating`) VALUES (4);

-- LOCALE

INSERT INTO `locale`(`code`, `name`)
VALUES ('en_GB', 'English');

INSERT INTO `locale`(`code`, `name`)
VALUES ('uk_UA', 'Ukrainian');

-- FEATURE_LOCALIZATION

INSERT INTO `feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(1,1,'isolated','pasangers are isolated from driver');

INSERT INTO`feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(1,2,'ізольований','пасажири ізольовані від водія');

INSERT INTO`feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(2, 1, 'animals', 'car can carry out animals');

INSERT INTO`feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(2,2,'тварини','автомобіль придатний для перевозки тварин');

INSERT INTO`feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(3,1,'silence','trip in silence without annoing music');

INSERT INTO`feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(3, 2, 'тиша', 'подорож у тиші');

INSERT INTO`feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(4, 1, 'child', 'car has special chair for child');

INSERT INTO`feature_localization`(`feature_id`,`locale_id`,`description`,`description_long`)
VALUES(4,2,'дитина','автомобіль обладнаний кріслом для перевозки дітей');

-- CAR
INSERT INTO `car`(	`number`,	`capacity`,	`mark`,	`state_id`,	`class_id`)
VALUES(				'ВС1111АО', 3, 			5, 		1, 			1);
INSERT INTO`car`(	`number`,	`capacity`,	`mark`,	`state_id`,	`class_id`)
VALUES(				'АЗ2222АА', 3, 			4,		1, 			1);
INSERT INTO`car`(	`number`,	`capacity`,	`mark`,	`state_id`,	`class_id`)
VALUES(				'ВС3333АО', 3, 			5, 		1, 			2);
-- INSERT INTO `car`(	`number`,	`capacity`,	`mark`,	`state_id`,	`class_id`)
-- VALUES(				'ВС2789АО', 3, 			5, 		1, 			1);
-- CAR_FEATURE
INSERT INTO `car_feature`(`car_id`, `feature_id`) VALUES(1, 1);
INSERT INTO `car_feature`(`car_id`, `feature_id`) VALUES(2, 1);
INSERT INTO `car_feature`(`car_id`, `feature_id`) VALUES(3, 1);
-- INSERT INTO `car_feature`(`car_id`, `feature_id`) VALUES(1, 4);
-- INSERT INTO `car_feature`(`car_id`, `feature_id`) VALUES (2, 2);
-- INSERT INTO `car_feature`(`car_id`, `feature_id`) VALUES (2, 4);
-- INSERT INTO `car_feature`(`car_id`, `feature_id`) VALUES (3, 1);

-- ORDER

-- INSERT into `order`(`user_id`, `created_at`, `start_at`, `end_at`, `price`, `route_discount`, `user_discount`, `cash`)
-- VALUES (1, now(), now(), date_add(now(), interval 3 DAY_HOUR), 100, 10, 20, 72);
-- 	insert into `route`(`order_id`, `point_order`, `point_name`)
-- 		values (1, 1, '111');
-- 	insert into `route`(`order_id`, `point_order`, `point_name`)
-- 		values (1, 2, '222');
--     insert INTO `executor`(	`order_id`,	`car_id`)
-- 		values(					1, 			1);
--     
-- INSERT into `order`(`user_id`, `created_at`, `start_at`, `end_at`, `price`, `route_discount`, `user_discount`, `cash`)
-- VALUES (2, now(), now(), date_add(now(), interval 6 DAY_HOUR), 40, 5, 6, 30);
-- 	insert into `route`(`order_id`, `point_order`, `point_name`)
-- 		values (2, 1, '111');
-- 	insert into `route`(`order_id`, `point_order`, `point_name`)
-- 		values (2, 2, '333');
--     insert INTO `executor`(	`order_id`,	`car_id`)
-- 		values(					2, 			2);
--     
-- INSERT into `order`(`user_id`, `created_at`, `start_at`, `end_at`, `price`, `route_discount`, `user_discount`, `cash`)
-- VALUES (2, now(), date_add(now(), interval 1 DAY_HOUR), null, null, null, null, null);
-- 	insert into `route`(`order_id`, `point_order`, `point_name`) values (3, 1, '111');
-- 	insert into `route`(`order_id`, `point_order`, `point_name`) values (3, 2, '333');