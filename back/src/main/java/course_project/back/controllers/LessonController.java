package course_project.back.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.models.LessonDTO;
import course_project.back.orms.LessonORM;
import course_project.back.services.LessonService;


@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<LessonORM> lessonsORM = lessonService.findAll();
        List<LessonDTO> lessonsDTO = lessonsORM.stream()
                .map(LessonDTO::new) // предполагается, что конструктор LessonDTO(LessonORM orm) корректно мапит поля
                .collect(Collectors.toList());
        return new ResponseEntity<>(lessonsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        LessonORM lessonORM = lessonService.findById(id);
        if (lessonORM == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new LessonDTO(lessonORM), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) {
        LessonORM lessonORM = new LessonORM(); // Создание ORM объекта из DTO
        // Здесь нужно скопировать данные из lessonDTO в lessonORM
        //  ... (Mapper или ручное копирование) ...
        LessonORM createdLessonORM = lessonService.create(lessonORM);
        return new ResponseEntity<>(new LessonDTO(createdLessonORM), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        LessonORM lessonORM = lessonService.findById(id);
        if (lessonORM == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Здесь нужно обновить поля lessonORM данными из lessonDTO
        // ... (Mapper или ручное копирование) ...
        LessonORM updatedLessonORM = lessonService.update(lessonORM);
        return new ResponseEntity<>(new LessonDTO(updatedLessonORM), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        boolean deleted = lessonService.deleteById(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
