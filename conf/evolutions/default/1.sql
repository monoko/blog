# --- !Ups

create table blog(
    id integer not null, 
    title varchar(255),
    body  text,
    created_at datetime not null, 
    updated_at datetime not null
    );

# --- !Downs
  drop table blog