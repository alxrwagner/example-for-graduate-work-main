-- liquibase formatted sql

-- changeset wagner:create-table-user
create table "user"(
                    id bigserial not null primary key,
                    first_name varchar(100) not null,
                    last_name varchar(100) not null,
                    email varchar(100) not null,
                    phone varchar(100) not null,
                    image varchar(255)
);