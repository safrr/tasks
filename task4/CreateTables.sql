CREATE TABLE IF NOT EXISTS streets
(
    s_id INT PRIMARY KEY NOT NULL,
    s_name VARCHAR(150) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS locations_id_seq;
CREATE TABLE IF NOT EXISTS locations
(
    l_id INT PRIMARY KEY NOT NULL DEFAULT nextval('locations_id_seq'),
    l_latitude double precision NOT NULL,
    l_street INT NOT NULL,
    l_longitude double precision NOT NULL,
    CONSTRAINT l_street FOREIGN KEY (l_street)
        REFERENCES streets (s_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS outcome_statuses_id_seq;
CREATE TABLE IF NOT EXISTS outcome_statuses
(
    os_id INT PRIMARY KEY NOT NULL DEFAULT nextval('outcome_statuses_id_seq'),
    os_date date NOT NULL,
    os_category VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS crimes
(
    c_id INT PRIMARY KEY NOT NULL ,
    c_category VARCHAR(150) NOT NULL,
    c_location_type VARCHAR(50) NOT NULL,
    c_location INT NOT NULL,
    c_context VARCHAR(150) NOT NULL,
    c_outcome_status INT,
    c_persistent_id VARCHAR(64) NOT NULL,
    c_location_subtype VARCHAR(150) NOT NULL,
    c_month DATE NOT NULL,
    CONSTRAINT c_location FOREIGN KEY (c_location)
        REFERENCES locations (l_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT c_outcome_status FOREIGN KEY (c_outcome_status)
        REFERENCES outcome_statuses (os_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS stop_and_searches_id_seq;
CREATE TABLE IF NOT EXISTS stop_and_searches
(
    ss_type VARCHAR(150) NOT NULL,
    ss_involved_person boolean NOT NULL,
    ss_date_time date NOT NULL,
    ss_operation boolean NOT NULL,
    ss_operation_name  VARCHAR(150),
    ss_location_id INT,
    ss_gender VARCHAR(150),
    ss_age_range VARCHAR(150),
    ss_self_defined_ethnicity VARCHAR(150),
    ss_officer_defined_ethnicity VARCHAR(150),
    ss_legislation VARCHAR(150),
    ss_object_of_search VARCHAR(150),
    ss_outcome VARCHAR(150) NOT NULL,
    ss_outcome_linked_to_object_of_search VARCHAR(150),
    ss_removal_of_more_than_outer_clothing boolean NOT NULL,
    ss_id INT PRIMARY KEY NOT NULL DEFAULT nextval('stop_and_searches_id_seq'::regclass),
    CONSTRAINT ss_location_id FOREIGN KEY (ss_location_id)
        REFERENCES locations (l_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)