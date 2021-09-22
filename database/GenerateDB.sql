CREATE TABLE Users
(
    id bigint NOT NULL PRIMARY KEY,
    first_name varchar(70) NOT NULL,
    last_name varchar(70) NOT NULL,
    email varchar(50) NOT NULL,
    date_created date,
    date_updated date,
    status varchar(25) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE Subscriptions
(
    id bigint NOT NULL PRIMARY KEY,
    subscription varchar(255) NOT NULL,
    date_start date NOT NULL,
    date_end date NOT NULL,
    users_id bigint NOT NULL,
    CONSTRAINT fk_users FOREIGN KEY(users_id) REFERENCES Users(id)
);

CREATE TABLE Roles
(
    id bigint NOT NULL PRIMARY KEY,
    role_name varchar(25) NOT NULL
);

CREATE TABLE Users_Roles (
     users_id bigint NOT NULL,
     roles_id bigint NOT NULL,
     PRIMARY KEY (users_id, roles_id),
     FOREIGN KEY (users_id) REFERENCES users(id) ON UPDATE CASCADE,
     FOREIGN KEY (roles_id) REFERENCES roles(id) ON UPDATE CASCADE
);

CREATE TABLE Companies
(
    id integer NOT NULL PRIMARY KEY,
    symbol varchar(5) NOT NULL,
    currency varchar(3) NOT NULL,
    description varchar(255) NOT NULL,
    displaySymbol varchar(10) NOT NULL,
    type varchar(255) NOT NULL
);

CREATE TABLE Users_Companies (
     users_id bigint NOT NULL,
     companies_id bigint NOT NULL,
     PRIMARY KEY (users_id, companies_id),
     FOREIGN KEY (users_id) REFERENCES users(id) ON UPDATE CASCADE,
     FOREIGN KEY (companies_id) REFERENCES companies(id) ON UPDATE CASCADE
);

CREATE TABLE Reports
(
    id integer NOT NULL PRIMARY KEY,
    companies_id integer NOT NULL,
    unit varchar(3) NOT NULL,
    label varchar(255) NOT NULL,
    value bigint,
    concept varchar(255) NOT NULL
);

ALTER TABLE Reports
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

CREATE TABLE News
(
    id integer NOT NULL PRIMARY KEY,
    companies_id integer NOT NULL,
    from_date date,
    to_date date
);

ALTER TABLE News
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

CREATE TABLE Stocks
(
    id integer NOT NULL PRIMARY KEY,
    companies_id integer NOT NULL,
    country varchar(2) NOT NULL,
    currency varchar(3) NOT NULL,
    exchange varchar(255) NOT NULL,
    finnhubIndustry varchar(50) NOT NULL,
    ipo date,
    logo varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    phone float NOT NULL,
    ticker varchar(5) NOT NULL,
    weburl varchar(255) NOT NULL
);

ALTER TABLE Stocks
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

CREATE TABLE Metrics
(
    id integer NOT NULL PRIMARY KEY,
    companies_id integer NOT NULL,
    week_high float NOT NULL,
    week_high_date date,
    week_low float NOT NULL,
    week_low_date date,
    week_price_daily float NOT NULL
);

ALTER TABLE Metrics
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

CREATE TABLE Candles
(
    id integer NOT NULL PRIMARY KEY,
    companies_id integer NOT NULL,
    from_date date,
    to_date date,
    resolution varchar(3) NOT NULL
);

ALTER TABLE Candles
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);
