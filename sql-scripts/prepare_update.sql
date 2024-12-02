PREPARE update_role (INT, VARCHAR) AS
    UPDATE roles SET role_name = $2 WHERE ID = $1;

PREPARE update_user_name (INT, VARCHAR) AS
    UPDATE users_tutor_grade_book SET name = $2 WHERE ID = $1;
PREPARE update_user_second_name (INT, VARCHAR) AS
    UPDATE users_tutor_grade_book SET second_name = $2 WHERE ID = $1;
PREPARE update_user_phone (INT, VARCHAR) AS
    UPDATE users_tutor_grade_book SET phone = $2 WHERE ID = $1;
PREPARE update_user_email (INT, VARCHAR) AS
    UPDATE users_tutor_grade_book SET email = $2 WHERE ID = $1;
PREPARE update_user_human_readable_id (INT, TEXT) AS
    UPDATE users_tutor_grade_book SET human_readable_id = $2 WHERE ID = $1;
PREPARE update_user_description (INT, VARCHAR) AS
    UPDATE users_tutor_grade_book SET description = $2 WHERE ID = $1;

PREPARE update_message (INT, VARCHAR) AS
    UPDATE messages SET msg_text = $2 WHERE ID = $1;

PREPARE update_subject_main_name (INT, VARCHAR) AS
    UPDATE subjects SET main_name = $2 WHERE ID = $1;
PREPARE update_subject_analogy_names (INT, TEXT) AS
    UPDATE subjects SET analogy_names = $2 WHERE ID = $1;

PREPARE update_lesson_description (INT, VARCHAR) AS
    UPDATE lessons SET description = $2 WHERE ID = $1;
PREPARE update_lesson_human_readable_id (INT, TEXT) AS
    UPDATE lessons SET human_readable_id = $2 WHERE ID = $1;
PREPARE update_lesson_homework (INT, TEXT) AS
    UPDATE lessons SET homework = $2 WHERE ID = $1;

PREPARE update_request_type (INT, VARCHAR) AS
    UPDATE request_types SET request_type = $2 WHERE ID = $1;