CREATE TABLE users (
    id IDENTITY,
    userName VARCHAR NOT NULL
);
CREATE TABLE vocabulary (
    id IDENTITY,
    vocab VARCHAR NOT NULL
);
CREATE TABLE ranking (
    id IDENTITY,
    /*userName VARCHAR NOT NULL PRIMARY KEY,*/
    name VARCHAR NOT NULL,
    score INT NOT NULL
);
