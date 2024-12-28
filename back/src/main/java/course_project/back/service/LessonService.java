package course_project.back.service;

import java.util.List;
import java.util.Optional;

import course_project.back.DTO.LessonDTO;
import course_project.back.entity.LessonEntity;
import course_project.back.repository.LessonRepository;

import org.springframework.stereotype.Service;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<LessonDTO> findAll() {
        System.out.println("Иду в бд за всеми уроками...");
        List<LessonEntity> res = lessonRepository.findAll();
        System.out.println(res.get(0) + " сущность");
        return res.stream().map(LessonDTO::new).toList();
    }

    public LessonDTO findById(Long id) {
        Optional<LessonEntity> lesson = lessonRepository.findById(id);
        return lesson.map(LessonDTO::new).orElse(null);

    }

    public LessonDTO create(LessonDTO lessonDTO) {
        LessonEntity lessonORM = new LessonEntity(lessonDTO);
        LessonEntity save_result = lessonRepository.save(lessonORM);
        return new LessonDTO(save_result);
    }

    public LessonDTO update(LessonDTO lessonDTO) {
        return create(lessonDTO);
    }

    public boolean deleteById(Long id) {
        try {
            lessonRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Не удалось обновить");
            return false;
        }
    }

    public List<LessonDTO> findAllByUserEmail(String email) {
        List<LessonEntity> res = lessonRepository.findByUsers_Email(email);
        return res.stream().map(LessonDTO::new).toList();
    }
}

