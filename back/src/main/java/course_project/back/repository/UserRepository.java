package course_project.back.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import course_project.back.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.roles " +
            "WHERE u.email = :currentEmail " +
            "AND u.isDeleted = false")
    UserEntity findByEmail(String currentEmail);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.roles " +
            "WHERE u.humanReadableID = :humanReadableID " +
            "AND u.isDeleted = false")
    UserEntity findByHumanReadableID(String humanReadableID);
}
