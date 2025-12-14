package course_project.back.service;

import java.util.ArrayList;
import java.util.List;

import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.WeekDTO;
import course_project.back.converters.LessonConverter;
import course_project.back.entity.LessonEntity;
import course_project.back.repository.LessonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonConverter lessonConverter;

    @Transactional
    public LessonDTO create(LessonDTO lessonDTO) {
        LessonEntity result = setHumanReadableIdAndSave(lessonConverter.fromDTO(lessonDTO));
        LessonDTO resultDTO = lessonConverter.fromEntity(result);
        return resultDTO;
    }

    public LessonDTO update(LessonDTO lessonDTO) {
        return create(lessonDTO);
    }

    public boolean deleteById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).get();
        lessonEntity.setIsDeleted(true);
        lessonRepository.save(lessonEntity);
        return lessonRepository != null;
    }

    public List<LessonDTO> findAllByUserEmail(String email, WeekDTO weekDTO) {
        List<LessonEntity> result = lessonRepository.findAllByUserEmail(email, weekDTO.getStartDate(),
                weekDTO.getEndDate());
        result.sort((lesson1, lesson2) -> {
            return (lesson1.getStartTime().compareTo(lesson2.getStartTime()));
        });
        return result.stream().map(lessonConverter::fromEntity).toList();
    }

    public List<LessonDTO> findAllBySubject(Long id, WeekDTO weekDTO) {
        List<LessonEntity> result = new ArrayList<LessonEntity>(lessonRepository.findAllBySubjectId(id, weekDTO.getStartDate(),
                weekDTO.getEndDate()));
        result.sort((lesson1, lesson2) -> {
            return (lesson1.getStartTime().compareTo(lesson2.getStartTime()));
        });
        return result.stream().map(lessonConverter::fromEntity).toList();
    }

    private LessonEntity setHumanReadableIdAndSave(LessonEntity lessonEntity) {
        LessonEntity result = lessonRepository.save(lessonEntity);
        result.setDefaultHumanRedableID();
        return lessonRepository.save(result);
    }
}
