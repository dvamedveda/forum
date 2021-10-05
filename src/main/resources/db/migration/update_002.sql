-- create user table

create table "forum_users"
(
    id           serial primary key,
    login        text unique not null,
    password     text        not null,
    enabled      boolean default true,
    authority_id int references "authorities" (id)
);