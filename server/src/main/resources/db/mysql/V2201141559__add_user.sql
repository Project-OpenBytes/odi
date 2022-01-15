CREATE TABLE IF NOT EXISTS `user`
(
    `id` varchar
(
    32
)
    NOT NULL COMMENT 'user id ',
    `nickname` varchar
(
    255
) DEFAULT NULL COMMENT 'user nick name',
    `user_name` varchar
(
    255
) DEFAULT NULL COMMENT 'user unique name',
    `email` varchar
(
    255
) DEFAULT NULL COMMENT 'email',
    `avatar_url` VARCHAR
(
    1024
) default null,
    `status` int DEFAULT NULL COMMENT 'user account status',
    `insert_time` DATETIME
(
    3
) COMMENT '创建时间',
    `update_time` DATETIME
(
    3
) COMMENT '更新时间',
    PRIMARY KEY
(
    `id`
) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    ROW_FORMAT = Dynamic
    COMMENT = 'user table';


CREATE TABLE IF NOT EXISTS `channel_user`
(
    `id` varchar
(
    32
) NOT NULL ,
    `user_id` varchar
(
    32
) NOT NULL ,
    `channel_name` varchar
(
    255
),
    `channel_user_id` varchar
(
    255
) DEFAULT NULL,
    `channel_user_login` varchar
(
    255
) DEFAULT NULL,
    `channel_access_token` VARCHAR
(
    1024
) default null,

    `channel_refresh_token` VARCHAR
(
    1024
) default null,

    `token_type` VARCHAR
(
    32
) default null,
    `token_expire` DATETIME
(
    3
) ,
    `insert_time` DATETIME
(
    3
) ,
    `update_time` DATETIME
(
    3
) ,
    PRIMARY KEY
(
    `id`
) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    ROW_FORMAT = Dynamic;

alter table channel_user change id id int AUTO_INCREMENT;
alter table user change id id int AUTO_INCREMENT;