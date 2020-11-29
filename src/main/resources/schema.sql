CREATE TABLE `account_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `trasaction_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trasaction_type` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `denomination` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` int NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `account_details` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `customer_pin` varchar(6) DEFAULT NULL,
  `customer_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `amount` double DEFAULT NULL,
  `account_type` int NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `account_created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_id_UNIQUE` (`customer_id`)
);



CREATE TABLE `atm_state` (
  `id` int NOT NULL AUTO_INCREMENT,
  `denomination_id` int NOT NULL,
  `denomination_count` int NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `deposit_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_details_id` bigint NOT NULL,
  `deposit_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`)
);




CREATE TABLE `transaction_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_details_id` bigint NOT NULL,
  `transaction_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `trasaction_type_id` int NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `withdrawal_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_details_id` bigint NOT NULL,
  `withdrawal_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `amount` double NOT NULL,
  `atm_state_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);


