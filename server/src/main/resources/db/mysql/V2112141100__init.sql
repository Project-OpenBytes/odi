CREATE TABLE IF NOT EXISTS `dataset`
(
    `id` VARCHAR(128) NOT NULL,
    `name` VARCHAR(512) NOT NULL,
    `homepage` VARCHAR(1024) NOT NULL,
    `description` TEXT NOT NULL,
    `readme_link` VARCHAR(1024) NOT NULL,
    `created_at`  DATETIME(3),
    `updated_at`  DATETIME(3),
    `owner_name` TEXT,
    `creator_user_id` VARCHAR(128) ,
    `creator_org_id` VARCHAR(128),
    `view_count` INT,
    `star_count` INT,
    `download_count` INT,
    PRIMARY KEY (`id`) USING BTREE
    )
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    ROW_FORMAT = Dynamic;
