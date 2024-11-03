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