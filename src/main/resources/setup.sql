CREATE TABLE IF NOT EXISTS languages (
    id int(255) NOT NULL,
    name VARCHAR(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS language_messages (
    id int(255) NOT NULL,
    language_id int(255) NOT NULL,
    variable VARCHAR(48) NOT NULL,
    message VARCHAR(256) NOT NULL,
    plugin VARCHAR(48) NOT NULL,
    version VARCHAR(48) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (language_id) REFERENCES languages(id)
);