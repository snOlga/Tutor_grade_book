CREATE TABLE creds (
	ID SERIAL PRIMARY KEY,
	login VARCHAR(50) CHECK ((LENGTH(login)>10) AND (login ~ '^([A-z]|[0-9])*$')),
	password TEXT
);

CREATE TABLE contacts (
	ID SERIAL PRIMARY KEY,
	phone VARCHAR(50) CHECK (phone ~ '^[0-9]*$'),
	email VARCHAR(50) CHECK (email ~ '^[0-z]+@([A-z]+\.)+[A-z]{2,4}$')
);

CREATE TABLE user_info (
	ID SERIAL PRIMARY KEY,
	name VARCHAR(50) CHECK (name ~ '^[A-z]*$'),
	second_name VARCHAR(50) CHECK (second_name ~ '^[A-z]*$'),
	contacts_id INT REFERENCES contacts(ID),
	description VARCHAR(400) CHECK (description ~ '^([A-z]|[0-9]|\s)*$')
);

CREATE TABLE students (
	ID SERIAL PRIMARY KEY,
	creds_id INT REFERENCES creds(ID),
	info_id INT REFERENCES user_info(ID),
	human_readable_id TEXT UNIQUE NOT NULL
);

CREATE TABLE tutors (
	ID SERIAL PRIMARY KEY,
	creds_id INT REFERENCES creds(ID),
	info_id INT REFERENCES user_info(ID),
	human_readable_id TEXT UNIQUE NOT NULL
);

CREATE TABLE chats (
	ID SERIAL PRIMARY KEY,
	tutor_id INT REFERENCES tutors(ID),
	student_id INT REFERENCES students(ID)
);

CREATE TABLE messages (
	ID SERIAL PRIMARY KEY,
	chat_id INT REFERENCES chats(ID),
	creds_id INT REFERENCES creds(ID),
	msg_text VARCHAR(200)
);

CREATE TABLE subjects (
	ID SERIAL PRIMARY KEY,
	main_name VARCHAR(50) CHECK (main_name ~ '^[A-z]*$'),
	analogy_names TEXT CHECK (main_name ~ '^(([A-z]|\s)+(;|$))*')
);

CREATE TABLE lessons (
	ID SERIAL PRIMARY KEY,
	tutor_id INT REFERENCES tutors(ID),
	start_time TIMESTAMP NOT NULL,
	duration INTERVAL NOT NULL,
	subject_id INT REFERENCES subjects(ID),
	homework TEXT NULL,
	is_open BOOLEAN,
	is_active BOOLEAN,
	description VARCHAR(200),
	human_readable_id TEXT UNIQUE NOT NULL
);

CREATE TABLE students_lessons (
	student_id INT REFERENCES students(ID),
	lesson_id INT REFERENCES lessons(ID)
);

CREATE TABLE student_lessons_requests (
	ID SERIAL PRIMARY KEY,
	student_id INT REFERENCES students(ID),
	lesson_id INT REFERENCES lessons(ID),
	is_approved BOOLEAN
);

CREATE TABLE tutor_lessons_requests (
	ID SERIAL PRIMARY KEY,
	tutor_id INT REFERENCES tutors(ID),
	student_id INT REFERENCES students(ID),
	lesson_id INT REFERENCES lessons(ID),
	is_approved BOOLEAN
);

----------------

CREATE INDEX chats_index ON chats USING HASH (ID);

CREATE INDEX creds_index ON creds USING HASH (ID);

CREATE INDEX user_info_index ON user_info USING HASH (ID);

CREATE INDEX students_index ON students USING HASH (ID);

CREATE INDEX tutors_index ON tutors USING HASH (ID);

CREATE INDEX contacts_index ON contacts USING HASH (ID);

CREATE INDEX lessons_index_on_ID ON lessons (start_time);
CREATE INDEX lessons_index_on_time ON lessons USING HASH (ID);

CREATE INDEX subjects_index ON subjects USING HASH (ID);

CREATE INDEX student_lessons_requests_index ON student_lessons_requests USING HASH (ID);

