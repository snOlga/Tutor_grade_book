package course_project.back.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.Where;

import course_project.back.DTO.MessageDTO;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "is_deleted = false")
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Nonnull
    @Column(name = "sent_time")
    private Timestamp sentTime;

    @Nonnull
    @Column(name = "is_edited")
    private Boolean isEdited;

    @Nonnull
    @Column(name = "msg_text")
    private String text;

    @Nonnull
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public MessageEntity(MessageDTO messageDTO) {
        this.id = messageDTO.getId();
        this.sentTime = messageDTO.getSentTime();
        this.isEdited = messageDTO.getIsEdited();
        this.text = messageDTO.getText();
        this.isDeleted = messageDTO.getIsDeleted();
    }
}
