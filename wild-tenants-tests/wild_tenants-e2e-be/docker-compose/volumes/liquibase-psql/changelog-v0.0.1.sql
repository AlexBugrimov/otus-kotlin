--liquibase formatted sql

CREATE TYPE mr_name_type AS ENUM ('hot_water', 'cold_water', 'electricity');
CREATE TYPE ub_month_type AS ENUM ('january', 'february', 'march', 'april', 'may', 'june', 'july', 'august', 'september', 'october', 'november', 'december');

CREATE TABLE utility_bill
(
    ub_id    TEXT PRIMARY KEY CHECK (length(ub_id) < 64),
    owner_id TEXT          NOT NULL CHECK (length(owner_id) < 64),
    lock     TEXT          NOT NULL CHECK (length(lock) < 64),
    month    ub_month_type NOT NULL,
    year     INT           NOT NULL
);

CREATE TABLE meter_reading
(
    mr_id             SERIAL PRIMARY KEY,
    name              mr_name_type NOT NULL,
    indicated_value   NUMERIC(6, 2),
    volume_for_period NUMERIC(6, 2),
    accrued_sum       NUMERIC(6, 2),
    paid_amount       NUMERIC(6, 2),
    ub_id             TEXT         NOT NULL,
    CONSTRAINT fk_meter_reading_utility_bill FOREIGN KEY (ub_id) REFERENCES utility_bill (ub_id)
);