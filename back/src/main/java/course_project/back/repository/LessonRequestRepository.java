package course_project.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import course_project.back.entity.LessonRequestEntity;

@Repository
public interface LessonRequestRepository extends JpaRepository<LessonRequestEntity, Long> {

    List<LessonRequestEntity> findAllByReciever_EmailAndSender_IsDeletedFalseAndReciever_IsDeletedFalseAndLesson_IsDeletedFalse(
            String email);

    List<LessonRequestEntity> findAllBySender_EmailAndSender_IsDeletedFalseAndReciever_IsDeletedFalseAndLesson_IsDeletedFalse(
            String email);
}
