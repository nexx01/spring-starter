--liquibase formatted sql

--changeset aashutov:1
create table if not exists project
(
    id bigint primary key,
    code varchar(32),
    name varchar(22),
    description varchar(128)
)
