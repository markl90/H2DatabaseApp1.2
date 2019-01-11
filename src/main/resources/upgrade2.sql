CREATE TABLE IF NOT EXISTS addresses (
  id INTEGER not NULL,
  house_number integer,
   street_name varchar(50),
  PRIMARY KEY (id)
);


--INSERT INTO addresses values (100, 84, 'Catherton');
MERGE INTO addresses KEY(ID) values (100, 84, 'Catherton');