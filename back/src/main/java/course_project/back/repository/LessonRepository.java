package course_project.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import course_project.back.entity.LessonEntity;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    @Query("SELECT l FROM LessonEntity l " +
            "JOIN l.users u " +
            "WHERE u.email = :email " +
            "AND u.isDeleted = false " +
            "AND l.owner.isDeleted = false " +
            "AND l.isDeleted = false")
    List<LessonEntity> findAllByUserEmail(String email);

    @Query("SELECT l FROM LessonEntity l " +
            "JOIN l.users u " +
            "WHERE u.isDeleted = false " +
            "AND l.owner.isDeleted = false " +
            "AND l.isDeleted = false " +
            "AND l.isOpen = true " +
            "AND l.subject.id = :subjectId")
    List<LessonEntity> findAllBySubjectId(Long subjectId);
}
