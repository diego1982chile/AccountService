drop table if exists account;

CREATE TABLE account(
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   id_client INTEGER NOT NULL,
   id_holding INTEGER NOT NULL,
   username TEXT NOT NULL,
   password TEXT NOT NULL,
   company TEXT,
   FOREIGN KEY (id_client)
      REFERENCES client (id),
   FOREIGN KEY (id_holding)
      REFERENCES holding (id),
   UNIQUE(id_client, id_holding)
);