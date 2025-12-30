package course_project.back.controller.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.repository.ChatRepository;
import course_project.back.repository.LessonRepository;
import course_project.back.repository.LessonRequestRepository;
import course_project.back.repository.MessageRepository;
import course_project.back.repository.RolesRepository;
import course_project.back.repository.SubjectRepository;
import course_project.back.repository.UserRepository;

@Service
public class Utils {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private LessonRequestRepository lessonRequestRepository;
    @Autowired
    private MessageRepository messageRepository;

    public void cleanDb() {
        messageRepository.deleteAll();
        chatRepository.deleteAll();
        lessonRequestRepository.deleteAll();
        lessonRepository.deleteAll();
        subjectRepository.deleteAll();
        userRepository.deleteAll();
        rolesRepository.deleteAll();
    }
}
