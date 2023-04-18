-- liquibase formatted sql

-- changeset wagner:create-table-comment
create table comment
(
    pk bigserial not null primary key,
    author_image varchar(255),
    created_at   integer,
    text         varchar(255),
    ads_id       integer
        references public.ads,
    author_id    bigint
        references public."user"
);