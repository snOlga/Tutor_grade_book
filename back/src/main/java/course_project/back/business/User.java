package course_project.back.business;

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
            String login,
            String password,
            Set<UserRoles> roles) {
        super(name, secondName, email, phone, description, humanReadableID, login, password, roles);
    }

    public User(String name,
            String humanReadableID,
            String login,
            String password,
            Set<UserRoles> roles) {
        super(name, humanReadableID, login, password, roles);
    }

    public void setDefaultHumanRedableID() {
        setHumanReadableID(getName() + "_" + getSecondName() + "_" + getId());
    }
}
