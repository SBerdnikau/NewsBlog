create table if not exists public.users
(
    id               bigserial
        constraint users_pk
            primary key,
    first_name       varchar(255)                        not null,
    second_name      varchar(255)                        not null,
    email            varchar(255)                        not null,
    telephone_number varchar(255)                        not null,
    is_deleted       boolean   default false             not null,
    created          timestamp default CURRENT_TIMESTAMP not null,
    updated          timestamp default CURRENT_TIMESTAMP
);

alter table public.users
    owner to admin;

create table if not exists public.security
(
    id       bigserial
        constraint security_pk
            primary key,
    login    varchar(255)                           not null,
    password varchar(255)                           not null,
    user_id  bigint                                 not null
        constraint user_id
            references public.users
            on update cascade on delete cascade,
    role     varchar(100) default USER              not null,
    created  timestamp    default CURRENT_TIMESTAMP not null,
    updated  timestamp    default CURRENT_TIMESTAMP
);

alter table public.security
    owner to admin;

create table if not exists public.news
(
    id               bigserial
        constraint news_pk
            primary key,
    title            varchar(100)                        not null,
    image_news       varchar(255),
    description_news varchar(512),
    author_news      bigint
        constraint author_news_id
            references public.users
            on update cascade on delete cascade,
    created          timestamp default CURRENT_TIMESTAMP not null,
    updated          timestamp default CURRENT_TIMESTAMP
);

comment on column public.news.author_news is 'user_id';

alter table public.news
    owner to admin;

create table if not exists public.comments
(
    id                   bigserial
        constraint comments_pk
            primary key,
    comment_topic        varchar(255)                        not null,
    description_comments varchar(512),
    author_comments      bigint                              not null
        constraint author_comments_id
            references public.users
            on update cascade on delete cascade,
    created              timestamp default CURRENT_TIMESTAMP not null,
    updated              timestamp default CURRENT_TIMESTAMP
);

comment on constraint author_comments_id on public.comments is 'user_id';

alter table public.comments
    owner to admin;

