--liquibase formatted sql

--changeSet aashutov:1
CREATE TABLE IF NOT EXISTS revision
(
    id SERIAL PRIMARY KEY,
    timestamp BIGINT NOT NULL
);

--changeSet aashutov:2
CREATE TABLE IF NOT EXISTS users_aud
(
    id BIGINT,
    rev INT REFERENCES revision (id),
    revtype SMALLINT,
    username VARCHAR(64) NOT NULL UNIQUE ,
    birth_date DATE,
    firstName VARCHAR(64),
    lastName VARCHAR(64),
    role VARCHAR(32),
    company_id BIGINT
)