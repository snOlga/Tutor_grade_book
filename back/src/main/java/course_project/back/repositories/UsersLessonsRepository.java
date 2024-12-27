package course_project.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import course_project.back.entity.UsersLessonsId;
import course_project.back.entity.UsersLessonsORM;

import java.util.List;

@Repository
public interface UsersLessonsRepository extends JpaRepository<UsersLessonsORM, UsersLessonsId> {
    List<UsersLessonsORM> findById_User_Id(Long id);
    List<UsersLessonsORM> findById_Lesson_IdIn(List<Long> ids);
    List<UsersLessonsORM> findDistinctUserById_Lesson_IdIn(List<Long> ids);
}
