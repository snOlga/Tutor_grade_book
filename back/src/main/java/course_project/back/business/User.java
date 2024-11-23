package course_project.back.business;

import java.util.Set;

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
            Set roles) {
        super(name, secondName, email, phone, description, humanReadableID, login, password, roles);
    }

    public User(String name,
            String email,
            String login,
            String password,
            Set roles) {
        super(name, email, login, password, roles);
    }

    public User(String name,
            String login,
            String password,
            Set roles) {
        super(name, login, password, roles);
    }
}
