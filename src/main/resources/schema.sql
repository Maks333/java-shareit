CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    CONSTRAINT user_pm_key PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR NOT NULL,
    available BOOLEAN NOT NULL,
    user_id BIGINT,
    CONSTRAINT item_pm_key PRIMARY KEY (id),
    CONSTRAINT item_fr_key FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT  GENERATED ALWAYS AS IDENTITY,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id BIGINT,
    booker_id BIGINT,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT booking_id_pm_k PRIMARY KEY(id),
    CONSTRAINT item_id_fr_k FOREIGN KEY(item_id) REFERENCES items(id),
    CONSTRAINT booker_id_fr_k FOREIGN KEY(booker_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    text VARCHAR(512) NOT NULL,
    item_id BIGINT,
    author_id BIGINT,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT comments_id_pm_k PRIMARY KEY(id),
    CONSTRAINT item_id_fr_k FOREIGN KEY(item_id) REFERENCES items(id),
    CONSTRAINT author_id_fr_k FOREIGN KEY(author_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    description VARCHAR(1000) NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id BIGINT,
    CONSTRAINT requests_id_pk PRIMARY KEY(id),
    CONSTRAINT requests_user_id_fk FOREIGN KEY(user_id) REFERENCES users(id)
);