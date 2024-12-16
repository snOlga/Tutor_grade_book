package course_project.back.models;

import java.sql.Timestamp;

import course_project.back.orms.LessonORM;
import lombok.Data;

@Data
public class LessonDTO {
    private Long id;
    private Timestamp startTime;
    private String duration;
    private Long subjectId;
    private String homework;
    private Boolean isOpen;
    private Boolean sActive;
    private String description;
    private String humanReadableId;

    public LessonDTO(LessonORM orm) {}
}
