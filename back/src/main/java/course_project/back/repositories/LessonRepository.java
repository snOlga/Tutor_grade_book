package course_project.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import course_project.back.orms.LessonORM;

@Repository
public interface LessonRepository extends JpaRepository<LessonORM, Long> {
    //в этом интерфейсе уже есть все методы для жопа репозиториев
}
