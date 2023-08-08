--liquibase formatted sql

--changeset aashutov:1
create table if not exists product(
   id bigint primary key ,
   name varchar,
   price double precision

);

