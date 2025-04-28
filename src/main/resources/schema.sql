create table if not exists users (
    username varchar(100) not null unique,
    password varchar(100) not null
);