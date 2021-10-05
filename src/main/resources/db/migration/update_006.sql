-- insert default user

insert into "forum_users" (login, password, enabled, authority_id)
values ('user', '$2a$10$YU0VQz3.y3FLVPPGjoCdhufzLsiYmb1VTty8ARIlVHq7NYiUFYBQi', true,
        (select id from "authorities" where authority = 'ROLE_USER'));