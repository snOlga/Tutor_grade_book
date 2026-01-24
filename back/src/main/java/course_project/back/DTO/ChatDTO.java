package course_project.back.DTO;

import java.util.Set;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private String id;
    private Set<ParticipatorDTO> users;
    private Boolean isDeleted = false;
}
