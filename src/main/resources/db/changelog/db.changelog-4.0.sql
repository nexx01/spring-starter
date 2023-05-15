--liquibase formatted sql

--changeSet aashutov:1
ALTER table users
add COLUMN password VARCHAR(128) default '{noop}123';

--changeSet aashutov:2
ALTER table users_aud
    add COLUMN password VARCHAR(128) ;
