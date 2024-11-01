INSERT INTO creds (login, password) VALUES 
	('foostudent123', 'password1'),
	('cooltutor123', 'password2'),
	('barstudent123', 'password3'),
	('supertutor007', 'password4');

INSERT INTO contacts (phone, email) VALUES 
	('1234567890', 'student1@example.com'),
	('2345678901', 'tutor1@example.com'),
	('3456789012', 'student2@example.com'),
	('4567890123', 'tutor2@example.com');

INSERT INTO user_info (name, second_name, contacts_id, description) VALUES 
	('John', 'Doe', 1, 'Student of mathematics'),
	('Jane', 'Smith', 2, 'Tutor in mathematics'),
	('Emily', 'Johnson', 3, 'Student of physics'),
	('Michael', 'Brown', 4, 'Tutor in physics');

INSERT INTO students (creds_id, info_id, human_readable_id) VALUES 	
	(1, 1, 'student-001'),
	(3, 3, 'student-002');

INSERT INTO tutors (creds_id, info_id, human_readable_id) VALUES 	
	(2, 2, 'tutor-001'),
	(4, 4, 'tutor-002');

INSERT INTO chats (tutor_id, student_id) VALUES
	(1, 1),
	(2, 2);

INSERT INTO messages (chat_id, creds_id, msg_text) VALUES 
	(1, 1, 'I have a question about the homework.'),
	(1, 2, 'I can help you.'),
	(2, 3, 'When will we start?'),
	(2, 4, 'Wait for 5 minutes.');

INSERT INTO subjects (main_name, analogy_names) VALUES 
	('Mathematics', 'Math;Algebra;Geometry'),
	('Physics', 'Mechanics;Thermodynamics');

INSERT INTO lessons (tutor_id, start_time, duration, subject_id, homework, is_open, is_active, description, human_readable_id) 
VALUES 
	(1, '2024-11-01 10:00:00', '01:30:00', 1, 'Solve problems', TRUE, TRUE, 'Mathematics lesson', 'lesson-001'),
	(2, '2024-11-02 14:00:00', '02:00:00', 2, 'Review', TRUE, TRUE, 'Physics lesson', 'lesson-002'),
	(1, '2024-11-01 12:00:00', '01:30:00', 1, 'Solve problems', TRUE, TRUE, 'Mathematics lesson', 'lesson-003');

INSERT INTO students_lessons (student_id, lesson_id) VALUES 
	(1, 1);

INSERT INTO tutor_lessons_requests (tutor_id, student_id, lesson_id, is_approved) VALUES 
	(1, 1, 1, TRUE),
	(2, 2, 2, FALSE);
	
INSERT INTO student_lessons_requests (student_id, lesson_id, is_approved) VALUES
  	(2, 3, FALSE);