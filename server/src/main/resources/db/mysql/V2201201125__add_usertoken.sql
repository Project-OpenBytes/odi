CREATE TABLE IF NOT EXISTS `user_token`
(
    `id` VARCHAR
(
    128
) NOT NULL,

    `insert_time` DATETIME
(
    3
),
    `update_time` DATETIME
(
    3
),

    `user_id` varchar
(
    32
) NOT NULL ,
    `user_token` VARCHAR
(
    128
) NOT NULL,
    `token_type` int
(
    3
) NOT NULL,


    `refresh_time` DATETIME
(
    3
),
    `expired_in_seconds` INT,
    PRIMARY KEY
(
    `id`
) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    ROW_FORMAT = Dynamic;


alter table user_token change id id int AUTO_INCREMENT;
