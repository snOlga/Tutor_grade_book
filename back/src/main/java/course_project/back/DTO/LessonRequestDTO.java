package course_project.back.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequestDTO {
    private Long id;
    private Boolean isApproved;
    private Boolean isDeleted;
    private ParticipatorDTO sender;
    private ParticipatorDTO reciever;
    private LessonDTO lesson;
}
