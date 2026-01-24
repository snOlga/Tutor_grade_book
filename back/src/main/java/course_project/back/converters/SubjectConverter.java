package course_project.back.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.SubjectDTO;
import course_project.back.converters.i.ConverterInterface;
import course_project.back.entity.SubjectEntity;
import course_project.back.repository.SubjectRepository;

@Service
public class SubjectConverter implements ConverterInterface<SubjectDTO, SubjectEntity> {

    @Autowired
    private SubjectRepository subjectRepository;
    private final ModelMapper mapper = new ModelMapper();

    public SubjectConverter() {
        configureMappings();
    }

    private void configureMappings() {
        mapper.createTypeMap(SubjectEntity.class, SubjectDTO.class);
        mapper.createTypeMap(SubjectDTO.class, SubjectEntity.class);
    }

    @Override
    public SubjectEntity fromDTO(SubjectDTO subjectDTO) {
        return mapper.map(subjectDTO, SubjectEntity.class);
    }

    @Override
    public SubjectDTO fromEntity(SubjectEntity subjectEntity) {
        return mapper.map(subjectEntity, SubjectDTO.class);
    }

    @Override
    public SubjectEntity getFromDB(SubjectDTO subjectDTO) {
        return subjectRepository.findByName(subjectDTO.getName());
    }

}
