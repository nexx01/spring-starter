--liquibase formatted sql

--changeset aashutov:1

Create table if not exists customer
(
    id    bigint primary key,
    name  varchar(128),
    email varchar(128)
);

--changeset aashutov:2
create sequence hibernate_sequence;
