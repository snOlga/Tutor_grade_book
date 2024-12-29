package course_project.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import course_project.back.entity.LessonEntity;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    LessonEntity findByHumanReadableId(String humanReadableId);

    List<LessonEntity> findByUsers_Email(String email);

    List<LessonEntity> findByIdIn(List<Long> ids);
}
