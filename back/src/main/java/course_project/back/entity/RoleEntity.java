package course_project.back.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {
    public static String ROLE_ADMIN = "ADMIN";
    public static String ROLE_TUTOR = "TUTOR";
    public static String ROLE_STUDENT = "STUDENT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @Column(name = "name")
    private String name;
}
