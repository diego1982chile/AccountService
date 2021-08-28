drop table if exists holding;

CREATE TABLE holding(
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   name TEXT NOT NULL UNIQUE
);