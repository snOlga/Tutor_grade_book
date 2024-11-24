EXECUTE insert_into_roles('Admin');
EXECUTE insert_into_roles('Teacher');
EXECUTE insert_into_roles('Student');

EXECUTE insert_into_users_tutor_grade_book('John', 'Doe', '1234567890', 'john.doe@example.com', 'John123', 'Admin', 'password123');
EXECUTE insert_into_users_tutor_grade_book('Jane', 'Smith', '0987654321', 'jane.smith@example.com', 'CoolJane', 'Mathematics Teacher', 'securepass');
EXECUTE insert_into_users_tutor_grade_book('Alice', 'Johnson', '5551234567', 'alice.j@example.com', 'Alice111', 'Student in Mathematics', 'password');
EXECUTE insert_into_users_tutor_grade_book('Mike', 'Smith', '0987654321', 'mike.smith@example.com', 'CoolMike', 'Mathematics Teacher', 'securepass');
EXECUTE insert_into_users_tutor_grade_book('Kyle', 'Johnson', '5551234567', 'Kyle.j@example.com', 'superKyle111', 'Student in Mathematics', 'password');

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