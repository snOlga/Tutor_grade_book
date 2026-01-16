package course_project.back.controller;

import java.util.List;
import java.util.UUID;

import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.WeekDTO;
import course_project.back.service.LessonService;
import course_project.back.service.SanitizerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private SanitizerService sanitizerService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO createdLessonDTO = lessonService.create(sanitizerService.sanitize(lessonDTO));
        return new ResponseEntity<>(createdLessonDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable String id, @RequestBody LessonDTO lessonDTO) {
        LessonDTO updatedLessonDTO = lessonService.update(sanitizerService.sanitize(lessonDTO));
        return updatedLessonDTO != null ? new ResponseEntity<>(updatedLessonDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable String id) {
        boolean deleted = lessonService.deleteById(UUID.fromString(sanitizerService.sanitize(id)));
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR', 'STUDENT')")
    @PutMapping("/user/{email}")
    public ResponseEntity<List<LessonDTO>> getAllUserLessons(@PathVariable String email, @RequestBody WeekDTO weekDTO) {
        List<LessonDTO> allLessons = lessonService.findAllByUserEmail(email, weekDTO);
        return new ResponseEntity<>(allLessons, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR', 'STUDENT')")
    @PutMapping("/subject/{subjectId}")
    public ResponseEntity<List<LessonDTO>> getAllSubjectLessons(@PathVariable Long subjectId, @RequestBody WeekDTO weekDTO) {
        List<LessonDTO> allLessons = lessonService.findAllBySubject(subjectId, weekDTO);
        return new ResponseEntity<>(allLessons, HttpStatus.OK);
    }
}
