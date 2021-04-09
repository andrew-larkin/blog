USE `blogdb`;

INSERT INTO `tags` (`id`, `name`) VALUES (1,"Spring");
INSERT INTO `tags` (`name`) VALUES ("Spring Boot"), ("Flyway"), ("Hibernate"), ("Swagger");

INSERT INTO `users` (`id`, `is_moderator`, `reg_time`, `name`, `email`, `password`, `code`, `photo`, `application_user_role`)
VALUES (1, 1, "2020-12-15' '19:10", "AndrewLarkin", "diesel-z@yandex.ru",
"$2y$10$4pYGZqZC8j3/CdoaJVhicuDh1IwFuGGroirhRp/C4POCxLjyLwDgK", "1456",
"https://res.cloudinary.com/unevbe14/image/upload/v1617488127/umaual5zoqhdc7s4fdon.png", "MODERATOR");

INSERT INTO `users` (`is_moderator`, `reg_time`, `name`, `email`, `password`, `code`, `photo`, `application_user_role`)
VALUES (0, "2021-01-15' '19:20", "AlexeyPushkarev", "apushkarev@yandex.ru",
"$2y$10$YzXAyl5BiKmjos8z0wWTFu.MQ5S91QwyYj3AyPqPCINN2QHgA0mAe", "",
"https://res.cloudinary.com/unevbe14/image/upload/v1617567749/yotquatq0zfaimanpkhq.png", "USER"),
(0, "2021-01-15' '19:30", "DenisStepashin", "astepashin@yandex.ru",
"$2y$10$AI3QSGIa.cfs3U77FINlZe0Rj4KPWSLEjC7v/cloMkyU8EiwrMklG", "", "", "USER"),
(0, "2021-01-15' '19:40", "MaxMarakanov", "mmarakanov@yandex.ru",
"$2y$10$oBOMqorS0VUYp/WLoF0qWO4cBHX5WFob1Km7f/ffDOtXL5em5kYcO", "", "", "USER"),
(0, "2021-01-15' '19:41", "LeonidStepanov", "gleonov@yandex.ru",
"$2y$10$MQiE5Q/VMf1gnfqY9HjrQOAV5UAYWhjBA3QDntYS.vr8y4ddKEcTC", "", "", "USER"),
(1, "2021-03-16' '21:40", "AndrewPavlow", "blin72305@gmail.com",
"$2a$12$AaMScb1726IawUvqrB5rz.krzyMmuWfYxK3sWoGMVgkSX4LyT4gVi", "", "", "MODERATOR");

INSERT INTO `posts` (`id`, `is_active`, `moderation_status`, `moderator_id`, `user_id`, `time`, `title`, `text`, `view_count`)
VALUES (1, 1, "ACCEPTED", 6, 1, "2021-04-07' '19:40", "DispatcherServlet and web.xml in Spring Boot",
"The DispatcherServlet is the front controller in Spring web applications.", 6);
INSERT INTO `posts` (`is_active`, `moderation_status`, `moderator_id`, `user_id`, `time`, `title`, `text`, `view_count`)
VALUES (1, "ACCEPTED", 1, 3, "2021-01-15' '19:57", "Rolling Back Migrations with Flyway",
"In this short tutorial, we'll explore a couple of ways to rollback a migration with Flyway", 5),
(1, "DECLINED", 1, 4, "2021-01-15' '20:17", "Background Jobs in Spring with JobRunr",
"In this tutorial, we're going to look into distributed background job scheduling and processing in Java using JobRunr and have it integrate with Spring", 0),
(1, "ACCEPTED", 1, 3, "2021-01-16' '20:27", "Redis vs MongoDB",
"First, we'll take a quick look at the features offered by Redis and MongoDB. Then, we'll discuss when to use Redis or MongoDB by comparing them against each other.", 3),
(1, "ACCEPTED", 6, 2, "2021-04-08' '20:41", "ArrayList vs. LinkedList in Java",
"Collections in Java are based on a couple of core interfaces and more than a dozen implementation classes. The wide selection of different implementations can sometimes lead to confusion.", 2),
(1, "ACCEPTED", 1, 1, "2021-04-07' '20:41", "JWT schema",
"JWT schema:<div><img src='https://res.cloudinary.com/unevbe14/image/upload/v1617491772/dl9ziz9nf5jitu2tnuaz.png'><br></div>", 1);

INSERT INTO `tag2post` (`id`, `post_id`, `tag_id`)
VALUES (1, 1, 2);
INSERT INTO `tag2post` (`post_id`, `tag_id`)
VALUES (2, 3), (3, 1), (1, 1);

INSERT INTO `post_votes` (`id`, `user_id`, `post_id`, `time`, `value`)
VALUES (1, 2, 1, "2021-01-15' '19:45", 1);
INSERT INTO `post_votes` (`user_id`, `post_id`, `time`, `value`)
VALUES (4, 1, "2021-01-15' '21:45", 1),
(3, 2, "2021-01-15' '22:45", 1),
(1, 5, "2021-04-06' '19:35", 1),
(1, 4, "2021-04-06' '19:39", -1),
(2, 6, "2021-04-06' '19:43", 1);

INSERT INTO `post_comments` (`id`, `parent_id`, `post_id`, `user_id`, `time`, `text`)
VALUES (1, 0, 1, 2, "2021-01-15' '19:45", "Very good");
INSERT INTO `post_comments` (`parent_id`, `post_id`, `user_id`, `time`, `text`)
VALUES (0, 2, 3, "2021-01-15' '20:45", "Thanks! Flyway is the thing, that i do not like right now, but have to love it in next two weeks.."),
(2, 2, 4, "2021-01-15' '21:45", "Do not worry, it is more easier than you think!"),
(0, 5, 6, "2021-04-02' '19:57", "Good!");
