package course_project.back.converters;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.LessonDTO;
import course_project.back.converters.i.ConverterInterface;
import course_project.back.entity.LessonEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.LessonRepository;

@Service
public class LessonConverter implements ConverterInterface<LessonDTO, LessonEntity> {

    @Autowired
    private ParticipatorConverter participatorConverter;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private SubjectConverter subjectConverter;

    @Override
    public LessonEntity fromDTO(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(Utils.fromDTO(lessonDTO.getId()));
        lessonEntity.setHeading(lessonDTO.getHeading());
        lessonEntity.setDurationInMinutes(lessonDTO.getDurationInMinutes());
        lessonEntity.setHomework(lessonDTO.getHomework());
        lessonEntity.setDescription(lessonDTO.getDescription());
        lessonEntity.setStartTime(lessonDTO.getStartTime());
        lessonEntity.setIsOpen(lessonDTO.getIsOpen());
        lessonEntity.setIsDeleted(lessonDTO.getIsDeleted());

        UserEntity owner = participatorConverter.getFromDB(lessonDTO.getOwner());
        HashSet<UserEntity> participators = new HashSet<>();
        participators.add(owner);

        lessonEntity.setOwner(owner);
        lessonEntity.setUsers(participators);
        lessonEntity.setSubject(subjectConverter.getFromDB(lessonDTO.getSubject()));
        lessonEntity.setHumanReadableId("");
        return lessonEntity;
    }

    @Override
    public LessonDTO fromEntity(LessonEntity lessonEntity) {
        if (lessonEntity == null)
            return null;

        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(lessonEntity.getId().toString());
        lessonDTO.setHeading(lessonEntity.getHeading());
        lessonDTO.setDescription(lessonEntity.getDescription());
        lessonDTO.setHomework(lessonEntity.getHomework());
        lessonDTO.setDurationInMinutes(lessonEntity.getDurationInMinutes());
        lessonDTO.setStartTime(lessonEntity.getStartTime());
        lessonDTO.setIsOpen(lessonEntity.getIsOpen());
        lessonDTO.setIsDeleted(lessonEntity.getIsDeleted());
        lessonDTO.setHumanReadableId(lessonEntity.getHumanReadableId());
        lessonDTO.setOwner(participatorConverter.fromEntity(lessonEntity.getOwner()));
        lessonDTO.setUsers(lessonEntity.getUsers().stream().map(participatorConverter::fromEntity).toList());
        lessonDTO.setSubject(subjectConverter.fromEntity(lessonEntity.getSubject()));

        return lessonDTO;
    }

    @Override
    public LessonEntity getFromDB(LessonDTO lessonDTO) {
        return lessonRepository.findById(Utils.fromDTO(lessonDTO.getId())).get();
    }
}
