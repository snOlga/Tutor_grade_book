CREATE INDEX chats_index ON chats USING HASH (ID);

CREATE INDEX creds_index ON creds USING HASH (ID);

CREATE INDEX user_info_index ON user_info USING HASH (ID);

CREATE INDEX students_index_ID ON students USING HASH (ID);
CREATE INDEX student_index_on_human_readable_id ON students USING HASH (human_readable_id);

CREATE INDEX tutors_index ON tutors USING HASH (ID);

CREATE INDEX contacts_index ON contacts USING HASH (ID);

CREATE INDEX lessons_index_on_time ON lessons (start_time);
CREATE INDEX lessons_index_on_tutor_id ON lessons USING HASH (tutor_id);
CREATE INDEX lessons_index_on_subject_id ON lessons USING HASH (subject_id);
CREATE INDEX lessons_index_on_ID ON lessons USING HASH (ID);
CREATE INDEX lessons_index_on_human_readable_id ON lessons USING HASH (human_readable_id);

CREATE INDEX subjects_index ON subjects USING HASH (ID);

CREATE INDEX student_lessons_requests_index ON student_lessons_requests USING HASH (ID);

CREATE INDEX tutor_lessons_requests_index ON tutor_lessons_requests USING HASH (ID);