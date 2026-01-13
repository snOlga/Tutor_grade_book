package course_project.back.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.LessonRequestDTO;
import course_project.back.converters.LessonRequestConverter;
import course_project.back.converters.Utils;
import course_project.back.entity.LessonRequestEntity;
import course_project.back.repository.LessonRequestRepository;
import course_project.back.repository.LessonRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import course_project.back.DTO.LessonUserUpdateDTO;

@Service
public class LessonRequestService {

    @Autowired
    private LessonRequestRepository lessonRequestRepository;
    @Autowired
    private LessonRequestConverter lessonRequestConverter;
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${lesson.user.update.topic}")
    private String lessonUserUpdateTopic;

    public LessonRequestDTO create(LessonRequestDTO lessonRequestDTO) {
        if (lessonRequestDTO.getLesson() != null && lessonRequestDTO.getLesson().getId() != null) {
            var maybeLesson = lessonRepository.findById(Utils.fromDTO(lessonRequestDTO.getLesson().getId()));
            if (maybeLesson.isPresent() && Boolean.TRUE.equals(maybeLesson.get().getIsDeleted())) {
                return null;
            }
        }
        LessonRequestEntity result = lessonRequestRepository.save(lessonRequestConverter.fromDTO(lessonRequestDTO));
        return lessonRequestConverter.fromEntity(result);
    }

    public List<LessonRequestDTO> findAllIncomeByUserEmail(String email) {
        List<LessonRequestEntity> lessonRequestEntities = lessonRequestRepository.findByRecieverEmail(email);
        return lessonRequestEntities.stream().map(lessonRequestConverter::fromEntity).toList();
    }

    public List<LessonRequestDTO> findAllOutcomeByUserEmail(String email) {
        List<LessonRequestEntity> lessonRequestEntities = lessonRequestRepository.findAllBySenderEmail(email);
        return lessonRequestEntities.stream().map(lessonRequestConverter::fromEntity).toList();
    }

    public LessonRequestDTO updateApprovement(UUID id, LessonRequestDTO lessonRequestDTO) {
        LessonRequestEntity lessonRequestEntity = lessonRequestRepository.findById(id).get();
        lessonRequestEntity.setIsApproved(lessonRequestDTO.getIsApproved());
        lessonRequestRepository.save(lessonRequestEntity);
        if (lessonRequestDTO.getIsApproved()) {
            addUserToLesson(lessonRequestEntity);
            lessonRequestEntity.setIsDeleted(true);
            lessonRequestRepository.save(lessonRequestEntity);
        }
        return lessonRequestConverter.fromEntity(lessonRequestEntity);
    }

    public Boolean deleteById(UUID id) {
        LessonRequestEntity lessonRequestEntity = lessonRequestRepository.findById(id).get();
        lessonRequestEntity.setIsDeleted(true);
        lessonRequestRepository.save(lessonRequestEntity);
        return lessonRequestEntity != null;
    }

    private void addUserToLesson(LessonRequestEntity lessonRequestEntity) {
        LessonUserUpdateDTO message = new LessonUserUpdateDTO();
        message.setLessonId(lessonRequestEntity.getLesson().getId());
        if (lessonRequestEntity.getSender() != null)
            message.setSenderId(lessonRequestEntity.getSender().getId());
        if (lessonRequestEntity.getReciever() != null)
            message.setRecieverId(lessonRequestEntity.getReciever().getId());
        kafkaTemplate.send(lessonUserUpdateTopic, message);
    }
}
