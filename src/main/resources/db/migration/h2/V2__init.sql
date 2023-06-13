CREATE TABLE `user` (
  `id` varchar(40) NOT NULL ,
  `name` varchar(40) DEFAULT NULL ,
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


INSERT INTO `user` VALUES ('1', 'admin', '$2a$12$nXPoohJkpNbD1oSxtN0P1uGxhYP40Rn1Z0Yh1yxQ2lMhdz2TOqIZu');
INSERT INTO `inst` VALUES ('1', 'default', 'http://192.168.66.100:9180','edd1c9f034335f136f87ad84b625c8f1');