# --- !Ups
alter table article ADD PRIMARY KEY (id);
alter table article modify id INTEGER AUTO_INCREMENT;

alter table user ADD PRIMARY KEY (id);
alter table user modify id INTEGER AUTO_INCREMENT;

# --- !Downs
alter table article DROP PRIMARY KEY;
alter table article modify id INTEGER NOT NULL;

alter table user DROP PRIMARY KEY;
alter table user modify id INTEGER NOT NULL;
