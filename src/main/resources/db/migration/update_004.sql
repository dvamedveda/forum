-- create comments table

create table "forum_comments"
(
    id            serial primary key,
    comment_text  text      not null,
    commented     timestamp not null,
    forum_user_id int references "forum_users" (id),
    forum_post_id int references "forum_posts" (id)
);