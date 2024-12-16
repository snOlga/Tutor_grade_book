package course_project.back.orms;

import jakarta.persistence.*;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
@Table(name = "lessons")
public class LessonORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "duration")
    private String duration; // Можно использовать Duration, но в JPA лучше хранить как String или Interval

    @Column(name = "subject_id")
    private Long subjectId; // Предполагаем, что это внешний ключ

    @Column(name = "homework")
    private String homework;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "s_active")
    private Boolean sActive;

    @Column(name = "description")
    private String description;

    @Column(name = "human_readable_id")
    private String humanReadableId;
}
