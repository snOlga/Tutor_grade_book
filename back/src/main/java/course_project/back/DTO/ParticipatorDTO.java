package course_project.back.DTO;

import java.util.List;

import course_project.back.entity.UserEntity;
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

    public ParticipatorDTO(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.secondName = userEntity.getSecondName();
        this.email = userEntity.getEmail();
        this.phone = userEntity.getPhone();
        this.description = userEntity.getDescription();
        this.humanReadableID = userEntity.getHumanReadableID();
        this.roles = userEntity.getRoles().stream().map(role -> role.getName()).toList();
    }
}
