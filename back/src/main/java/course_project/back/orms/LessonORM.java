package course_project.back.orms;

import course_project.back.business.LessonDTO;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class LessonORM {

    public LessonORM(LessonDTO lessonDTO) {
        this.id = lessonDTO.getId();
        this.startTime = lessonDTO.getStartTime();
        this.duration = lessonDTO.getDuration();
        this.subjectId = lessonDTO.getSubjectId();
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
    private Integer duration;
    // В минутах

    @Column(name = "subject_id")
    private Long subjectId; // Предполагаем, что это внешний ключ

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

    @OneToMany(mappedBy = "id.lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UsersLessonsORM> usersLessons;
}
