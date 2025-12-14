package course_project.back.entity;

import java.util.Set;

// import org.hibernate.annotations.Where;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_tutor_grade_book")
// @Where(clause = "is_deleted = false")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;

    @Nonnull
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @Nonnull
    @Column(name = "human_readable_id", unique = true)
    private String humanReadableID;

    @Nonnull
    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<RoleEntity> roles;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public void setDefaultHumanRedableID() {
        setHumanReadableID(getName() + "_" + getSecondName() + "_" + getId());
    }
}
