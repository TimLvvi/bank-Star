-- liquibase formatted sql

-- changeset tim:1
CREATE TABLE dynamic_recommendation (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR,
    product_id VARCHAR,
    product_text TEXT
);

CREATE TABLE dynamic_rule (
    id SERIAL PRIMARY KEY,
    query VARCHAR,
    arguments TEXT,
    negate BOOLEAN,
    recommendation_id INTEGER REFERENCES dynamic_recommendation(id)
    );

    -- changeset tim:2
CREATE TABLE statistics_rule(
    id SERIAL PRIMARY KEY,
    count_triggers INTEGER DEFAULT 0,
    dynamic_rule_id INTEGER REFERENCES dynamic_rule(id)
    ON DELETE CASCADE
    );


