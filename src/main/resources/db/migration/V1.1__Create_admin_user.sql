-- password = admin
INSERT INTO users (first_name,last_name,created_at,email,phone_number,password) VALUES ('admin','admin',NOW(),'admin@admin.com','+584143332222','$2a$10$d3OTxQ4QMCMbyaCYFwYo3u9dCsJ1fc7TfQFyeYN6qTeCvJJCY9ulW');
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO users_roles (user_id,role_id) VALUES (1,1);