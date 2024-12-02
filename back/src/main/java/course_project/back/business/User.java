package course_project.back.business;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import course_project.back.enums.UserRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_tutor_grade_book")
public class User extends UserAbstract {

    public User(String name,
            String secondName,
            String email,
            String phone,
            String description,
            String humanReadableID,
            String password,
            Set<UserRoles> roles) {
        super(name, secondName, email, phone, description, humanReadableID, password, roles);
    }

    public User(String name,
            String humanReadableID,
            String login,
            String password,
            Set<UserRoles> roles) {
        super(name, humanReadableID, password, roles);
    }

    public User() {
    }

    public User(Map<String, String[]> json) {
        this(json.get("name")[0],
                json.get("secondName")[0],
                json.get("email")[0],
                json.get("phone")[0],
                "", //json.get("description")[0],
                "",
                json.get("password")[0], new HashSet<UserRoles>());

        Set<UserRoles> roles = new HashSet<>();
        String[] rolesStr = json.get("roles");
        for (String role : rolesStr) {
            roles.add(UserRoles.fromString(role));
        }
        this.setRoles(roles);
    }

    public void setDefaultHumanRedableID() {
        setHumanReadableID(getName() + "_" + getSecondName() + "_" + getId());
    }
}
