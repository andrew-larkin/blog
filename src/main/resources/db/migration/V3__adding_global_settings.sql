USE `blogdb`;

INSERT INTO `global_settings` (`id`,`code`, `name`, `value`) VALUES (1,"MULTIUSER_MODE", "Многопользовательский режим", "YES");
INSERT INTO `global_settings` (`code`, `name`, `value`) VALUES ("POST_PREMODERATION", "Премодерация постов", "NO"),
("STATISTICS_IS_PUBLIC", "Показывать всем статистику блога", "YES");