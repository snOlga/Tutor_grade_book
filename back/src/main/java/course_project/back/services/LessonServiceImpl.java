package course_project.back.services;

import java.util.List;
import java.util.Optional;

import course_project.back.business.LessonDTO;
import course_project.back.entity.LessonORM;

import org.springframework.stereotype.Service;

import course_project.back.repositories.LessonRepository;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<LessonDTO> findAll() {
        System.out.println("Иду в бд за всеми уроками...");
        List<LessonORM> res = lessonRepository.findAll();
        System.out.println(res.get(0) + " сущность");
        return res.stream().map(LessonDTO::new).toList();
    }

    @Override
    public LessonDTO findById(Long id) {
        Optional<LessonORM> lesson = lessonRepository.findById(id);
        // Возвращаем null, если урок не найден
        return lesson.map(LessonDTO::new).orElse(null);

    }

    @Override
    public LessonDTO create(LessonDTO lessonDTO) {
        LessonORM lessonORM = new LessonORM(lessonDTO);
        LessonORM save_result = lessonRepository.save(lessonORM);
        return new LessonDTO(save_result);
    }

    @Override
    public LessonDTO update(LessonDTO lessonDTO) {
        LessonORM lessonORM = new LessonORM(lessonDTO);
        LessonORM save_result = lessonRepository.save(lessonORM);
        return new LessonDTO(save_result);
        // JpaRepository.save()  обрабатывает как создание, так и обновление
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            lessonRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Не удалось обновить");
            return false;
        }
    }

    public List<LessonDTO> findAllByUserId(String humanReadableId) {
        List<LessonORM> res = lessonRepository.findByHumanReadableId(humanReadableId);
        return res.stream().map(LessonDTO::new).toList();
    }
}

