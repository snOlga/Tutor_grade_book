package course_project.back.entity;

import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_tutor_grade_book")
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
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<RoleEntity> roles;

    public void setDefaultHumanRedableID() {
        setHumanReadableID(getName() + "_" + getSecondName() + "_" + getId());
    }

    public UserEntity(String name, String secondName, String email, String phone, String description,
            String humanReadableID, String password, Set<RoleEntity> roles) {
        this.name = name;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.humanReadableID = humanReadableID;
        this.password = password;
        this.roles = roles;
    }
}
