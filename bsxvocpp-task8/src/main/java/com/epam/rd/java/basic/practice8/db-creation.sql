CREATE DATABASE IF NOT EXISTS pr8;

USE pr8;

DROP TABLE IF EXISTS users;

CREATE TABLE users(
    `id` int NOT NULL AUTO_INCREMENT,
    `login` varchar(10),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS teams;

CREATE TABLE teams(
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(10),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS users_teams;

CREATE TABLE `users_teams` (
    `user_id` int NOT NULL,
    `team_id` int NOT NULL,
    UNIQUE KEY `users_teams_uk` (`user_id`, `team_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`team_id`) REFERENCES `teams`(`id`) ON DELETE CASCADE
);