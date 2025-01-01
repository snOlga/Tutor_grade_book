package course_project.back.DTO;

import java.sql.Timestamp;
import java.util.List;

import course_project.back.entity.LessonEntity;
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

    public LessonDTO(LessonEntity lessonEntity) {
        this.id = lessonEntity.getId();
        this.startTime = lessonEntity.getStartTime();
        this.durationInMinutes = lessonEntity.getDurationInMinutes();
        this.subject = new SubjectDTO(lessonEntity.getSubject());
        this.homework = lessonEntity.getHomework();
        this.isOpen = lessonEntity.getIsOpen();
        this.isDeleted = lessonEntity.getIsDeleted();
        this.description = lessonEntity.getDescription();
        this.humanReadableId = lessonEntity.getHumanReadableId();
        this.heading = lessonEntity.getHeading();
        this.owner = new ParticipatorDTO(lessonEntity.getOwner());
        this.users = lessonEntity.getUsers().stream().map(ParticipatorDTO::new).toList();
    }
}
