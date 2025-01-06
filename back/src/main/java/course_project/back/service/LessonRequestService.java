package course_project.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.LessonRequestDTO;
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

    public LessonRequestDTO create(LessonRequestDTO lessonRequestDTO) {
        LessonRequestEntity lessonRequestEntity = new LessonRequestEntity(lessonRequestDTO);
        lessonRequestEntity.setSender(userRepository.findByEmail(lessonRequestDTO.getSender().getEmail()));
        lessonRequestEntity.setReciever(userRepository.findByEmail(lessonRequestDTO.getReciever().getEmail()));
        lessonRequestEntity.setLesson(lessonRepository.findById(lessonRequestDTO.getLesson().getId()).get());
        LessonRequestEntity result = lessonRequestRepository.save(lessonRequestEntity);
        return new LessonRequestDTO(result);
    }

    public List<LessonRequestDTO> findAllIncomeByUserEmail(String email) {
        List<LessonRequestEntity> lessonRequestEntities = lessonRequestRepository.findAllByReciever_Email(email);
        return lessonRequestEntities.stream().map(LessonRequestDTO::new).toList();
    }

    public List<LessonRequestDTO> findAllOutcomeByUserEmail(String email) {
        List<LessonRequestEntity> lessonRequestEntities = lessonRequestRepository.findAllBySender_Email(email);
        return lessonRequestEntities.stream().map(LessonRequestDTO::new).toList();
    }

    public LessonRequestDTO updateApprovement(Long id, LessonRequestDTO lessonRequestDTO) {
        LessonRequestEntity lessonRequestEntity = lessonRequestRepository.findById(id).get();
        lessonRequestEntity.setIsApproved(lessonRequestDTO.getIsApproved());
        lessonRequestRepository.save(lessonRequestEntity);
        if (lessonRequestDTO.getIsApproved())
            addUserToLesson(lessonRequestEntity);
        return new LessonRequestDTO(lessonRequestEntity);
    }

    public Boolean deleteById(Long id) {
        LessonRequestEntity lessonRequestEntity = lessonRequestRepository.findById(id).get();
        lessonRequestEntity.setIsDeleted(true);
        lessonRequestRepository.save(lessonRequestEntity);
        return lessonRequestEntity != null;
    }

    public void inviteParticipators(LessonDTO lessonDTO) {
        if (lessonDTO.getUsers().size() == 0)
            return;
        LessonRequestEntity lessonRequestEntityScheme = makeSchemeRequestFrom(lessonDTO);
        inviteEveryParticipator(lessonRequestEntityScheme, lessonDTO);
    }

    private LessonRequestEntity makeSchemeRequestFrom(LessonDTO lessonDTO) {
        UserEntity owner = userRepository.findByEmail(lessonDTO.getOwner().getEmail());
        LessonEntity lessonEntity = lessonRepository.findById(lessonDTO.getId()).get();

        return new LessonRequestEntity(owner, lessonEntity);
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

    private void addUserToLesson(LessonRequestEntity lessonRequestEntity) {
        LessonEntity lessonEntity = lessonRequestEntity.getLesson();
        lessonEntity.getUsers().add(lessonRequestEntity.getReciever());
        lessonEntity.getUsers().add(lessonRequestEntity.getSender());
        lessonRepository.save(lessonEntity);
    }
}
