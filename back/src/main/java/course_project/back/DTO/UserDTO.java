package course_project.back.DTO;

import java.util.Set;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String secondName;
    private String email;
    private String phone;
    private String description;
    private String humanReadableID;
    private String password;
    private Set<String> roles;
}
