package course_project.back.entity;

import org.hibernate.annotations.Where;

import course_project.back.DTO.LessonRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "is_approved = false AND is_deleted = false")
@Table(name = "lessons_requests")
public class LessonRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "reciever_id")
    private UserEntity reciever;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;

    public LessonRequestEntity(LessonRequestDTO lessonRequestDTO) {
        this.id = lessonRequestDTO.getId();
        this.isApproved = lessonRequestDTO.getIsApproved();
        this.isDeleted = lessonRequestDTO.getIsDeleted();
        this.lesson = new LessonEntity(lessonRequestDTO.getLesson());
    }

    public LessonRequestEntity(Boolean isApproved, Boolean isDeleted, UserEntity sender, LessonEntity lesson) {
        this.isApproved = isApproved;
        this.sender = sender;
        this.lesson = lesson;
        this.isDeleted = isDeleted;
    }

    @Override
    public LessonRequestEntity clone() {
        return new LessonRequestEntity(this.id, this.isApproved, this.isDeleted, this.sender, this.reciever, this.lesson);
    }
}
