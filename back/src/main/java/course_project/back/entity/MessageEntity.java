package course_project.back.entity;

import java.sql.Timestamp;
import java.util.UUID;

// import org.hibernate.annotations.Where;  

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
// @Where(clause = "is_deleted = false")
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

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
    private Boolean isDeleted = Boolean.FALSE;
}
