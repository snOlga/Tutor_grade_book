package course_project.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import course_project.back.entity.LessonRequestEntity;

@Repository
public interface LessonRequestRepository extends JpaRepository<LessonRequestEntity, Long> {

}
