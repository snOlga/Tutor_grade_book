CREATE TABLE roles (
  ID SERIAL PRIMARY KEY,    
	role_name VARCHAR(50) UNIQUE NOT NULL CHECK (role_name ~ '^([A-z])*$')
);

CREATE TABLE users_tutor_grade_book ( -- because of another db on my account :(
	ID SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL CHECK (name ~ '^[A-z]*$'),
	second_name VARCHAR(50) CHECK (second_name ~ '^[A-z]*$'),
  	phone VARCHAR(50) CHECK (phone ~ '^[0-9]*$'),    
	email VARCHAR(50) UNIQUE NOT NULL CHECK (email ~ '^[0-z\.]+@([0-z]+\.)+[A-z]{2,4}$'),
	human_readable_id TEXT UNIQUE NOT NULL CHECK (human_readable_id ~ '^([0-z\_])*$'),
  	description VARCHAR(400) CHECK (description ~ '^([A-z]|[0-9]|\s)*$'),    
  	password TEXT
);

CREATE TABLE users_roles (
	user_id INT REFERENCES users_tutor_grade_book(ID),
	role_id INT REFERENCES roles(ID)
);

CREATE TABLE chats (
  	ID SERIAL PRIMARY KEY,
	user1_id INT REFERENCES users_tutor_grade_book(ID),
  	user2_id INT REFERENCES users_tutor_grade_book(ID)
);

CREATE TABLE messages (
	ID SERIAL PRIMARY KEY,
	chat_id INT REFERENCES chats(ID),
  	author_id INT REFERENCES users_tutor_grade_book(ID),    
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
	human_readable_id TEXT UNIQUE NOT NULL CHECK (human_readable_id ~ '^([0-z\_])*$')
);

CREATE TABLE users_lessons (
	user_id INT REFERENCES users_tutor_grade_book(ID),
  	lesson_id INT REFERENCES lessons(ID)
);

CREATE TABLE request_types (
	ID SERIAL PRIMARY KEY,
  	request_type VARCHAR(50) UNIQUE NOT NULL CHECK (request_type ~ '^([A-z\s])*$')
);

CREATE TABLE lessons_requests (
  	ID SERIAL PRIMARY KEY,    
	is_approved BOOLEAN,
  	sender_id INT REFERENCES users_tutor_grade_book(ID),
	reciever_id INT REFERENCES users_tutor_grade_book(ID),
  	lesson_id INT REFERENCES lessons(ID),    
	request_type_id INT REFERENCES request_types(ID),
	is_deleted BOOLEAN NOT NULL
);