package course_project.back.converters;

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

    @Override
    public SubjectEntity fromDTO(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(subjectDTO.getId());
        subjectEntity.setName(subjectDTO.getName());
        subjectEntity.setAnalogyNames(subjectDTO.getAnalogyNames());
        return subjectEntity;
    }

    @Override
    public SubjectDTO fromEntity(SubjectEntity subjectEntity) {
        if (subjectEntity == null)
            return null;

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subjectEntity.getId());
        subjectDTO.setName(subjectEntity.getName());
        subjectDTO.setAnalogyNames(subjectEntity.getAnalogyNames());
        return subjectDTO;
    }

    @Override
    public SubjectEntity getFromDB(SubjectDTO subjectDTO) {
        return subjectRepository.findByName(subjectDTO.getName());
    }

}
