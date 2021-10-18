CREATE DATABASE street_level_crimes;

CREATE SCHEMA IF NOT EXISTS strret_crimes;

DROP TABLE IF EXISTS crimes CASCADE;

DROP TABLE IF EXISTS locations CASCADE;

DROP TABLE IF EXISTS streets CASCADE;

DROP TABLE IF EXISTS outcome_statuses CASCADE;

CREATE TABLE streets
(
    s_id INT  PRIMARY KEY NOT NULL,
    s_name VARCHAR(150) NOT NULL
);

CREATE TABLE locations
(
    l_id SERIAL PRIMARY KEY,
    l_latitude REAL NOT NULL,
    l_street INT NOT NULL,
    l_longitude REAL NOT NULL,
    FOREIGN KEY (l_street) REFERENCES streets (s_id) ON DELETE Cascade ON UPDATE Cascade
);

CREATE TABLE outcome_statuses(
    os_id SERIAL PRIMARY KEY,
    os_date DATE,
    os_category VARCHAR(150)
);

CREATE TABLE crimes
(
    c_id INT PRIMARY KEY NOT NULL ,
    c_category VARCHAR(150),
    c_location_type VARCHAR(50),
    c_location INT NOT NULL,
    c_context VARCHAR(150),
    c_outcome_status INT,
    c_persistent_id VARCHAR(64),
    c_location_subtype VARCHAR(150),
    c_month DATE,
    FOREIGN KEY (c_location) REFERENCES locations (l_id) ON DELETE Cascade ON UPDATE Cascade,
    FOREIGN KEY (c_outcome_status) REFERENCES outcome_statuses (os_id) ON DELETE Cascade ON UPDATE Cascade
);