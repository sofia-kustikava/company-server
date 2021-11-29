CREATE TABLE Companies (
    id bigint NOT NULL PRIMARY KEY,
    currency varchar(3) NOT NULL,
    description varchar(255) NOT NULL,
    display_symbol varchar(10) NOT NULL,
    figi varchar(255) NOT NULL,
    mic varchar(10) NOT NULL,
    symbol varchar(10) NOT NULL,
    type varchar(255) NOT NULL
);

CREATE TABLE Reports (
    id bigint NOT NULL PRIMARY KEY,
    companies_id bigint NOT NULL,
    unit varchar(3) NOT NULL,
    label varchar(255) NOT NULL,
    value bigint NOT NULL,
    concept varchar(255) NOT NULL
);

ALTER TABLE Reports
    ADD CONSTRAINT CompaniesId FOREIGN KEY(companies_id) REFERENCES Companies(id);

CREATE TABLE Quote (
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

CREATE TABLE Metrics (
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

create sequence companies_id_seq;

alter sequence companies_id_seq owner to postgres;

alter sequence companies_id_seq owned by companies.id;

create sequence quote_id_seq;

alter sequence quote_id_seq owner to postgres;

alter sequence quote_id_seq owned by quote.id;

create sequence metrics_id_seq;

alter sequence metrics_id_seq owner to postgres;

alter sequence metrics_id_seq owned by metrics.id;