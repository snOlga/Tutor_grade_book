package course_project.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import course_project.back.orms.LessonORM;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonORM, Long> {
    List<LessonORM> findByHumanReadableId(String humanReadableId);

    @Query("SELECT l.usersLessons FROM LessonORM l WHERE l.humanReadableId = :hr_id")
    List<LessonORM> findLessonsByUserHumanReadableId(@Param("hr_id") String hr_id);

    List<LessonORM> findByIdIn(List<Long> ids);
}
