CREATE OR REPLACE FUNCTION instead_of_delete_lesson()
RETURNS TRIGGER AS $$
	BEGIN
		UPDATE lessons SET is_deleted = TRUE WHERE ID = OLD.ID;
		RETURN NULL;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_instead_of_delete_lesson
BEFORE DELETE ON lessons
FOR EACH ROW
EXECUTE FUNCTION instead_of_delete_lesson();



CREATE OR REPLACE FUNCTION instead_of_delete_lesson_request()
RETURNS TRIGGER AS $$
	BEGIN
		UPDATE lessons_requests SET is_deleted = TRUE WHERE ID = OLD.ID;
		RETURN NULL;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_instead_of_delete_lesson_request
BEFORE DELETE ON lessons_requests
FOR EACH ROW
EXECUTE FUNCTION instead_of_delete_lesson_request();



CREATE OR REPLACE FUNCTION update_users_lessons_from_sender_request()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM users_lessons WHERE user_id = NEW.sender_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO users_lessons (user_id, lesson_id) VALUES
				(NEW.sender_id, NEW.lesson_id);
			DELETE FROM lessons_requests WHERE ID = NEW.ID;
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_update_users_lessons_from_sender_request
AFTER UPDATE OF is_approved ON lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_users_lessons_from_sender_request();



CREATE OR REPLACE FUNCTION update_users_lessons_from_reciever_request()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM users_lessons WHERE user_id = NEW.reciever_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO users_lessons (user_id, lesson_id) VALUES
				(NEW.reciever_id, NEW.lesson_id);
			DELETE FROM lessons_requests WHERE ID = NEW.ID;
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_update_users_lessons_from_reciever_request
AFTER UPDATE OF is_approved ON lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_users_lessons_from_reciever_request();


CREATE OR REPLACE FUNCTION prevent_duplicate_user_lesson()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM users_lessons 
        WHERE user_id = NEW.user_id AND lesson_id = NEW.lesson_id
    ) THEN
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_prevent_duplicate_user_lesson
BEFORE INSERT ON users_lessons
FOR EACH ROW
EXECUTE FUNCTION prevent_duplicate_user_lesson();



CREATE OR REPLACE FUNCTION update_edited_message()
RETURNS TRIGGER AS $$
BEGIN
    NEW.is_edited = true;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_update_edited_message
BEFORE UPDATE OF msg_text 
ON messages
FOR EACH ROW
EXECUTE FUNCTION update_edited_message();



CREATE OR REPLACE FUNCTION check_if_lesson_not_deleted()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (
        SELECT * FROM lessons 
        WHERE ID = NEW.lesson_id AND is_deleted = true
    ) THEN
		--RAISE EXCEPTION 'Lesson is deleted';
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_on_check_if_lesson_not_deleted
BEFORE INSERT ON lessons_requests
FOR EACH ROW
EXECUTE FUNCTION check_if_lesson_not_deleted();