package course_project.back.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipatorDTO {
    @NonNull
    private String name;
    @NonNull
    private String secondName;
    @NonNull
    private String email;
    private String phone;
    private String description;
    @NonNull
    private String humanReadableID;
    @NonNull
    private List<String> roles;
}
