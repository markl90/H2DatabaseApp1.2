DROP TABLE user_details IF EXISTS;

CREATE TABLE IF NOT EXISTS user_details (
  id INTEGER not NULL,
   first_name varchar(50),
  last_name varchar(50),
  PRIMARY KEY (id)
);

INSERT INTO user_details VALUES (100, 'Mark', 'Ledwold');





