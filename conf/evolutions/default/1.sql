# --- !Ups

create table article(
    id integer not null,
    title varchar(255),
    body  text,
    created_at datetime not null,
    updated_at datetime not null
    );

create table user(
    id integer not null,
    name varchar(255),
    password  varchar(255)
    );

# --- !Downs
drop table article

drop table user
