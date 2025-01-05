package course_project.back.controller;

import java.util.List;

import course_project.back.DTO.LessonDTO;
import course_project.back.service.LessonService;

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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/create")
    public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO createdLessonDTO = lessonService.create(lessonDTO);
        return new ResponseEntity<>(createdLessonDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        LessonDTO updatedLessonDTO = lessonService.update(lessonDTO);
        return updatedLessonDTO != null ? new ResponseEntity<>(updatedLessonDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        boolean deleted = lessonService.deleteById(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/with_user/{email}")
    public ResponseEntity<List<LessonDTO>> getAllUserLessons(@PathVariable String email) {
        List<LessonDTO> allLessons = lessonService.findAllByUserEmail(email);
        return new ResponseEntity<>(allLessons, HttpStatus.OK);
    }

    @GetMapping("/with_subject/{id}")
    public ResponseEntity<List<LessonDTO>> getAllSubjectLessons(@RequestParam Long subjectId) {
        List<LessonDTO> allLessons = lessonService.findAllBySubject(subjectId);
        return new ResponseEntity<>(allLessons, HttpStatus.OK);
    }

}
