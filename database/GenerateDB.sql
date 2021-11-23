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
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    price float NOT NULL,
    tracking_size int NOT NULL
);

CREATE TABLE Users_Subscriptions
(
    id bigint NOT NULL PRIMARY KEY,
    users_id bigint NOT NULL,
    subscriptions_id bigint NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users(id) ON UPDATE CASCADE,
    FOREIGN KEY (subscriptions_id) REFERENCES subscriptions(id) ON UPDATE CASCADE,
    date_start date,
    date_end date,
    sub_status varchar(10) NOT NULL
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
    id bigint NOT NULL PRIMARY KEY,
    currency varchar(3) NOT NULL,
    description varchar(255) NOT NULL,
    display_symbol varchar(10) NOT NULL,
    figi varchar(255) NOT NULL,
    mic varchar(10) NOT NULL,
    symbol varchar(10) NOT NULL,
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
    current_price float,
    change float,
    percent_change float,
    high_price float,
    low_price float,
    open_price float,
    close_price float
);

ALTER TABLE Quote
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

CREATE TABLE Metrics
(
    id bigint NOT NULL PRIMARY KEY,
    companies_id bigint,
    week_high float,
    week_high_date date,
    week_low float,
    week_low_date date,
    week_price_daily float
);

ALTER TABLE Metrics
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

create sequence users_subscriptions_id_seq;

alter sequence users_subscriptions_id_seq owner to postgres;

alter sequence users_subscriptions_id_seq owned by users_subscriptions.id;

create sequence users_id_seq;

alter sequence users_id_seq owner to postgres;

alter sequence users_id_seq owned by users.id;

create sequence companies_id_seq;

alter sequence companies_id_seq owner to postgres;

alter sequence companies_id_seq owned by companies.id;

create sequence quote_id_seq;

alter sequence quote_id_seq owner to postgres;

alter sequence quote_id_seq owned by quote.id;

create sequence metrics_id_seq;

alter sequence metrics_id_seq owner to postgres;

alter sequence metrics_id_seq owned by metrics.id;

