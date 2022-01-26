CREATE TABLE IF NOT EXISTS `dataset_tag`
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

    `dataset_id` varchar
(
    128
) NOT NULL ,
    `tag_name` VARCHAR
(
    128
) NOT NULL,
    PRIMARY KEY
(
    `id`
) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    ROW_FORMAT = Dynamic;


alter table dataset_tag change id id int AUTO_INCREMENT;
