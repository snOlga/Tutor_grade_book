package course_project.back.entity;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Column(name = "sent_time")
    private Timestamp sentTime;

    @Column(name = "is_edited")
    private Boolean isEdited;

    @Column(name = "msg_text")
    private String text;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;
}
