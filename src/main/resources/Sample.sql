CREATE TABLE IF NOT EXISTS user_details (
  id INTEGER not NULL,
   first_name varchar(50),
  last_name varchar(50),
  PRIMARY KEY (id)
);

merge into USER_DETAILS KEY(ID) values (100, 'Mark', 'Ledwold');





