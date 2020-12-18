USE blogdb;

create table captcha_codes (
id integer not null auto_increment,
code TINYTEXT NOT NULL,
secret_code TINYTEXT NOT NULL,
time DATETIME NOT NULL,
primary key (id)) engine=InnoDB;

create table global_settings (
id integer not null auto_increment,
code varchar(255) not null,
name varchar(255) not null,
value varchar(255) not null,
primary key (id)) engine=InnoDB;

create table post_comments (
id integer not null auto_increment,
parent_id integer,
post_id integer not null,
text TEXT NOT NULL,
time DATETIME NOT NULL,
user_id integer not null,
primary key (id)) engine=InnoDB;

create table post_votes (
id integer not null auto_increment,
post_id integer not null,
time DATETIME NOT NULL,
user_id integer not null,
value TINYINT NOT NULL,
primary key (id)) engine=InnoDB;

create table posts (
id integer not null auto_increment,
is_active TINYINT NOT NULL,
moderation_status ENUM('NEW','ACCEPTED','DECLINED') NOT NULL DEFAULT 'NEW',
moderator_id integer,
text TEXT NOT NULL,
time DATETIME NOT NULL,
title varchar(255) not null,
user_id integer not null,
view_count integer not null,
primary key (id)) engine=InnoDB;

create table tag2post (
id integer not null auto_increment,
post_id integer not null,
tag_id integer not null,
primary key (id)) engine=InnoDB;

create table tags (
id integer not null auto_increment,
name varchar(255) not null,
primary key (id)) engine=InnoDB;

create table users (
id integer not null auto_increment,
code varchar(255),
email varchar(255) not null,
is_moderator TINYINT NOT NULL,
name varchar(255) not null,
password varchar(255) not null,
photo TEXT,
reg_time DATETIME NOT NULL,
primary key (id)) engine=InnoDB;