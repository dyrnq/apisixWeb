CREATE TABLE `inst` (
  `id` varchar(40) NOT NULL ,
  `name` varchar(40) DEFAULT NULL ,
  `url` varchar(40) DEFAULT NULL ,
  `api_key` varchar(40) DEFAULT NULL ,
  PRIMARY KEY (`id`)
);

INSERT INTO `inst` VALUES ('1', '192.168.66.100:9180', 'http://192.168.66.100:9180','edd1c9f034335f136f87ad84b625c8f1');

