CREATE INDEX chats_index_on_users ON chats (user1_id, user2_id);

CREATE INDEX messages_index_on_chat ON messages USING HASH (chat_id);

CREATE INDEX users_index_on_ID ON users USING HASH (ID);
CREATE INDEX users_index_on_human_readable_id ON users USING HASH (human_readable_id);
CREATE INDEX users_index_on_login ON users USING HASH (login);

CREATE INDEX users_roles_index_on_user_id ON users_roles USING HASH (user_id);

CREATE INDEX roles_index_on_ID ON roles USING HASH (ID);
CREATE INDEX roles_index_on_role_name ON roles USING HASH (role_name);

CREATE INDEX users_lessons_index_on_user_id ON users_lessons USING HASH (user_id);
CREATE INDEX users_lessons_index_on_lesson_id ON users_lessons USING HASH (lesson_id);

CREATE INDEX lessons_index_on_start_time ON lessons (start_time);
CREATE INDEX lessons_index_on_subject_id ON lessons USING HASH (subject_id);
CREATE INDEX lessons_index_on_ID ON lessons USING HASH (ID);
CREATE INDEX lessons_index_on_human_readable_id ON lessons USING HASH (human_readable_id);

CREATE INDEX subjects_index_on_main_name ON subjects USING HASH (main_name);

CREATE INDEX lessons_requests_index_on_sender_id ON lessons_requests USING HASH (sender_id);
CREATE INDEX lessons_requests_index_on_reciever_id ON lessons_requests USING HASH (reciever_id);

CREATE INDEX request_types_index_on_ID ON request_types USING HASH (ID);
CREATE INDEX request_types_index_on_request_type ON request_types USING HASH (request_type);