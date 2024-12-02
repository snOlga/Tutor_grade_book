package main.java.course_project.back.repositories;

import main.java.course_project.back.orms.LessonORM;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<LessonORM, Long> {
    //в этом интерфейсе уже есть все методы для жопа репозиториев
}
