CREATE TABLE user(
  id IDENTITY,
  userName VARCHAR NOT NULL
);
CREATE TABLE vocabulary(
  id IDENTITY,
  vocab varchar NOT NULL
);
CREATE TABLE ranking(
  userName varchar NOT NULL,
  score INTEGER
);
