package course_project.back.DTO;

import course_project.back.entity.SubjectEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String name;
    private String analogyNames;

    public SubjectDTO(SubjectEntity subjectEntity) {
        this.id = subjectEntity.getId();
        this.name = subjectEntity.getName();
        this.analogyNames = subjectEntity.getAnalogyNames();
    }
}
