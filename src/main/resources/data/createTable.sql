
create  DATABASE yuntu;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE yuntu.`sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `user_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                            `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `unq_name` (`user_name`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
