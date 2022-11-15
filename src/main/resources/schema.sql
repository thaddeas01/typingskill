CREATE TABLE users (
    id IDENTITY,
    userName VARCHAR NOT NULL
);
CREATE TABLE vocabulary (
    id IDENTITY,
    vocab VARCHAR NOT NULL
);
CREATE TABLE ranking (
    userName VARCHAR NOT NULL PRIMARY KEY,
    score INT NOT NULL
);
