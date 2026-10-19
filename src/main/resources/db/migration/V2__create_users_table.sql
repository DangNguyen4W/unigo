CREATE TABLE roles(
    id serial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE,
    created_at timestamptz,
    updated_at timestamptz
);

INSERT INTO roles(name)
VALUES
    ('SUPER_ADMIN'),
('USER');

CREATE TABLE users(
    id uuid PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    first_name varchar(50),
    last_name varchar(50),
    username varchar(50) NOT NULL UNIQUE,
    phone_number varchar(15) NOT NULL UNIQUE,
    role_id integer NOT NULL REFERENCES roles(id),
    email varchar(100) UNIQUE,
    is_active boolean NOT NULL,
    created_at timestamptz,
    updated_at timestamptz
);

