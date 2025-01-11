package course_project.back.DTO;

import java.util.HashSet;
import java.util.Set;

import course_project.back.entity.ChatEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private Set<ParticipatorDTO> users;

    public ChatDTO(ChatEntity chatEntity) {
        this.id = chatEntity.getId();
        this.users = new HashSet<>(chatEntity.getUsers().stream().map(ParticipatorDTO::new).toList());
    }
}
