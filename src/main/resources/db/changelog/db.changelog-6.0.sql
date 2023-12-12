--liquibase formatted sql

--changeSet aashutov:1
ALTER table company
add COLUMN is_world_famous boolean;

