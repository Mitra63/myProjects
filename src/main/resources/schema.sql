drop table if exists Customer;
drop table if exists Account;
drop table if exists User_Role;
drop table if exists Rest_User;
drop table if exists Role;



CREATE TABLE Rest_User
(
    User_Id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(20) UNIQUE NOT NULL,
    Password VARCHAR(200)       NOT NULL,
    Disabled BOOLEAN DEFAULT FALSE
);

CREATE TABLE Role
(
    Role_Id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    Role_Name VARCHAR(50) UNIQUE NOT NULL
);

create table User_Role
(
    User_Role_Id BIGINT PRIMARY KEY AUTO_INCREMENT,
    User_Id      BIGINT REFERENCES Rest_User (User_Id),
    Role_Id      BIGINT REFERENCES Role (Role_Id)
);

CREATE TABLE Customer
(
    User_Id      BIGINT NOT NULL,
    First_Name    VARCHAR(20),
    Last_Name     NVARCHAR(50),
    National_Code CHAR(15),
    Mobile       VARCHAR(15),
    Address      VARCHAR(100),
    PRIMARY KEY (User_Id),
    FOREIGN KEY (User_Id) REFERENCES Rest_User (User_Id) ON DELETE CASCADE
);

CREATE TABLE Account
(
    Account_ID  BIGINT AUTO_INCREMENT UNIQUE,
    Account_Number BIGINT AUTO_INCREMENT UNIQUE,
    User_Id       BIGINT  NOT NULL,
    Account_Type   VARCHAR(10) NOT NULL
        CONSTRAINT ATC CHECK (Account_Type IN ('CURRENT', 'SAVINGS')),
    Balance       BIGINT   NOT NULL,
    Create_Date    DATETIME,
    Blocked BOOLEAN DEFAULT false,
    PRIMARY KEY (Account_ID),
    FOREIGN KEY (User_Id) REFERENCES Rest_User (User_Id) ON DELETE CASCADE
);
CREATE SEQUENCE ACCOUNT_SEQUENCE_ID START WITH 1 INCREMENT BY 1;

CREATE TABLE Transaction
(
    Transaction_ID        BIGINT IDENTITY (1,1),
    Account_Id      BIGINT  NOT NULL,
    Destination_Account_Id BIGINT  NOT NULL,
    Amount               BIGINT   NOT NULL,
    Operation_Type        VARCHAR(15) NOT NULL,
    Transaction_Date      DATETIME,
    Description          NVARCHAR(1000),
    User_id     BIGINT  NOT NULL,
    PRIMARY KEY (Transaction_ID),
    FOREIGN KEY (Account_Id) REFERENCES Account (Account_ID) ON DELETE CASCADE,
    FOREIGN KEY (Destination_Account_Id) REFERENCES Account (Account_ID) ON DELETE CASCADE,
    FOREIGN KEY (User_id) REFERENCES Rest_User (User_Id) ON DELETE CASCADE
);
CREATE SEQUENCE TRANSACTION_SEQUENCE_ID START WITH 1 INCREMENT BY 1;
