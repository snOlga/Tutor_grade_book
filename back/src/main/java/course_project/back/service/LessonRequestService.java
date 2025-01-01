package course_project.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.LessonDTO;
import course_project.back.entity.LessonEntity;
import course_project.back.entity.LessonRequestEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.LessonRepository;
import course_project.back.repository.LessonRequestRepository;
import course_project.back.repository.UserRepository;

@Service
public class LessonRequestService {

    @Autowired
    private LessonRequestRepository lessonRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public void inviteParticipators(LessonDTO lessonDTO) {
        if (lessonDTO.getUsers().size() == 0)
            return;
        LessonRequestEntity lessonRequestEntityScheme = makeSchemeRequestFrom(lessonDTO);
        inviteEveryParticipator(lessonRequestEntityScheme, lessonDTO);
    }

    private LessonRequestEntity makeSchemeRequestFrom(LessonDTO lessonDTO) {
        UserEntity owner = userRepository.findByEmail(lessonDTO.getOwner().getEmail());
        LessonEntity lessonEntity = lessonRepository.findById(lessonDTO.getId()).get();

        return new LessonRequestEntity(false, false, owner, lessonEntity);
    }

    private void inviteEveryParticipator(LessonRequestEntity sheme, LessonDTO lessonDTO) {
        List<UserEntity> participators = lessonDTO.getUsers().stream()
                .map(user -> userRepository.findByEmail(user.getEmail())).toList();
        participators.forEach(participator -> {
            LessonRequestEntity lessonRequestEntity = sheme.clone();
            lessonRequestEntity.setReciever(participator);
            lessonRequestRepository.save(lessonRequestEntity);
        });
    }
}
