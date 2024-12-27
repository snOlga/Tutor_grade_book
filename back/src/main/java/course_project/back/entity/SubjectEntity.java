package course_project.back.entity;

import course_project.back.DTO.SubjectDTO;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subjects")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @Column(name = "main_name")
    private String name;

    @Column(name = "analogy_names")
    private String analogyNames;

    public SubjectEntity(SubjectDTO subjectDTO) {
        this.id = subjectDTO.getId();
        this.name = subjectDTO.getName();
        this.analogyNames = subjectDTO.getAnalogyNames();
    }
}
