package course_project.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course_project.back.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    
}
