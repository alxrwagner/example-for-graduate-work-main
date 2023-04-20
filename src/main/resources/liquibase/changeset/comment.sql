-- liquibase formatted sql

-- changeset wagner:create-table-comment
create table comment
(
    pk         serial not null primary key,
    created_at timestamp,
    text       varchar(255),
    ads_pk     integer
        references public.ads,
    author_id  integer
        references public.users
);