CREATE TABLE roles (
  ID SERIAL PRIMARY KEY,    
	role_name VARCHAR(50) UNIQUE NOT NULL CHECK (role_name ~ '^([A-z])*$')
);

CREATE TABLE users (
	ID SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL CHECK (name ~ '^[A-z]*$'),
	second_name VARCHAR(50) CHECK (second_name ~ '^[A-z]*$'),
  	phone VARCHAR(50) CHECK (phone ~ '^[0-9]*$'),    
	email VARCHAR(50) CHECK (email ~ '^[0-z\.]+@([0-z]+\.)+[A-z]{2,4}$'),
	human_readable_id TEXT UNIQUE NOT NULL,
  	description VARCHAR(400) CHECK (description ~ '^([A-z]|[0-9]|\s)*$'),    
	login VARCHAR(50) UNIQUE NOT NULL CHECK (login ~ '^([A-z\.]|[0-9\.]|[A-z\_]|[0-9\_])*$'),
  	password TEXT
);

CREATE TABLE users_roles (
	user_id INT REFERENCES users(ID),
	role_id INT REFERENCES roles(ID)
);

CREATE TABLE chats (
  	ID SERIAL PRIMARY KEY,
	user1_id INT REFERENCES users(ID),
  	user2_id INT REFERENCES users(ID)
);

CREATE TABLE messages (
	ID SERIAL PRIMARY KEY,
	chat_id INT REFERENCES chats(ID),
  	author_id INT REFERENCES users(ID),    
	sent_time TIMESTAMP NOT NULL,
  	is_edited BOOLEAN,
	msg_text VARCHAR(200) NOT NULL
);

CREATE TABLE subjects (
	ID SERIAL PRIMARY KEY,
	main_name VARCHAR(50) CHECK (main_name ~ '^[A-z]*$') UNIQUE NOT NULL,
	analogy_names TEXT CHECK (main_name ~ '^(([A-z]|\s)+(;|$))*')
);

CREATE TABLE lessons (
	ID SERIAL PRIMARY KEY,
	start_time TIMESTAMP NOT NULL,
	duration INTERVAL NOT NULL,
	subject_id INT REFERENCES subjects(ID),
	homework TEXT NULL,
	is_open BOOLEAN NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	description VARCHAR(200),
	human_readable_id TEXT UNIQUE NOT NULL
);

CREATE TABLE users_lessons (
	user_id INT REFERENCES users(ID),
  	lesson_id INT REFERENCES lessons(ID)
);

CREATE TABLE request_types (
	ID SERIAL PRIMARY KEY,
  	request_type VARCHAR(50) UNIQUE NOT NULL CHECK (request_type ~ '^([A-z\s])*$')
);

CREATE TABLE lessons_requests (
  	ID SERIAL PRIMARY KEY,    
	is_approved BOOLEAN,
  	sender_id INT REFERENCES users(ID),
	reciever_id INT REFERENCES users(ID),
  	lesson_id INT REFERENCES lessons(ID),    
	request_type_id INT REFERENCES request_types(ID),
	is_deleted BOOLEAN NOT NULL
);


----------------

CREATE OR REPLACE FUNCTION instead_of_delete_lesson()
RETURNS TRIGGER AS $$
	BEGIN
		UPDATE lessons SET is_deleted = TRUE WHERE ID = OLD.ID;
		RETURN NULL;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_instead_of_delete_lesson
BEFORE DELETE ON lessons
FOR EACH ROW
EXECUTE FUNCTION instead_of_delete_lesson();



CREATE OR REPLACE FUNCTION instead_of_delete_lesson_request()
RETURNS TRIGGER AS $$
	BEGIN
		UPDATE lessons_requests SET is_deleted = TRUE WHERE ID = OLD.ID;
		RETURN NULL;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_instead_of_delete_lesson_request
BEFORE DELETE ON lessons_requests
FOR EACH ROW
EXECUTE FUNCTION instead_of_delete_lesson_request();



