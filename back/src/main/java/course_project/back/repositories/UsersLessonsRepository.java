package course_project.back.repositories;

import course_project.back.business.User;
import course_project.back.orms.UsersLessonsId;
import course_project.back.orms.UsersLessonsORM;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import course_project.back.orms.LessonORM;

import java.util.List;
import java.util.Set;

@Repository
public interface UsersLessonsRepository extends JpaRepository<UsersLessonsORM, UsersLessonsId> {
    List<UsersLessonsORM> findById_User_Id(Long id);
    List<UsersLessonsORM> findById_Lesson_IdIn(List<Long> ids);
    List<UsersLessonsORM> findDistinctUserById_Lesson_IdIn(List<Long> ids);
}
