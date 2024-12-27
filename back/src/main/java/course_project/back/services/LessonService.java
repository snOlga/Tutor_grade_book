package course_project.back.services;

import java.util.List;
import java.util.Optional;

import course_project.back.business.LessonDTO;
import course_project.back.entity.LessonEntity;

import org.springframework.stereotype.Service;

import course_project.back.repositories.LessonRepository;

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
        // Возвращаем null, если урок не найден
        return lesson.map(LessonDTO::new).orElse(null);

    }

    public LessonDTO create(LessonDTO lessonDTO) {
        LessonEntity lessonORM = new LessonEntity(lessonDTO);
        LessonEntity save_result = lessonRepository.save(lessonORM);
        return new LessonDTO(save_result);
    }

    public LessonDTO update(LessonDTO lessonDTO) {
        LessonEntity lessonORM = new LessonEntity(lessonDTO);
        LessonEntity save_result = lessonRepository.save(lessonORM);
        return new LessonDTO(save_result);
        // JpaRepository.save()  обрабатывает как создание, так и обновление
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
        List<LessonEntity> res = lessonRepository.findAllByUserEmail(email);
        return res.stream().map(LessonDTO::new).toList();
    }
}

