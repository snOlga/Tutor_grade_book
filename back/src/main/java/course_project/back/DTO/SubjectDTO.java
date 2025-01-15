package course_project.back.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    @NonNull
    private String name;
    private String analogyNames;
}
