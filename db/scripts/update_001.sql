CREATE TABLE person (
    id SERIAL PRIMARY KEY NOT NULL,
    login varchar UNIQUE NOT NULL,
    password varchar NOT NULL
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');