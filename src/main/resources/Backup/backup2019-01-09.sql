;             
CREATE USER IF NOT EXISTS SA SALT '79b5cac35253c60a' HASH '4f84fe1e96b7eb2510524092b9fedef99d8be52ec30cf457caca9cd8bd8d0618' ADMIN;           
CREATE CACHED TABLE PUBLIC.APPLICATION_SCHEMA_UPGRADE(
    UPGRADE_ID INT NOT NULL,
    UPGRADE_DETAILS VARCHAR(255),
    SCRIPT VARCHAR(255),
    UPGRADE_STATUS VARCHAR(255)
);        
-- 22 +/- SELECT COUNT(*) FROM PUBLIC.APPLICATION_SCHEMA_UPGRADE;             
INSERT INTO PUBLIC.APPLICATION_SCHEMA_UPGRADE(UPGRADE_ID, UPGRADE_DETAILS, SCRIPT, UPGRADE_STATUS) VALUES
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'COMPLETE'),
(1, 'Version 1.0.0 - initial schema', 'Sample.sql', 'STARTED');
CREATE CACHED TABLE PUBLIC.USER_DETAILS(
    ID INTEGER NOT NULL,
    FIRST_NAME VARCHAR(50),
    LAST_NAME VARCHAR(50)
);
ALTER TABLE PUBLIC.USER_DETAILS ADD CONSTRAINT PUBLIC.CONSTRAINT_3 PRIMARY KEY(ID);           
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.USER_DETAILS;            
INSERT INTO PUBLIC.USER_DETAILS(ID, FIRST_NAME, LAST_NAME) VALUES
(100, 'Mark', 'Ledwold');  
