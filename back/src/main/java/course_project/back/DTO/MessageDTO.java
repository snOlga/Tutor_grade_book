package course_project.back.DTO;

import java.sql.Timestamp;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    @NonNull
    private ChatDTO chat;
    @NonNull
    private ParticipatorDTO author;
    @NonNull
    private Timestamp sentTime;
    private Boolean isEdited;
    @NonNull
    private String text;
    private Boolean isDeleted;
}
