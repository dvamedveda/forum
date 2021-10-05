-- create post table

create table "forum_posts"
(
    id            serial primary key,
    post_name     text      not null,
    post_desc     text      not null,
    created       timestamp not null,
    forum_user_id int references "forum_users" (id)
);