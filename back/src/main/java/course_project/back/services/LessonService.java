package course_project.back.services;

import java.util.List;

import course_project.back.business.LessonDTO;

public interface LessonService {
    List<LessonDTO> findAll();
    LessonDTO findById(Long id);
    LessonDTO create(LessonDTO lessonDTO);
    LessonDTO update(LessonDTO lessonDTO);
    boolean deleteById(Long id);
}
