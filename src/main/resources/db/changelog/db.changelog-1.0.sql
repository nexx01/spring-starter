--liquibase formatted sql

--changeSet aashutov:1


CREATE TABLE IF NOT EXISTS company
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);
create sequence company_id_seq
    start 1
    increment 1
    NO MAXVALUE
    CACHE 1;
ALTER TABLE ONLY company ALTER COLUMN id SET DEFAULT nextval('company_id_seq'::regclass);

-- ALTER TABLE company_id ADD COLUMN key_column BIGSERIAL PRIMARY KEY;
--rollback DROP TABLE company;

--changeSet aashutov:2
CREATE TABLE IF NOT EXISTS company_locales
(
    company_id BIGINT REFERENCES company (id),
    lang VARCHAR(2),
    description VARCHAR(255) NOT NULL ,
    PRIMARY KEY (company_id, lang)
);
--changeSet aashutov:3
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    role VARCHAR(32),
    company_id BIGINT REFERENCES company (id)
);
--changeSet aashutov:4
CREATE TABLE IF NOT EXISTS payment
(
    id BIGSERIAL PRIMARY KEY ,
    amount INT NOT NULL ,
    receiver_id BIGINT NOT NULL REFERENCES users (id)
);
--changeSet aashutov:5
CREATE TABLE IF NOT EXISTS chat
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

--changeSet aashutov:6
CREATE TABLE IF NOT EXISTS users_chat
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL REFERENCES users (id),
    chat_id BIGINT NOT NULL REFERENCES chat (id),
    UNIQUE (user_id, chat_id)
);
