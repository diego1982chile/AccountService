drop table if exists user;

CREATE TABLE user(
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   username TEXT NOT NULL UNIQUE,
   password TEXT NOT NULL,
   salt TEXT NOT NULL
);