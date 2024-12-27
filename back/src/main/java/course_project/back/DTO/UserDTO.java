package course_project.back.DTO;

import java.util.Map;
import java.util.Set;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String secondName;
    private String email;
    private String phone;
    private String description;
    private String humanReadableID;
    private String password;
    private Set<String> roles;

    public UserDTO(Map<String, String[]> json) {
        this.name = json.get("name")[0];
        this.secondName = json.get("secondName")[0];
        this.email = json.get("email")[0];
        this.phone = json.get("phone")[0];
        this.password = json.get("password")[0];
        this.humanReadableID = "";

        Set<String> roles = Set.of(json.get("roles"));
        this.setRoles(roles);
    }
}
