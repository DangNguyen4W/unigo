-- Bước 1: Xóa Foreign Key trước (vì nó ràng buộc 2 cột lại với nhau)
ALTER TABLE users DROP CONSTRAINT users_role_id_fkey;

-- Bước 2: Đổi kiểu roles.id từ BIGINT sang SMALLINT
ALTER TABLE roles ALTER COLUMN id TYPE SMALLINT;

-- Bước 3: Đổi chuỗi sequence của roles.id sang SMALLINT
ALTER SEQUENCE roles_id_seq AS SMALLINT;

-- Bước 4: Đổi kiểu users.role_id từ BIGINT sang SMALLINT
ALTER TABLE users ALTER COLUMN role_id TYPE SMALLINT;

-- Bước 5: Tạo lại Foreign Key
ALTER TABLE users ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES roles(id);
