package course_project.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import course_project.back.entity.LessonEntity;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {

        @Query("""
                        SELECT l FROM LessonEntity l
                        JOIN l.users u
                        WHERE u.email = :email
                          AND u.isDeleted = false
                          AND l.owner.isDeleted = false
                          AND l.isDeleted = false
                          AND l.startTime >= :startDate
                          AND l.startTime < :endDate
                        """)
        List<LessonEntity> findAllByUserEmail(
                        @Param("email") String email,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

        @Query("SELECT l FROM LessonEntity l " +
                        "JOIN l.users u " +
                        "WHERE u.isDeleted = false " +
                        "AND l.owner.isDeleted = false " +
                        "AND l.isDeleted = false " +
                        "AND l.isOpen = true " +
                        "AND l.subject.id = :subjectId " +
                        "AND l.startTime >= :startDate " +
                        "AND l.startTime < :endDate")
        List<LessonEntity> findAllBySubjectId(
                        @Param("subjectId") Long subjectId,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

}
