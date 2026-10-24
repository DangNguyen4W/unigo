CREATE TABLE roles(
  id serial PRIMARY KEY,
  name varchar(50) NOT NULL UNIQUE,
  is_active boolean NOT NULL,
  created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamptz,
  CONSTRAINT chk_roles_name_uppercase CHECK (name = UPPER(name))
);

INSERT INTO roles(name, is_active)
VALUES
  ('SUPER_ADMIN', TRUE),
('USER', TRUE);

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
  created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamptz
);

CREATE TABLE permissions(
  id bigserial PRIMARY KEY,
  code varchar(100) NOT NULL UNIQUE,
  is_active boolean NOT NULL,
  created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamptz
);

CREATE TABLE role_permissions(
  role_id integer NOT NULL REFERENCES roles(id),
  permission_id bigint NOT NULL REFERENCES permissions(id),
  PRIMARY KEY (role_id, permission_id)) { }
