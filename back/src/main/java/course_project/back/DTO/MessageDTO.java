package course_project.back.DTO;

import java.sql.Timestamp;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private ChatDTO chat;
    private ParticipatorDTO author;
    private Timestamp sentTime;
    private Boolean isEdited;
    private String text;
    private Boolean isDeleted;
}
