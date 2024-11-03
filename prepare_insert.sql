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