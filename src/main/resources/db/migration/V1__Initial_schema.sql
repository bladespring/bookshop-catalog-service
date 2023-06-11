CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    author VARCHAR(255) NOT NULL,
    created_date timestamp NOT NULL,
    isbn VARCHAR(255) UNIQUE NOT NULL,
    last_modified_date timestamp NOT NULL,
    price float8 NOT NULL,
    title VARCHAR(255) NOT NULL,
    version integer NOT NULL
);