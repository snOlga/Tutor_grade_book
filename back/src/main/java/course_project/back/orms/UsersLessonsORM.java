package course_project.back.orms;

import course_project.back.business.LessonDTO;
import course_project.back.business.User;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_lessons")

public class UsersLessonsORM {
    @EmbeddedId
    private UsersLessonsId id;
    // Связь с Use

    public UsersLessonsORM(User user, LessonORM lesson) {
        this.id = new UsersLessonsId(user, lesson);
    }
}
