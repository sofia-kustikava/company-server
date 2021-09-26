CREATE TABLE Users
(
    id bigint NOT NULL PRIMARY KEY,
    subscriptions_id bigint NOT NULL,
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
    date_end date NOT NULL
);

ALTER TABLE Users
    ADD CONSTRAINT SubscriptionsId FOREIGN KEY(subscriptions_id) REFERENCES Subscriptions(id);

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
    id bigint NOT NULL PRIMARY KEY,
    currency varchar(3) NOT NULL,
    description varchar(255) NOT NULL,
    display_symbol varchar(10) NOT NULL,
    figi varchar(255) NOT NULL,
    mic varchar(10) NOT NULL,
    symbol varchar(5) NOT NULL,
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
    id bigint NOT NULL PRIMARY KEY,
    companies_id bigint NOT NULL,
    unit varchar(3) NOT NULL,
    label varchar(255) NOT NULL,
    value bigint NOT NULL,
    concept varchar(255) NOT NULL
);

ALTER TABLE Reports
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

CREATE TABLE Quote
(
    id bigint NOT NULL PRIMARY KEY,
    companies_id bigint NOT NULL,
    current_price float NOT NULL,
    change float NOT NULL,
    percent_change float NOT NULL,
    high_price float NOT NULL,
    low_price float NOT NULL,
    open_price float NOT NULL,
    close_price float NOT NULL
);

ALTER TABLE Quote
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);


CREATE TABLE Metrics
(
    id bigint NOT NULL PRIMARY KEY,
    companies_id bigint NOT NULL,
    week_high float NOT NULL,
    week_high_date date,
    week_low float NOT NULL,
    week_low_date date,
    week_price_daily float NOT NULL
);

ALTER TABLE Metrics
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);