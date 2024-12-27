package course_project.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import course_project.back.entity.LessonEntity;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findByHumanReadableId(String humanReadableId);

    @Query(nativeQuery = true, value ="SELECT l.* FROM lessons l JOIN users_lessons ul ON l.id = ul.lesson_id JOIN users_tutor_grade_book u ON ul.user_id = u.id WHERE u.email = ?1")
    List<LessonEntity> findAllByUserEmail(@Param("email") String email);


    List<LessonEntity> findByIdIn(List<Long> ids);
}
