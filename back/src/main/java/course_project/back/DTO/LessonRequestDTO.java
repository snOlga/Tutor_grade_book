package course_project.back.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequestDTO {
    private String id;
    private Boolean isApproved;
    private Boolean isDeleted;
    @NonNull
    private ParticipatorDTO sender;
    @NonNull
    private ParticipatorDTO reciever;
    private LessonDTO lesson;
}
