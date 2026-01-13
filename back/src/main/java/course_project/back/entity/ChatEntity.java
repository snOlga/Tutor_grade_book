package course_project.back.entity;

import jakarta.persistence.*;
import java.util.Set;

// import org.hibernate.annotations.Where;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
// @Where(clause = "is_deleted = false")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "users_chats", joinColumns = { @JoinColumn(name = "chat_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private Set<UserEntity> users;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}
