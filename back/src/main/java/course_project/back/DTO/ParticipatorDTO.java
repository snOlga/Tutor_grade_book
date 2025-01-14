package course_project.back.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipatorDTO {
    private String name;
    private String secondName;
    private String email;
    private String phone;
    private String description;
    private String humanReadableID;
    private List<String> roles;
}
