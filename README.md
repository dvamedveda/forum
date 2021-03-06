[![Build Status](https://app.travis-ci.com/dvamedveda/forum.svg?branch=master)](https://app.travis-ci.com/dvamedveda/forum)
[![codecov](https://codecov.io/gh/dvamedveda/forum/branch/master/graph/badge.svg?token=ICNBF3INPL)](https://codecov.io/gh/dvamedveda/forum)

# Simple forum app (using Spring Boot)

### Application features:
This Java web application just realizing simple forum with core mechanics:
- checking/updating its database with liquibase;
- create\edit post;
- view\comment post;
- additional info for posts/comments;
- user registration/authentification;
- user autorization;
- store data in database;
- minimal frontend support.

This application developed with layered architecture:
- persistance-layer
- service-layer
- controller-layer
- view-layer

This application uses Spring framework(s).
Application also deployed on Heroku.

### Usage:
- create postgresql db named 'forum' at localhost:5432, with postgres/postgres credentials;
- git clone project;
- mvn clean package spring-boot:run;
- visit http://localhost:8080/
##### OR
- visit https://warm-garden-05012.herokuapp.com/
- default user is 'user' with password 'secret'
- use forum

### Application screenshots:
- you can view main page with all posts anonymously, but view/comment/create post needs for auth
![anonim_main](https://github.com/dvamedveda/screenshots/blob/main/forum/forum_anonim_main.png?raw=true) 
- you can create new post or edit your own post after log-in
![main_logged_id](https://github.com/dvamedveda/screenshots/blob/main/forum/forum_main_logged_in.png?raw=true)
![create_post](https://github.com/dvamedveda/screenshots/blob/main/forum/forum_create_post.png?raw=true)
![edit_post](https://github.com/dvamedveda/screenshots/blob/main/forum/forum_edit_post.png?raw=true)
- you can comment any post
![view_and_comment](https://github.com/dvamedveda/screenshots/blob/main/forum/forum_view_and_comment.png?raw=true)
- after logout you will be redirect to login page
![logout](https://github.com/dvamedveda/screenshots/blob/main/forum/forum_logout.png?raw=true)

### Used frameworks, libs, technologies:
- Core: Java, Maven, Spring Data, Spring Security, Spring MVC, Spring Boot
- UI: Bootstrap, JSP, JSTL
- Side: Postgresql, Liquibase, Tomcat, Spring MockMVC, SpringBootTest