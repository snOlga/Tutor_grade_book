SELECT * FROM lessons;
DELETE FROM lessons WHERE ID = 1;
SELECT * FROM lessons;
SELECT * FROM lessons_requests;
DELETE FROM lessons_requests WHERE ID = 1;
SELECT * FROM lessons_requests;

SELECT * FROM users_lessons;
UPDATE lessons_requests SET is_approved = true WHERE ID = 1;
SELECT * FROM users_lessons;
UPDATE lessons_requests SET is_approved = true WHERE ID = 2;
SELECT * FROM lessons_requests;
SELECT * FROM users_lessons;

SELECT * FROM messages;
UPDATE messages SET msg_text = 'Hello, Jane! :)' WHERE ID = 1;
SELECT * FROM messages;

SELECT * FROM lessons;
SELECT * FROM lessons_requests;
EXECUTE insert_into_lessons_requests(false, 3, 2, 1, 2, false);
SELECT * FROM lessons_requests;