CREATE OR REPLACE FUNCTION update_users_lessons_from_sender_request()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM users_lessons WHERE user_id = NEW.sender_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO users_lessons (user_id, lesson_id) VALUES
				(NEW.sender_id, NEW.lesson_id);
			DELETE FROM lessons_requests WHERE ID = NEW.ID;
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_update_users_lessons_from_sender_request
AFTER UPDATE OF is_approved ON lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_users_lessons_from_sender_request();



CREATE OR REPLACE FUNCTION update_users_lessons_from_reciever_request()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM users_lessons WHERE user_id = NEW.reciever_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO users_lessons (user_id, lesson_id) VALUES
				(NEW.reciever_id, NEW.lesson_id);
			DELETE FROM lessons_requests WHERE ID = NEW.ID;
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_update_users_lessons_from_reciever_request
AFTER UPDATE OF is_approved ON lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_users_lessons_from_reciever_request();



CREATE OR REPLACE FUNCTION prevent_duplicate_user_lesson()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM users_lessons 
        WHERE user_id = NEW.user_id AND lesson_id = NEW.lesson_id
    ) THEN
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_prevent_duplicate_user_lesson
BEFORE INSERT ON users_lessons
FOR EACH ROW
EXECUTE FUNCTION prevent_duplicate_user_lesson();



CREATE OR REPLACE FUNCTION update_edited_message()
RETURNS TRIGGER AS $$
BEGIN
    NEW.is_edited = true;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_update_edited_message
BEFORE UPDATE OF msg_text 
ON messages
FOR EACH ROW
EXECUTE FUNCTION update_edited_message();



CREATE OR REPLACE FUNCTION check_if_lesson_not_deleted()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (
        SELECT * FROM lessons 
        WHERE ID = NEW.lesson_id AND is_deleted = true
    ) THEN
		--RAISE EXCEPTION 'Lesson is deleted';
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_check_if_lesson_not_deleted
BEFORE INSERT ON lessons_requests
FOR EACH ROW
EXECUTE FUNCTION check_if_lesson_not_deleted();

----------------

PREPARE insert_into_roles (VARCHAR) AS
	INSERT INTO roles (role_name) VALUES ($1);

PREPARE insert_into_users (VARCHAR, VARCHAR, VARCHAR, VARCHAR, TEXT, VARCHAR, VARCHAR, TEXT) AS
	INSERT INTO users (name, second_name, phone, email, human_readable_id, description, login, password) VALUES ($1, $2, $3, $4, $5, $6, $7, $8);

PREPARE insert_into_users_roles (INT, INT) AS
	INSERT INTO users_roles (user_id, role_id) VALUES ($1, $2);

PREPARE insert_into_chats (INT, INT) AS
	INSERT INTO chats (user1_id, user2_id) VALUES ($1, $2);

PREPARE insert_into_messages (INT, INT, TIMESTAMP, BOOLEAN, VARCHAR) AS
	INSERT INTO messages (chat_id, author_id, sent_time, is_edited, msg_text) VALUES ($1, $2, $3, $4, $5);

PREPARE insert_into_subjects (VARCHAR, TEXT) AS
	INSERT INTO subjects (main_name, analogy_names) VALUES ($1, $2);

PREPARE insert_into_lessons (TIMESTAMP, INTERVAL, INT, TEXT, BOOLEAN, BOOLEAN, VARCHAR, TEXT) AS
	INSERT INTO lessons (start_time, duration, subject_id, homework, is_open, is_deleted, description, human_readable_id) VALUES ($1, $2, $3, $4, $5, $6, $7, $8);

PREPARE insert_into_users_lessons (INT, INT) AS
	INSERT INTO users_lessons (user_id, lesson_id) VALUES ($1, $2);

PREPARE insert_into_request_types (VARCHAR) AS
	INSERT INTO request_types (request_type) VALUES ($1);

PREPARE insert_into_lessons_requests (BOOLEAN, INT, INT, INT, INT, BOOLEAN) AS
	INSERT INTO lessons_requests (is_approved, sender_id, reciever_id, lesson_id, request_type_id, is_deleted) VALUES ($1, $2, $3, $4, $5, $6);

----------------

