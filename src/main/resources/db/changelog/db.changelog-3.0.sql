--liquibase formatted sql

--changeSet aashutov:1
ALTER table users
add COLUMN image VARCHAR(63);

--changeSet aashutov:2
ALTER table users_aud
    add COLUMN image VARCHAR(63);
