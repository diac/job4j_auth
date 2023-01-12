CREATE TABLE person (
    id SERIAL PRIMARY KEY NOT NULL,
    login varchar UNIQUE NOT NULL,
    password varchar NOT NULL
);