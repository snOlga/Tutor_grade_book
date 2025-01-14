package course_project.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.LessonRequestDTO;
import course_project.back.converters.LessonRequestConverter;
import course_project.back.entity.LessonEntity;
import course_project.back.entity.LessonRequestEntity;
import course_project.back.repository.LessonRepository;
import course_project.back.repository.LessonRequestRepository;

@Service
public class LessonRequestService {

    @Autowired
    private LessonRequestRepository lessonRequestRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonRequestConverter lessonRequestConverter;

    public LessonRequestDTO create(LessonRequestDTO lessonRequestDTO) {
        LessonRequestEntity result = lessonRequestRepository.save(lessonRequestConverter.fromDTO(lessonRequestDTO));
        return lessonRequestConverter.fromEntity(result);
    }

    public List<LessonRequestDTO> findAllIncomeByUserEmail(String email) {
        List<LessonRequestEntity> lessonRequestEntities = lessonRequestRepository.findAllByReciever_Email(email);
        return lessonRequestEntities.stream().map(lessonRequestConverter::fromEntity).toList();
    }

    public List<LessonRequestDTO> findAllOutcomeByUserEmail(String email) {
        List<LessonRequestEntity> lessonRequestEntities = lessonRequestRepository.findAllBySender_Email(email);
        return lessonRequestEntities.stream().map(lessonRequestConverter::fromEntity).toList();
    }

    public LessonRequestDTO updateApprovement(Long id, LessonRequestDTO lessonRequestDTO) {
        LessonRequestEntity lessonRequestEntity = lessonRequestRepository.findById(id).get();
        lessonRequestEntity.setIsApproved(lessonRequestDTO.getIsApproved());
        lessonRequestRepository.save(lessonRequestEntity);
        if (lessonRequestDTO.getIsApproved())
            addUserToLesson(lessonRequestEntity);
        return lessonRequestConverter.fromEntity(lessonRequestEntity);
    }

    public Boolean deleteById(Long id) {
        LessonRequestEntity lessonRequestEntity = lessonRequestRepository.findById(id).get();
        lessonRequestEntity.setIsDeleted(true);
        lessonRequestRepository.save(lessonRequestEntity);
        return lessonRequestEntity != null;
    }

    private void addUserToLesson(LessonRequestEntity lessonRequestEntity) {
        LessonEntity lessonEntity = lessonRequestEntity.getLesson();
        lessonEntity.getUsers().add(lessonRequestEntity.getReciever());
        lessonEntity.getUsers().add(lessonRequestEntity.getSender());
        lessonRepository.save(lessonEntity);
    }
}
