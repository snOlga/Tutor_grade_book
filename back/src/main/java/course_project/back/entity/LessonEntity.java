package course_project.back.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

// import org.hibernate.annotations.Where;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
// @Where(clause = "is_deleted = false")
@Table(name = "lessons")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "duration_in_minutes")
    private Integer durationInMinutes;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @Column(name = "homework")
    private String homework;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "description")
    private String description;

    @Column(name = "human_readable_id")
    private String humanReadableId;

    @Column(name = "heading")
    private String heading;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "users_lessons", joinColumns = { @JoinColumn(name = "lesson_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private Set<UserEntity> users;

    public void setDefaultHumanRedableID() {
        setHumanReadableId(getHeading() + "_" + getOwner().getName() + "_" + getId());
    }
}
