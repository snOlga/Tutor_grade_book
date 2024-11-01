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



