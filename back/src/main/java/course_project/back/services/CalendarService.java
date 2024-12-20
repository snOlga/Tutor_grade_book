package course_project.back.services;

import course_project.back.business.CalendarDTO;
import course_project.back.business.ParticipatorDTO;
import course_project.back.business.User;
import course_project.back.enums.UserRoles;
import course_project.back.orms.LessonORM;
import course_project.back.orms.UsersLessonsORM;
import course_project.back.repositories.LessonRepository;
import course_project.back.repositories.UserRepository;
import course_project.back.repositories.UsersLessonsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CalendarService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final UsersLessonsRepository usersLessonsRepository;

    public CalendarService(LessonRepository lessonRepository,
                           UserRepository userRepository,
                           UsersLessonsRepository usersLessonsRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.usersLessonsRepository = usersLessonsRepository;
    }

    public List<CalendarDTO> getCalendarByHRId(String user_hr_id) {
        User user = userRepository.findByHumanReadableID(user_hr_id);
        System.out.println(user.getId() + " " + user.getName());
        List<UsersLessonsORM> lessons_of_user = usersLessonsRepository.findById_User_Id(user.getId());
        System.out.println(lessons_of_user);
        List<Long> lessonIds = lessons_of_user.stream()
                .map(usersLessons -> usersLessons.getId().getLesson().getId())
                .toList();

        List<LessonORM> lessonORMS = lessonRepository.findByIdIn(lessonIds);
        List<UsersLessonsORM> users_in_lessons = usersLessonsRepository.findDistinctUserById_Lesson_IdIn(lessonIds);
        System.out.println(users_in_lessons);

        List<ParticipatorDTO> students = new ArrayList<>();
        List<ParticipatorDTO> tutors = new ArrayList<>();

        List<CalendarDTO> result = new ArrayList<>();

        for (UsersLessonsORM usersLessonsORM: users_in_lessons) {
            User curr_user = usersLessonsORM.getId().getUser();
            Set<UserRoles> roles = curr_user.getRoles();
            if (roles.contains(UserRoles.TUTOR)) {
                tutors.add(new ParticipatorDTO(curr_user.getId(), curr_user.getName()));
            }
            if (roles.contains(UserRoles.STUDENT)) {
                students.add(new ParticipatorDTO(curr_user.getId(), curr_user.getName()));
            }
        }
        for (LessonORM lessonORM: lessonORMS) {
            result.add(
                    new CalendarDTO(
                            lessonORM.getId(),
                            students,
                            tutors,
                            lessonORM.getStartTime(),
                            lessonORM.getDuration(),
                            lessonORM.getHeading(),
                            lessonORM.getDescription()
                    )
            );
        }

        return result;
    }
}
