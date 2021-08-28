drop table if exists client;

CREATE TABLE client(
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   name TEXT NOT NULL UNIQUE
);