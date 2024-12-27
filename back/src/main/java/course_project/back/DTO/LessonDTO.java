package course_project.back.DTO;

import java.sql.Timestamp;

import course_project.back.entity.LessonEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;
    private Timestamp startTime;
    private Integer duration;
    private SubjectDTO subject;
    private String homework;
    private Boolean isOpen;
    private Boolean isDeleted;
    private String description;
    private String humanReadableId;
    private String heading;

    public LessonDTO(LessonEntity lessonORM) {
        this.id = lessonORM.getId();
        this.startTime = lessonORM.getStartTime();
        this.duration = lessonORM.getDurationInMinutes();
        this.subject = new SubjectDTO(lessonORM.getSubject());
        this.homework = lessonORM.getHomework();
        this.isOpen = lessonORM.getIsOpen();
        this.isDeleted = lessonORM.getIsDeleted();
        this.description = lessonORM.getDescription();
        this.humanReadableId = lessonORM.getHumanReadableId();
        this.heading = lessonORM.getHeading();
    }

}
