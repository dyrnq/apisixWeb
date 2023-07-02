CREATE TABLE `user` (
  `id` varchar(40) NOT NULL ,
  `name` varchar(40) DEFAULT NULL ,
  `email` varchar(256) DEFAULT NULL ,
  `phone` varchar(256) DEFAULT NULL ,
  `pass` varchar(512) DEFAULT NULL ,
  PRIMARY KEY (`id`)
);

CREATE TABLE `inst` (
  `id` varchar(40) NOT NULL ,
  `name` varchar(40) DEFAULT NULL ,
  `url` varchar(40) DEFAULT NULL ,
  `api_key` varchar(40) DEFAULT NULL ,
  PRIMARY KEY (`id`)
);

CREATE TABLE `ca` (
  `id` varchar(40) NOT NULL ,
  `cert` CLOB DEFAULT NULL ,
  `key_r` CLOB DEFAULT NULL ,
  `not_after` BIGINT DEFAULT NULL ,
  `not_before` BIGINT DEFAULT NULL ,
  `subject` varchar(512) DEFAULT NULL ,
  `title` varchar(512) DEFAULT NULL ,
   PRIMARY KEY (`id`)
);

CREATE TABLE `cert` (
  `id` varchar(40) NOT NULL ,
  `domain` varchar(512) DEFAULT NULL ,
  `cert` CLOB DEFAULT NULL ,
  `key_r` CLOB DEFAULT NULL,
  `not_after` BIGINT DEFAULT NULL ,
  `not_before` BIGINT DEFAULT NULL ,
  `subject` varchar(512) DEFAULT NULL ,
  `ca_id` varchar(40) DEFAULT NULL ,
  `dnsapi` varchar(40) DEFAULT NULL ,
  `approach` INT DEFAULT NULL ,
  `renew` INT DEFAULT NULL ,
  `supplier` INT DEFAULT NULL ,
  `encryption` INT DEFAULT NULL ,
  `challenge` INT DEFAULT NULL ,
  `aux` CLOB DEFAULT NULL ,
  `inst_id` varchar(40) DEFAULT NULL ,
   PRIMARY KEY (`id`)
);

CREATE TABLE `manifest` (
  `id` varchar(40) NOT NULL ,
  `title` varchar(512) DEFAULT NULL ,
  `content` CLOB DEFAULT NULL ,
   PRIMARY KEY (`id`)
);



CREATE TABLE `deploy` (
  `id` varchar(40) NOT NULL ,
  `title` varchar(512) DEFAULT NULL ,
  `content` CLOB DEFAULT NULL ,
  `state` INT DEFAULT NULL ,
  `inst_id` varchar(40) DEFAULT NULL ,
  `insert_time` TIMESTAMP DEFAULT NULL ,
  `update_time` TIMESTAMP DEFAULT NULL ,
   PRIMARY KEY (`id`)
);




INSERT INTO `user` VALUES ('1', 'admin','hello@admin.com','13988888888', '$2a$12$nXPoohJkpNbD1oSxtN0P1uGxhYP40Rn1Z0Yh1yxQ2lMhdz2TOqIZu');
INSERT INTO `inst` VALUES ('1', 'default', 'http://192.168.66.100:9180','edd1c9f034335f136f87ad84b625c8f1');