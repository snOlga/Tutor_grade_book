package course_project.back.converters;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
    private final ModelMapper mapper = new ModelMapper();

    public LessonRequestConverter() {
        configureMappings();
    }

    private void configureMappings() {
        TypeMap<LessonRequestEntity, LessonRequestDTO> toDtoTypeMap = mapper.createTypeMap(LessonRequestEntity.class,
                LessonRequestDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(LessonRequestDTO::setId);
        });

        TypeMap<LessonRequestDTO, LessonRequestEntity> toEntityTypeMap = mapper.createTypeMap(LessonRequestDTO.class,
                LessonRequestEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(LessonRequestEntity::setId);
        });
    }

    @Override
    public LessonRequestEntity fromDTO(LessonRequestDTO lessonRequestDTO) {
        LessonRequestEntity entity = mapper.map(lessonRequestDTO, LessonRequestEntity.class);
        entity.setId(Utils.fromDTO(lessonRequestDTO.getId()));
        entity.setSender(participatorConverter.getFromDB(lessonRequestDTO.getSender()));
        entity.setReciever(participatorConverter.getFromDB(lessonRequestDTO.getReciever()));
        entity.setLesson(lessonConverter.getFromDB(lessonRequestDTO.getLesson()));
        return entity;
    }

    @Override
    public LessonRequestDTO fromEntity(LessonRequestEntity lessonRequestEntity) {
        if (lessonRequestEntity == null)
            return null;

        LessonRequestDTO lessonRequestDTO = mapper.map(lessonRequestEntity, LessonRequestDTO.class);
        lessonRequestDTO.setId(lessonRequestEntity.getId().toString());
        lessonRequestDTO.setSender(participatorConverter.fromEntity(lessonRequestEntity.getSender()));
        lessonRequestDTO.setReciever(participatorConverter.fromEntity(lessonRequestEntity.getReciever()));
        lessonRequestDTO.setLesson(lessonConverter.fromEntity(lessonRequestEntity.getLesson()));
        return lessonRequestDTO;
    }

    @Override
    public LessonRequestEntity getFromDB(LessonRequestDTO lessonRequestDTO) {
        return lessonRequestRepository.findById(Utils.fromDTO(lessonRequestDTO.getId())).get();
    }
}