CREATE INDEX tutor_lessons_requests_index ON tutor_lessons_requests USING HASH (ID);

----------------

CREATE OR REPLACE FUNCTION update_student_lessons_from_tutor()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM students_lessons WHERE student_id = NEW.student_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO students_lessons (student_id, lesson_id) VALUES
				(NEW.student_id, NEW.lesson_id);
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_approving_lesson_by_student
AFTER UPDATE OF is_approved ON tutor_lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_student_lessons_from_tutor();



CREATE OR REPLACE FUNCTION update_student_lessons_from_student()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM students_lessons WHERE student_id = NEW.student_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO students_lessons (student_id, lesson_id) VALUES
				(NEW.student_id, NEW.lesson_id);
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_approving_lesson_by_tutor
AFTER UPDATE OF is_approved ON student_lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_student_lessons_from_student();



CREATE OR REPLACE FUNCTION insert_lesson_if_not_exists()
RETURNS TRIGGER AS $$
	BEGIN
		IF EXISTS (
			SELECT * FROM lessons WHERE tutor_id = NEW.tutor_id AND 
				(((start_time + duration) > NEW.start_time) AND 
					(start_time < NEW.start_time) OR 
					((start_time + duration) > (NEW.start_time + duration)) AND
					(start_time >= NEW.start_time))
			)
		THEN
			RAISE EXCEPTION 'At this time lesson already exists';
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_lesson_if_not_exists
BEFORE INSERT
ON lessons
FOR EACH ROW
EXECUTE FUNCTION insert_lesson_if_not_exists();



CREATE OR REPLACE FUNCTION delete_lesson()
RETURNS TRIGGER AS $$
	BEGIN
		UPDATE lessons SET is_active = FALSE WHERE ID = OLD.ID;
		RETURN NULL;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_delete_lesson
BEFORE DELETE
ON lessons
FOR EACH ROW
EXECUTE FUNCTION delete_lesson();

----------------

PREPARE insert_creds (VARCHAR(50), TEXT) AS 
	INSERT INTO creds (login, password) VALUES($1, $2);

PREPARE insert_contacts (VARCHAR(50), VARCHAR(50)) AS
	INSERT INTO contacts (phone, email) VALUES ($1, $2);

PREPARE insert_user_info (VARCHAR(50), VARCHAR(50), INT, VARCHAR(400)) AS
	INSERT INTO user_info (name, second_name, contacts_id, description) VALUES ($1, $2, $3, $4);

PREPARE insert_students (INT, INT, TEXT) AS
	INSERT INTO students (creds_id, info_id, human_readable_id) VALUES ($1, $2, $3);

PREPARE insert_tutors (INT, INT, TEXT) AS
	INSERT INTO tutors (creds_id, info_id, human_readable_id) VALUES ($1, $2, $3);

PREPARE insert_chats (INT, INT) AS
	INSERT INTO chats (tutor_id, student_id) VALUES ($1, $2);

PREPARE insert_messages (INT, INT, VARCHAR(200)) AS
	INSERT INTO messages (chat_id, creds_id, msg_text) VALUES ($1, $2, $3);

PREPARE insert_subjects (VARCHAR(50), TEXT) AS
	INSERT INTO subjects (main_name, analogy_names) VALUES ($1, $2);

PREPARE insert_lessons (INT, TIMESTAMP, INTERVAL, INT, TEXT, BOOLEAN, BOOLEAN, VARCHAR(200), TEXT) AS
	INSERT INTO lessons (tutor_id, start_time, duration, subject_id, homework, is_open, is_active, description, human_readable_id) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9);

PREPARE insert_students_lessons (INT, INT) AS
	INSERT INTO students_lessons (student_id, lesson_id) VALUES ($1, $2);

PREPARE insert_student_lessons_requests (INT, INT, BOOLEAN) AS
	INSERT INTO student_lessons_requests (student_id, lesson_id, is_approved) VALUES ($1, $2, $3);

