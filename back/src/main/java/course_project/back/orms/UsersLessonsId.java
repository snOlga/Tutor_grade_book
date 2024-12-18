package course_project.back.orms;

import java.io.Serializable;

import course_project.back.business.*;
import jakarta.persistence.*;

import lombok.*;

@Data
@Embeddable
public class UsersLessonsId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private LessonORM lesson;

    // Конструкторы, геттеры и сеттеры

    public UsersLessonsId() {}

    public UsersLessonsId(User user, LessonORM lesson) {
        this.user = user;
        this.lesson = lesson;
    }
}