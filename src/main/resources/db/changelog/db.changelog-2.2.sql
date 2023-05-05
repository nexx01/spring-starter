--liquibase formatted sql

--changeSet aashutov:1
ALTER table users_aud
Drop constraint users_aud_username_key

--changeSet aashutov:2
ALTER table users_aud
    alter column username drop not null
