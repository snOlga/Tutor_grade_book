package course_project.back.business;

import java.sql.Timestamp;

import course_project.back.orms.LessonORM;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;
    private Timestamp startTime;
    private Integer duration;
    private Long subjectId;
    private String homework;
    private Boolean isOpen;
    private Boolean isDeleted;
    private String description;
    private String humanReadableId;
    private String heading;

    public LessonDTO(LessonORM lessonORM) {
        this.id = lessonORM.getId();
        this.startTime = lessonORM.getStartTime();
        this.duration = lessonORM.getDuration();
        this.subjectId = lessonORM.getSubjectId();
        this.homework = lessonORM.getHomework();
        this.isOpen = lessonORM.getIsOpen();
        this.isDeleted = lessonORM.getIsDeleted();
        this.description = lessonORM.getDescription();
        this.humanReadableId = lessonORM.getHumanReadableId();
        this.heading = lessonORM.getHeading();
    }

}
