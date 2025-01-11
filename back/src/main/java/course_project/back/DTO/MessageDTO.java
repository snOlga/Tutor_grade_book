package course_project.back.DTO;

import java.sql.Timestamp;

import course_project.back.entity.MessageEntity;
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

    public MessageDTO(MessageEntity messageEntity) {
        this.id = messageEntity.getId();
        this.chat = new ChatDTO(messageEntity.getChat());
        this.author = new ParticipatorDTO(messageEntity.getAuthor());
        this.sentTime = messageEntity.getSentTime();
        this.isEdited = messageEntity.getIsEdited();
        this.text = messageEntity.getText();
        this.isDeleted = messageEntity.getIsDeleted();
    }
}
