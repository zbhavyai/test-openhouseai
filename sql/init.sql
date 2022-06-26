DROP DATABASE IF EXISTS ohai_db;
DROP USER IF EXISTS 'ohai_app'@'%';

CREATE DATABASE IF NOT EXISTS ohai_db;
CREATE USER IF NOT EXISTS 'ohai_app'@'%' IDENTIFIED BY 'ohai_password';
GRANT ALL ON ohai_db.* TO 'ohai_app'@'%';

USE ohai_db;

CREATE TABLE applog (
    log_id BIGINT NOT NULL AUTO_INCREMENT,
    session_id VARCHAR(255),
    user_id VARCHAR(255),
    PRIMARY KEY (log_id)
);

CREATE TABLE actionproperties (
    properties_id BIGINT NOT NULL AUTO_INCREMENT,
    location_x DOUBLE PRECISION,
    location_y DOUBLE PRECISION,
    page_from VARCHAR(255),
    page_to VARCHAR(255),
    viewer_id VARCHAR(255),
    PRIMARY KEY (properties_id)
);

CREATE TABLE actions (
    actions_id BIGINT NOT NULL AUTO_INCREMENT,
    action_time DATETIME(6),
    action_type INTEGER,
    log_id BIGINT,
    properties_id BIGINT,
    PRIMARY KEY (actions_id),
    CONSTRAINT fk_actions_applog FOREIGN KEY (log_id) REFERENCES applog (log_id),
    CONSTRAINT fk_actions_actionproperties FOREIGN KEY (properties_id) REFERENCES actionproperties (properties_id)
);
