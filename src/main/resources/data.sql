-- Insert roles
INSERT INTO TB_ROLE (role_name) VALUES
('ROLE_ADMIN'),
('ROLE_PROFESSOR'),
('ROLE_STUDENT');

-- Insert users
INSERT INTO TB_USER (username, password) VALUES
('marcos', '$2a$10$Kii0EOtZ5ziIH1FItNbkb.vNZxdAURlJeDaRapwoZvpaA3EzSWNKe'),  -- Admin user
('pedro', '$2a$10$Kii0EOtZ5ziIH1FItNbkb.vNZxdAURlJeDaRapwoZvpaA3EzSWNKe'),   -- Professor user
('leonardo', '$2a$10$Kii0EOtZ5ziIH1FItNbkb.vNZxdAURlJeDaRapwoZvpaA3EzSWNKe'), -- Professor user
('andres', '$2a$10$Kii0EOtZ5ziIH1FItNbkb.vNZxdAURlJeDaRapwoZvpaA3EzSWNKe'),   -- Student user
('mariana', '$2a$10$Kii0EOtZ5ziIH1FItNbkb.vNZxdAURlJeDaRapwoZvpaA3EzSWNKe');  -- Student user

-- Assign roles to users
INSERT INTO TB_USER_ROLE (user_id, role_id) VALUES
(1, 1), -- 'marcos' has ROLE_ADMIN
(2, 2), -- 'pedro' has ROLE_PROFESSOR
(3, 2), -- 'leonardo' has ROLE_PROFESSOR
(4, 3), -- 'andres' has ROLE_STUDENT
(5, 3); -- 'mariana' has ROLE_STUDENT

-- Insert professors
INSERT INTO TB_PROFESSOR (document, first_name, last_name, phone_number, department, email, user_id) VALUES
('123', 'Pedro', 'Diaz', '111', 'Engineering', 'pedro@gmail.com', 2),  -- Professor linked to user 'pedro'
('124', 'Leonardo', 'Gomez', '222', 'Math', 'leo@gmail.com', 3);      -- Professor linked to user 'leonardo'

-- Insert courses
INSERT INTO TB_COURSE (course_code, name, description, credits, max_students, current_students, professor_id) VALUES
('123', 'Programming 1', 'Introduction to programming...', 2, 5, 0, 1), -- Course taught by professor with id:1
('124', 'Calculus 1', 'Basic calculus principles...', 2, 5, 0, 2);                -- Course taught by professor with id:2

-- Insert students
INSERT INTO TB_STUDENT (document, first_name, last_name, gender, birth_date, email, user_id) VALUES
('125', 'Andres', 'Gomez', 'MALE', '1991-10-20', 'andres@gmail.com', 4),   -- Student linked to user 'andres'
('126', 'Mariana', 'Vega', 'FEMALE', '1998-10-20', 'mariana@gmail.com', 5); -- Student linked to user 'mariana'
