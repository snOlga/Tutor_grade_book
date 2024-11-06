CREATE INDEX chats_index ON chats USING HASH (ID);

CREATE INDEX creds_index ON creds USING HASH (ID);

CREATE INDEX user_info_index ON user_info USING HASH (ID);

CREATE INDEX students_index ON students USING HASH (ID);

CREATE INDEX tutors_index ON tutors USING HASH (ID);

CREATE INDEX contacts_index ON contacts USING HASH (ID);

CREATE INDEX lessons_index_on_time ON lessons (start_time);
CREATE INDEX lessons_index_on_ID ON lessons USING HASH (ID);

CREATE INDEX subjects_index ON subjects USING HASH (ID);

CREATE INDEX student_lessons_requests_index ON student_lessons_requests USING HASH (ID);

CREATE INDEX tutor_lessons_requests_index ON tutor_lessons_requests USING HASH (ID);