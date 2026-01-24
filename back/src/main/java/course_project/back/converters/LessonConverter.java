package course_project.back.converters;

import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
    private final ModelMapper mapper = new ModelMapper();

    public LessonConverter() {
        configureMappings();
    }

    private void configureMappings() {
        TypeMap<LessonEntity, LessonDTO> toDtoTypeMap = mapper.createTypeMap(LessonEntity.class,
                LessonDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(LessonDTO::setId);
            m.skip(LessonDTO::setUsers);
        });

        TypeMap<LessonDTO, LessonEntity> toEntityTypeMap = mapper.createTypeMap(LessonDTO.class,
                LessonEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(LessonEntity::setId);
            m.skip(LessonEntity::setUsers);
        });
    }

    @Override
    public LessonEntity fromDTO(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = mapper.map(lessonDTO, LessonEntity.class);
        lessonEntity.setId(Utils.fromDTO(lessonDTO.getId()));

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

        LessonDTO lessonDTO = mapper.map(lessonEntity, LessonDTO.class);
        lessonDTO.setId(lessonEntity.getId().toString());
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
