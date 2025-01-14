package course_project.back.DTO;

import java.sql.Timestamp;
import java.util.List;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;
    private Timestamp startTime;
    private Integer durationInMinutes;
    private SubjectDTO subject;
    private String homework;
    private Boolean isOpen;
    private Boolean isDeleted;
    private String description;
    private String humanReadableId;
    private String heading;
    private ParticipatorDTO owner;
    private List<ParticipatorDTO> users;
}
