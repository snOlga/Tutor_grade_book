package course_project.back.DTO;

import course_project.back.entity.LessonRequestEntity;
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

    public LessonRequestDTO(LessonRequestEntity lessonRequestEntity) {
        this.id = lessonRequestEntity.getId();
        this.isApproved = lessonRequestEntity.getIsApproved();
        this.isDeleted = lessonRequestEntity.getIsDeleted();
        this.sender = new ParticipatorDTO(lessonRequestEntity.getSender());
        this.reciever = new ParticipatorDTO(lessonRequestEntity.getReciever());
        this.lesson = new LessonDTO(lessonRequestEntity.getLesson());
    }
}
