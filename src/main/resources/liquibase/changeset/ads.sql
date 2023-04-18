-- liquibase formatted sql

-- changeset wagner:create-table-ads
CREATE TABLE ads(
                       pk bigserial not null primary key ,
                       title varchar(100) not null,
                       description varchar(100) not null,
                       email varchar(100) not null,
                       phone varchar(100) not null,
                       price integer not null,
                       image varchar(255)
);