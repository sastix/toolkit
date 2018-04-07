DROP TABLE IF EXISTS `general_code`;

CREATE TABLE IF NOT EXISTS `general_code` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `general_code` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
