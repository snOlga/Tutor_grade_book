package course_project.back.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.Where;

import course_project.back.DTO.LessonDTO;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "is_deleted = false")
@Table(name = "lessons")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @Column(name = "start_time")
    private Timestamp startTime;

    @Nonnull
    @Column(name = "duration_in_minutes")
    private Integer durationInMinutes;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @Column(name = "homework")
    private String homework;

    @Nonnull
    @Column(name = "is_open")
    private Boolean isOpen;

    @Nonnull
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "description")
    private String description;

    @Column(name = "human_readable_id")
    private String humanReadableId;

    @Nonnull
    @Column(name = "heading")
    private String heading;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "users_lessons", joinColumns = { @JoinColumn(name = "lesson_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private Set<UserEntity> users;

    public LessonEntity(LessonDTO lessonDTO) {
        this.id = lessonDTO.getId();
        this.startTime = lessonDTO.getStartTime();
        this.durationInMinutes = lessonDTO.getDurationInMinutes();
        this.subject = new SubjectEntity(lessonDTO.getSubject());
        this.homework = lessonDTO.getHomework();
        this.isOpen = lessonDTO.getIsOpen();
        this.isDeleted = lessonDTO.getIsDeleted();
        this.description = lessonDTO.getDescription();
        this.heading = lessonDTO.getHeading();
        this.humanReadableId = lessonDTO.getHumanReadableId();
    }
}
