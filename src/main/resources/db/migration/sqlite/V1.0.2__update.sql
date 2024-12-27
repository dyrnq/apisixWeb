CREATE TABLE `inst_temp` (
  `id` varchar(40) NOT NULL ,
  `name` varchar(256) DEFAULT NULL ,
  `url` varchar(256) DEFAULT NULL ,
  `api_key` varchar(256) DEFAULT NULL ,
  `agent_url` VARCHAR(256) DEFAULT NULL ,
  `agent_api_key` VARCHAR(256) DEFAULT NULL ,
  PRIMARY KEY (`id`)
);

INSERT INTO inst_temp (id, agent_url, agent_api_key, name, url, api_key) SELECT id, agent_url, agent_api_key, name, url, api_key FROM inst;
DROP TABLE inst;
ALTER TABLE inst_temp RENAME TO inst;