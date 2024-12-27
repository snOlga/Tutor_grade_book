package course_project.back.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import course_project.back.DTO.LessonDTO;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class LessonEntity {

    public LessonEntity(LessonDTO lessonDTO) {
        this.id = lessonDTO.getId();
        this.startTime = lessonDTO.getStartTime();
        this.durationInMinutes = lessonDTO.getDuration();
        this.subject = new SubjectEntity(lessonDTO.getSubject());
        this.homework = lessonDTO.getHomework();
        this.isOpen = lessonDTO.getIsOpen();
        this.isDeleted = lessonDTO.getIsDeleted();
        this.heading = lessonDTO.getHeading();
        this.humanReadableId = lessonDTO.getHumanReadableId();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "duration")
    private Integer durationInMinutes;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @Column(name = "homework")
    private String homework;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "description")
    private String description;

    @Column(name = "human_readable_id")
    private String humanReadableId;

    @Column(name = "heading")
    private String heading;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "users_lessons", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "lesson_id") })
    private Set<UserEntity> users;
}
