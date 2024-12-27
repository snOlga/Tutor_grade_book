package course_project.back.entity;

import jakarta.persistence.*;

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

    public UsersLessonsORM(UserEntity user, LessonORM lesson) {
        this.id = new UsersLessonsId(user, lesson);
    }
}
