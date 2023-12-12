--liquibase formatted sql

--changeSet aashutov:1
ALTER table company
add COLUMN public_name VARCHAR(128);

