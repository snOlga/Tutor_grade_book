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
    @NonNull
    private Timestamp startTime;
    @NonNull
    private Integer durationInMinutes;
    @NonNull
    private SubjectDTO subject;
    private String homework;
    @NonNull
    private Boolean isOpen;
    private Boolean isDeleted;
    private String description;
    @NonNull
    private String humanReadableId;
    @NonNull
    private String heading;
    @NonNull
    private ParticipatorDTO owner;
    @NonNull
    private List<ParticipatorDTO> users;
}