EXECUTE insert_into_roles('Admin');
EXECUTE insert_into_roles('Teacher');
EXECUTE insert_into_roles('Student');

EXECUTE insert_into_users('John', 'Doe', '1234567890', 'john.doe@example.com', 'John123', 'Admin', 'jdoe', 'password123');
EXECUTE insert_into_users('Jane', 'Smith', '0987654321', 'jane.smith@example.com', 'CoolJane', 'Mathematics Teacher', 'jsmith', 'securepass');
EXECUTE insert_into_users('Alice', 'Johnson', '5551234567', 'alice.j@example.com', 'Alice111', 'Student in Mathematics', 'alice.j', 'password');
EXECUTE insert_into_users('Mike', 'Smith', '0987654321', 'mike.smith@example.com', 'CoolMike', 'Mathematics Teacher', 'm_smith', 'securepass');
EXECUTE insert_into_users('Kyle', 'Johnson', '5551234567', 'Kyle.j@example.com', 'superKyle111', 'Student in Mathematics', 'hihihi.j', 'password');

EXECUTE insert_into_users_roles(1, 1);
EXECUTE insert_into_users_roles(2, 2);
EXECUTE insert_into_users_roles(3, 3);
EXECUTE insert_into_users_roles(4, 2);
EXECUTE insert_into_users_roles(5, 3);

EXECUTE insert_into_chats(1, 2);
EXECUTE insert_into_chats(2, 3);

EXECUTE insert_into_messages(1, 1, '2024-11-09 12:30:00', false, 'Hello, Jane!');
EXECUTE insert_into_messages(1, 2, '2024-11-09 12:31:00', false, 'Hi John, how can I help?');
EXECUTE insert_into_messages(2, 2, '2024-11-09 13:00:00', true, 'Hello, Alice!');

EXECUTE insert_into_subjects('Mathematics', 'Math;Algebra;Geometry');
EXECUTE insert_into_subjects('Physics', 'Mechanics');

EXECUTE insert_into_lessons('2024-11-10 09:00:00', '12:30', 1, 'Complete exercises', true, false, 'Math lesson on Algebra', 'L001');
EXECUTE insert_into_lessons('2024-11-10 11:00:00', '14:00', 2, 'Read Chapter', true, false, 'Physics lesson on Mechanics', 'L002');

EXECUTE insert_into_users_lessons(3, 1);
EXECUTE insert_into_users_lessons(2, 2);

EXECUTE insert_into_request_types('Lesson Approval'); -- from teacher to student
EXECUTE insert_into_request_types('Attendance Request'); -- from student to teacher
EXECUTE insert_into_request_types('Collab Request'); -- from teacher to teacher

EXECUTE insert_into_lessons_requests(false, 3, 2, 1, 2, false);
EXECUTE insert_into_lessons_requests(false, 4, 2, 2, 3, false);

----------------

SELECT * FROM lessons;
DELETE FROM lessons WHERE ID = 1;
SELECT * FROM lessons;
SELECT * FROM lessons_requests;
DELETE FROM lessons_requests WHERE ID = 1;
SELECT * FROM lessons_requests;

SELECT * FROM users_lessons;
UPDATE lessons_requests SET is_approved = true WHERE ID = 1;
SELECT * FROM users_lessons;
UPDATE lessons_requests SET is_approved = true WHERE ID = 2;
SELECT * FROM lessons_requests;
SELECT * FROM users_lessons;

SELECT * FROM messages;
UPDATE messages SET msg_text = 'Hello, Jane! :)' WHERE ID = 1;
SELECT * FROM messages;

SELECT * FROM lessons;
SELECT * FROM lessons_requests;
EXECUTE insert_into_lessons_requests(false, 3, 2, 1, 2, false);
SELECT * FROM lessons_requests;

----------------

DROP TABLE lessons_requests;
DROP TABLE request_types;
DROP TABLE users_lessons;
DROP TABLE lessons;
DROP TABLE subjects;
DROP TABLE messages;
DROP TABLE chats;
DROP TABLE users_roles;
DROP TABLE users;
DROP TABLE roles;

----------------