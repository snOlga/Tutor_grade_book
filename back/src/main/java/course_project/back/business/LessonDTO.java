package course_project.back.business;

import java.sql.Timestamp;
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
}
