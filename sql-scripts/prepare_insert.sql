PREPARE insert_into_roles (VARCHAR) AS
	INSERT INTO roles (role_name) VALUES ($1);

PREPARE insert_into_users_tutor_grade_book (VARCHAR, VARCHAR, VARCHAR, VARCHAR, TEXT, VARCHAR, VARCHAR, TEXT) AS
	INSERT INTO users_tutor_grade_book (name, second_name, phone, email, human_readable_id, description, password) VALUES ($1, $2, $3, $4, $5, $6, $7);

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