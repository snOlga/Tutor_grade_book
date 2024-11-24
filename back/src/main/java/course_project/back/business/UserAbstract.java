package course_project.back.business;

import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import course_project.back.converters.UserRoleConverter;
import course_project.back.enums.UserRoles;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.UniqueConstraint;;

@MappedSuperclass
public abstract class UserAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Nonnull
    @Column(name = "name")
    private String name;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "description")
    private String description;
    @Nonnull
    @Column(name = "human_readable_id", unique = true)
    private String humanReadableID;
    @Nonnull
    @Column(name = "login", unique = true)
    private String login;
    @Nonnull
    @Column(name = "password")
    private String password;
    @ElementCollection(targetClass = UserRoles.class)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private Set<UserRoles> roles;

    public UserAbstract(String name,
            String secondName,
            String email,
            String phone,
            String description,
            String humanReadableID,
            String login,
            String password,
            Set<UserRoles> roles) {
        this.name = name;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.humanReadableID = humanReadableID;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public UserAbstract(String name,
            String humanReadableID,
            String login,
            String password,
            Set<UserRoles> roles) {
        this.name = name;
        this.humanReadableID = humanReadableID;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHumanReadableID(String humanReadableID) {
        this.humanReadableID = humanReadableID;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set roles) {
        this.roles = roles;
    }

    // ------------------------------------------------------

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getHumanReadableID() {
        return humanReadableID;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }
}
