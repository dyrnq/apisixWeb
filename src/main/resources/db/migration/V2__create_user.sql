CREATE TABLE `user` (
  `id` varchar(40) NOT NULL ,
  `name` varchar(40) DEFAULT NULL ,
  `pass` varchar(40) DEFAULT NULL ,
  PRIMARY KEY (`id`)
);

INSERT INTO `user` VALUES ('1', 'admin', 'admin');
INSERT INTO `user` VALUES ('2', 'test', 'test');
INSERT INTO `user` VALUES ('3', 'root', 'root');

