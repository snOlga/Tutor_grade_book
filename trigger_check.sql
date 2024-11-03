UPDATE tutor_lessons_requests SET is_approved = TRUE WHERE lesson_id=2;

SELECT * FROM students_lessons;

UPDATE student_lessons_requests SET is_approved = TRUE WHERE lesson_id=3;

SELECT * FROM students_lessons;

INSERT INTO lessons (tutor_id, start_time, duration, subject_id, homework, is_open, is_active, description, human_readable_id) 
VALUES 
	(1, '2024-11-01 10:00:00', '01:30:00', 1, 'Solve problems', TRUE, TRUE, 'Mathematics lesson', 'lesson-01');