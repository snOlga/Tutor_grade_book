package course_project.back.repositories;

import course_project.back.business.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByHumanReadableID(String humanReadableID);
}
