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