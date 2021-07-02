DROP TABLE IF EXISTS clicks CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS documents CASCADE;

CREATE TABLE users(
    id serial PRIMARY KEY,
    uuid UUID default gen_random_uuid(),
    name varchar,
    UNIQUE(uuid)
);
CREATE TABLE documents(
    id serial PRIMARY KEY,
    uuid UUID default gen_random_uuid(),
    name varchar,
    UNIQUE(uuid)
);
CREATE TABLE clicks(
    usr UUID REFERENCES users(uuid),
    doc UUID REFERENCES documents(uuid),
    date date
);


DROP index IF EXISTS clicks_doc;
CREATE INDEX clicks_doc on clicks(doc);

DROP index IF EXISTS users_uuid;
CREATE INDEX users_uuid on users(uuid);

DROP index IF EXISTS documents_uuid;
CREATE INDEX documents_uuid on documents(uuid);