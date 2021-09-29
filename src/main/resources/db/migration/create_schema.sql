-- create user tables

create table authorities (
    id serial primary key,
    authority text not null
);

create table forum_users (
    id serial primary key,
    login text unique not null,
    password text not null,
    enabled boolean default true,
    authority_id int references authorities(id)
);

-- create post table
create table forum_posts (
    id serial primary key,
    post_name text not null,
    post_desc text not null,
    created timestamp not null,
    forum_user_id int references forum_users(id)
);

-- create comments table
create table forum_comments (
    id serial primary key,
    comment_text text not null,
    commented timestamp not null,
    forum_user_id int references forum_users(id),
    forum_post_id int references forum_posts(id)
);

-- insert default roles
insert into authorities (authority) values ('ROLE_USER'), ('ROLE_ADMIN');

-- insert default user
insert into forum_users (login, password, enabled, authority_id)
values ('user', '$2a$10$YU0VQz3.y3FLVPPGjoCdhufzLsiYmb1VTty8ARIlVHq7NYiUFYBQi', true,
        (select id from authorities where authority = 'ROLE_USER'));