package course_project.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import course_project.back.entity.LessonRequestEntity;

@Repository
public interface LessonRequestRepository extends JpaRepository<LessonRequestEntity, Long> {

        @Query("SELECT lr FROM LessonRequestEntity lr " +
                        "WHERE lr.reciever.email = :email " +
                        "AND lr.sender.isDeleted = false " +
                        "AND lr.reciever.isDeleted = false " +
                        "AND lr.lesson.isDeleted = false " +
                        "AND lr.isDeleted = false")
        List<LessonRequestEntity> findByRecieverEmail(String email);

        @Query("SELECT lr FROM LessonRequestEntity lr " +
                        "WHERE lr.sender.email = :email " +
                        "AND lr.sender.isDeleted = false " +
                        "AND lr.reciever.isDeleted = false " +
                        "AND lr.lesson.isDeleted = false " +
                        "AND lr.isDeleted = false")
        List<LessonRequestEntity> findAllBySenderEmail(String email);
}
