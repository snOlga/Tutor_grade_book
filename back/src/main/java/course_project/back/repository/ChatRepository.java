package course_project.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import course_project.back.entity.ChatEntity;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("SELECT c FROM ChatEntity c " +
            "JOIN c.users u " +
            "WHERE u.email = :email AND c.isDeleted = false")
    List<ChatEntity> findAllByUserEmail(String email);
}
