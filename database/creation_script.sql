# drop schema mgol;
-- -----------------------------------------------------
-- Schema mgol
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mgol`;

-- -----------------------------------------------------
-- Table `mgol`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`role`
(
    `id`   INT                                                          NOT NULL,
    `name` VARCHAR(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mgol`.`user_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`user_status`
(
    `id`   INT                                                          NOT NULL,
    `name` VARCHAR(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mgol`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`user`
(
    `id`             VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `username`       VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `email`          VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `password`       VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `last_name`      VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `first_name`     VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `middle_name`    VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_time`    TIMESTAMP                                                    NULL DEFAULT CURRENT_TIMESTAMP,
    `class_id`       VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `user_status_id` INT                                                          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC),
    INDEX `fk_user_class1_idx` (`class_id` ASC),
    INDEX `fk_user_user_status1_idx` (`user_status_id` ASC),
    CONSTRAINT `fk_user_user_status1`
        FOREIGN KEY (`user_status_id`)
            REFERENCES `mgol`.`user_status` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `mgol`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`class`
(
    `id`            VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `number`        INT                                                          NOT NULL,
    `letter`        VARCHAR(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `form_master`   VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `class_monitor` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    CONSTRAINT `fk_class_user1`
        FOREIGN KEY (`form_master`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_class_user2`
        FOREIGN KEY (`class_monitor`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

ALTER TABLE `mgol`.`user`
    ADD
        CONSTRAINT `fk_user_class1`
            FOREIGN KEY (`class_id`)
                REFERENCES `mgol`.`class` (`id`)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION;
-- -----------------------------------------------------
-- Table `mgol`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`user_role`
(
    `user_id` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `role_id` INT                                                          NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    INDEX `fk_user_role_user_idx` (`user_id` ASC),
    INDEX `fk_user_role_role1_idx` (`role_id` ASC),
    CONSTRAINT `fk_user_role_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_role_role1`
        FOREIGN KEY (`role_id`)
            REFERENCES `mgol`.`role` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mgol`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`subject`
(
    `id`   VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `name` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mgol`.`period`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`period`
(
    `id`          INT         NOT NULL,
    `day_of_week` SMALLINT(1) NOT NULL,
    `start_time`  TIME        NOT NULL,
    `end_time`    TIME        NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mgol`.`class_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`class_schedule`
(
    `id`         VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `teacher_id` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `room`       INT                                                          NULL,
    `class_id`   VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `subject_id` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `period_id`  INT                                                          NOT NULL,
    PRIMARY KEY (`id`, `teacher_id`, `class_id`, `subject_id`, `period_id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    INDEX `fk_class_schedule_user1_idx` (`teacher_id` ASC),
    INDEX `fk_class_schedule_class1_idx` (`class_id` ASC),
    INDEX `fk_class_schedule_subject1_idx` (`subject_id` ASC),
    INDEX `fk_class_schedule_period1_idx` (`period_id` ASC),
    CONSTRAINT `fk_class_schedule_user1`
        FOREIGN KEY (`teacher_id`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_class_schedule_class1`
        FOREIGN KEY (`class_id`)
            REFERENCES `mgol`.`class` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_class_schedule_subject1`
        FOREIGN KEY (`subject_id`)
            REFERENCES `mgol`.`subject` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_class_schedule_period1`
        FOREIGN KEY (`period_id`)
            REFERENCES `mgol`.`period` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mgol`.`reward`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`reward`
(
    `id`          VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `title`       VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `description` VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `user_id`     VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`, `user_id`),
    INDEX `fk_reward_user1_idx` (`user_id` ASC),
    CONSTRAINT `fk_reward_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mgol`.`notification_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`notification_type`
(
    `id`   VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `name` VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC)
)
    ENGINE = InnoDB
    COMMENT = 'Type of notification: news, info, important';


-- -----------------------------------------------------
-- Table `mgol`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`notification`
(
    `id`                   VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `created_by`           VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `title`                VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `message`              VARCHAR(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `approver`             VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `create_time`          TIMESTAMP                                                    NULL     DEFAULT CURRENT_TIMESTAMP,
    `is_approved`          TINYINT                                                      NOT NULL DEFAULT 0,
    `notification_type_id` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`, `created_by`, `notification_type_id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    INDEX `fk_notification_user1_idx` (`approver` ASC),
    INDEX `fk_notification_notification_type1_idx` (`notification_type_id` ASC),
    INDEX `fk_notification_user2_idx` (`created_by` ASC),
    CONSTRAINT `fk_notification_user1`
        FOREIGN KEY (`approver`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_notification_notification_type1`
        FOREIGN KEY (`notification_type_id`)
            REFERENCES `mgol`.`notification_type` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_notification_user2`
        FOREIGN KEY (`created_by`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mgol`.`user_notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mgol`.`user_notification`
(
    `user_id`         VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `notification_id` VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`user_id`, `notification_id`),
    INDEX `fk_user_notification_notification1_idx` (`notification_id` ASC),
    CONSTRAINT `fk_user_notification_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `mgol`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_notification_notification1`
        FOREIGN KEY (`notification_id`)
            REFERENCES `mgol`.`notification` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;
