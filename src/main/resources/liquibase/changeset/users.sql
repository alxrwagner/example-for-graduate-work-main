-- liquibase formatted sql

-- changeset wagner:create-table-user
create table users(
                    id serial not null primary key,
                    first_name varchar(100) not null,
                    last_name varchar(100) not null,
                    username varchar(100) unique not null,
                    password varchar(255) not null,
                    phone varchar(100) not null,
                    image varchar(255),
                    role varchar(255),
                    enabled boolean not null
);