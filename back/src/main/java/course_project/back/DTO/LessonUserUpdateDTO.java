package course_project.back.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonUserUpdateDTO {
    private UUID lessonId;
    private UUID senderId;
    private UUID recieverId;
}
