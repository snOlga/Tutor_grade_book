CREATE OR REPLACE FUNCTION update_student_lessons_from_tutor()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM students_lessons WHERE student_id = NEW.student_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO students_lessons (student_id, lesson_id) VALUES
				(NEW.student_id, NEW.lesson_id);
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_approving_lesson_by_student
AFTER UPDATE OF is_approved ON tutor_lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_student_lesson_from_tutor();



CREATE OR REPLACE FUNCTION update_student_lesson_from_student()
RETURNS TRIGGER AS $$
	BEGIN
		IF NOT EXISTS (
			SELECT * FROM students_lessons WHERE student_id = NEW.student_id AND lesson_id = NEW.lesson_id
			)
		AND (NEW.is_approved = TRUE)
		THEN	
			INSERT INTO students_lessons (student_id, lesson_id) VALUES
				(NEW.student_id, NEW.lesson_id);
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_approving_lesson_by_tutor
AFTER UPDATE OF is_approved ON student_lessons_requests
FOR EACH ROW
WHEN (NEW.is_approved = TRUE)
EXECUTE FUNCTION update_student_lesson_from_student();



CREATE OR REPLACE FUNCTION insert_lesson_if_not_exists()
RETURNS TRIGGER AS $$
	BEGIN
		IF EXISTS (
			SELECT * FROM lessons WHERE tutor_id = NEW.tutor_id AND 
				(((start_time + duration) > NEW.start_time) AND 
					(start_time < NEW.start_time) OR 
					((start_time + duration) > (NEW.start_time + duration)) AND
					(start_time >= NEW.start_time))
			)
		THEN
			RAISE EXCEPTION 'At this time lesson already exists';
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_lesson_if_not_exists
BEFORE INSERT
ON lessons
FOR EACH ROW
EXECUTE FUNCTION insert_lesson_if_not_exists();



CREATE OR REPLACE FUNCTION instead_of_delete_lesson()
RETURNS TRIGGER AS $$
	BEGIN
		UPDATE lessons SET is_active = FALSE WHERE ID = OLD.ID;
		RETURN NULL;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_lesson
BEFORE DELETE ON lessons
FOR EACH ROW
EXECUTE FUNCTION instead_of_delete_lesson();




