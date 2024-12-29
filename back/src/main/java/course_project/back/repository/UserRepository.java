package course_project.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import course_project.back.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.email = :currentEmail")
    public UserEntity findByEmail(@Param("currentEmail") String currentEmail);

    // @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.human_readable_id = :hr_id")
    public UserEntity findByHumanReadableID(String hr_id);
}
