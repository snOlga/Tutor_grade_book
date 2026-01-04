package course_project.back.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonUserUpdateDTO {
    private Long lessonId;
    private Long senderId;
    private Long recieverId;
}
