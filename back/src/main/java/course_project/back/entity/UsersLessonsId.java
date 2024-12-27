package course_project.back.entity;

import java.io.Serializable;

import jakarta.persistence.*;

import lombok.*;

@Data
@Embeddable
public class UsersLessonsId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private LessonORM lesson;

    // Конструкторы, геттеры и сеттеры

    public UsersLessonsId() {}

    public UsersLessonsId(UserEntity user, LessonORM lesson) {
        this.user = user;
        this.lesson = lesson;
    }
}