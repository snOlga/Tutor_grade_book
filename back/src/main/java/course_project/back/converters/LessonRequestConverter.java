package course_project.back.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.LessonRequestDTO;
import course_project.back.converters.i.ConverterInterface;
import course_project.back.entity.LessonRequestEntity;
import course_project.back.repository.LessonRequestRepository;

@Service
public class LessonRequestConverter implements ConverterInterface<LessonRequestDTO, LessonRequestEntity> {

    @Autowired
    private LessonConverter lessonConverter;
    @Autowired
    private ParticipatorConverter participatorConverter;
    @Autowired
    private LessonRequestRepository lessonRequestRepository;

    @Override
    public LessonRequestEntity fromDTO(LessonRequestDTO lessonRequestDTO) {
        LessonRequestEntity lessonRequestEntity = new LessonRequestEntity();
        lessonRequestEntity.setId(lessonRequestDTO.getId());
        lessonRequestEntity.setSender(participatorConverter.getFromDB(lessonRequestDTO.getSender()));
        lessonRequestEntity.setReciever(participatorConverter.getFromDB(lessonRequestDTO.getReciever()));
        lessonRequestEntity.setLesson(lessonConverter.getFromDB(lessonRequestDTO.getLesson()));
        lessonRequestEntity.setIsApproved(lessonRequestDTO.getIsApproved());
        lessonRequestEntity.setIsDeleted(lessonRequestDTO.getIsDeleted());
        return lessonRequestEntity;
    }

    @Override
    public LessonRequestDTO fromEntity(LessonRequestEntity lessonRequestEntity) {
        LessonRequestDTO lessonRequestDTO = new LessonRequestDTO();
        lessonRequestDTO.setId(lessonRequestEntity.getId());
        lessonRequestDTO.setSender(participatorConverter.fromEntity(lessonRequestEntity.getSender()));
        lessonRequestDTO.setReciever(participatorConverter.fromEntity(lessonRequestEntity.getReciever()));
        lessonRequestDTO.setLesson(lessonConverter.fromEntity(lessonRequestEntity.getLesson()));
        lessonRequestDTO.setIsApproved(lessonRequestEntity.getIsApproved());
        lessonRequestDTO.setIsDeleted(lessonRequestEntity.getIsDeleted());
        return lessonRequestDTO;
    }

    @Override
    public LessonRequestEntity getFromDB(LessonRequestDTO lessonRequestDTO) {
        return lessonRequestRepository.findById(lessonRequestDTO.getId()).get();
    }
}
