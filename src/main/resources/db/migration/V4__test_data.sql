USE `blogdb`;

INSERT INTO `tags` (`id`, `name`) VALUES (1,"Spring");
INSERT INTO `tags` (`name`) VALUES ("Spring Boot"), ("Flyway"), ("Hibernate"), ("Swagger");

INSERT INTO `users` (`id`, `is_moderator`, `reg_time`, `name`, `email`, `password`, `code`, `photo`, `application_user_role`)
VALUES (1, 1, "2021-01-15' '19:10", "AndrewLarkin", "diesel-z@yandex.ru", "degaus787ser2", "1456", "", "MODERATOR");

INSERT INTO `users` (`is_moderator`, `reg_time`, `name`, `email`, `password`, `code`, `photo`)
VALUES (0, "2021-01-15' '19:20", "AlexeyPushkarev", "apushkarev@yandex.ru", "us787ser2", "", ""),
(0, "2021-01-15' '19:30", "DenisStepashin", "astepashin@yandex.ru", "urmanga87ser2", "", ""),
(0, "2021-01-15' '19:40", "MaxMarakanov", "mmarakanov@yandex.ru", "u551d7ser2", "", ""),
(0, "2021-01-15' '19:41", "LeonidStepanov", "gleonov@yandex.ru", "us748ser2", "", "");

INSERT INTO `posts` (`id`, `is_active`, `moderation_status`, `moderator_id`, `user_id`, `time`, `title`, `text`, `view_count`)
VALUES (1, 1, "ACCEPTED", 1, 1, "2021-01-15' '19:40", "DispatcherServlet and web.xml in Spring Boot",
"The DispatcherServlet is the front controller in Spring web applications.", 6);
INSERT INTO `posts` (`is_active`, `moderation_status`, `moderator_id`, `user_id`, `time`, `title`, `text`, `view_count`)
VALUES (1, "ACCEPTED", 1, 3, "2021-01-15' '19:57", "Rolling Back Migrations with Flyway",
"In this short tutorial, we'll explore a couple of ways to rollback a migration with Flyway", 5),
(0, "NEW", 0, 4, "2021-01-15' '20:17", "Background Jobs in Spring with JobRunr",
"In this tutorial, we're going to look into distributed background job scheduling and processing in Java using JobRunr and have it integrate with Spring", 0),
(1, "ACCEPTED", 1, 3, "2021-01-16' '20:27", "Redis vs MongoDB",
"First, we'll take a quick look at the features offered by Redis and MongoDB. Then, we'll discuss when to use Redis or MongoDB by comparing them against each other.", 3),
(1, "ACCEPTED", 1, 2, "2021-01-17' '20:41", "ArrayList vs. LinkedList in Java",
"Collections in Java are based on a couple of core interfaces and more than a dozen implementation classes. The wide selection of different implementations can sometimes lead to confusion.", 2);

INSERT INTO `tag2post` (`id`, `post_id`, `tag_id`)
VALUES (1, 1, 2);
INSERT INTO `tag2post` (`post_id`, `tag_id`)
VALUES (2, 3), (3, 1);

INSERT INTO `post_votes` (`id`, `user_id`, `post_id`, `time`, `value`)
VALUES (1, 2, 1, "2021-01-15' '19:45", 1);
INSERT INTO `post_votes` (`user_id`, `post_id`, `time`, `value`)
VALUES (4, 1, "2021-01-15' '21:45", 1), (3, 2, "2021-01-15' '22:45", 1);

INSERT INTO `post_comments` (`id`, `parent_id`, `post_id`, `user_id`, `time`, `text`)
VALUES (1, 0, 1, 2, "2021-01-15' '19:45", "Very good");
INSERT INTO `post_comments` (`parent_id`, `post_id`, `user_id`, `time`, `text`)
VALUES (0, 2, 3, "2021-01-15' '20:45", "Thanks! Flyway is the thing, that i do not like right now, but have to love it in next two weeks.."),
(2, 2, 4, "2021-01-15' '21:45", "Do not worry, it is more easier than you think!");