PREPARE insert_tutor_lessons_requests (INT, INT, INT, BOOLEAN) AS
	INSERT INTO tutor_lessons_requests (tutor_id, student_id, lesson_id, is_approved) VALUES ($1, $2, $3, $4);

----------------

EXECUTE insert_creds('foostudent123', 'password1');
EXECUTE insert_creds('cooltutor123', 'password2');
EXECUTE insert_creds('barstudent123', 'password3');
EXECUTE insert_creds('supertutor007', 'password4');

EXECUTE insert_contacts('1234567890', 'student1@example.com');
EXECUTE insert_contacts('2345678901', 'tutor1@example.com');
EXECUTE insert_contacts('3456789012', 'student2@example.com');
EXECUTE insert_contacts('4567890123', 'tutor2@example.com');

EXECUTE insert_user_info('John', 'Doe', 1, 'Student of mathematics');
EXECUTE insert_user_info('Jane', 'Smith', 2, 'Tutor in mathematics');
EXECUTE insert_user_info('Emily', 'Johnson', 3, 'Student of physics');
EXECUTE insert_user_info('Michael', 'Brown', 4, 'Tutor in physics');

EXECUTE insert_students(1, 1, 'student-001');
EXECUTE insert_students(3, 3, 'student-002');

EXECUTE insert_tutors(2, 2, 'tutor-001');
EXECUTE insert_tutors(4, 4, 'tutor-002');

EXECUTE insert_chats(1, 1);
EXECUTE insert_chats(2, 2);

EXECUTE insert_messages(1, 1, 'I have a question about the homework.');
EXECUTE insert_messages(1, 2, 'I can help you.');
EXECUTE insert_messages(2, 3, 'When will we start?');
EXECUTE insert_messages(2, 4, 'Wait for 5 minutes.');

EXECUTE insert_subjects('Mathematics', 'Math;Algebra;Geometry');
EXECUTE insert_subjects('Physics', 'Mechanics;Thermodynamics');

EXECUTE insert_lessons(1, '2024-11-01 10:00:00', '01:30:00', 1, 'Solve problems', TRUE, TRUE, 'Mathematics lesson', 'lesson-001');
EXECUTE insert_lessons(2, '2024-11-02 14:00:00', '02:00:00', 2, 'Review', TRUE, TRUE, 'Physics lesson', 'lesson-002');
EXECUTE insert_lessons(1, '2024-11-01 12:00:00', '01:30:00', 1, 'Solve problems', TRUE, TRUE, 'Mathematics lesson', 'lesson-003');
EXECUTE insert_lessons(1, '2024-11-02 14:00:00', '02:00:00', 1, 'Review', TRUE, TRUE, 'Physics lesson', 'lesson-004');

EXECUTE insert_students_lessons(1, 1);

EXECUTE insert_tutor_lessons_requests(1, 1, 1, TRUE);
EXECUTE insert_tutor_lessons_requests(2, 2, 2, FALSE);

EXECUTE insert_student_lessons_requests(2, 3, FALSE);

----------------

UPDATE tutor_lessons_requests SET is_approved = TRUE WHERE lesson_id=2;

SELECT * FROM students_lessons;

UPDATE student_lessons_requests SET is_approved = TRUE WHERE lesson_id=3;

SELECT * FROM students_lessons;

SELECT * FROM lessons;

DELETE FROM lessons WHERE ID = 1;

SELECT * FROM lessons;

-- INSERT INTO lessons (tutor_id, start_time, duration, subject_id, homework, is_open, is_active, description, human_readable_id) 
-- VALUES 
-- 	(1, '2024-11-01 10:00:00', '01:30:00', 1, 'Solve problems', TRUE, TRUE, 'Mathematics lesson', 'lesson-01');

DROP TABLE students_lessons;
DROP TABLE tutor_lessons_requests;
DROP TABLE student_lessons_requests;
DROP TABLE lessons;
DROP TABLE subjects;
DROP TABLE messages;
DROP TABLE chats;
DROP TABLE students;
DROP TABLE tutors;
DROP TABLE creds;
DROP TABLE user_info;
DROP TABLE contacts;