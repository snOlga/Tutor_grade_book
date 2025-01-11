package course_project.back.entity;

import jakarta.persistence.*;
import java.util.Set;

import course_project.back.DTO.ChatDTO;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "users_chats", joinColumns = { @JoinColumn(name = "chat_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private Set<UserEntity> users;

    public ChatEntity(ChatDTO chatDTO) {
        this.id = chatDTO.getId();
    }
}
