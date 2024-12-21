package course_project.back.repositories;

import course_project.back.business.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :currentEmail")
    public User findByEmail(@Param("currentEmail") String currentEmail);

    // @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.human_readable_id = :hr_id")
    public User findByHumanReadableID(String hr_id);
}
