package course_project.back.controllers;

import java.util.List;

import course_project.back.business.CalendarDTO;
import course_project.back.services.CalendarService;
import course_project.back.services.LessonServiceImpl;
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

import course_project.back.business.LessonDTO;


@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonServiceImpl lessonService;
    private final CalendarService calendarService;

    @Autowired
    public LessonController(LessonServiceImpl lessonService, CalendarService calendarService) {
        this.lessonService = lessonService;
        this.calendarService = calendarService;
    }

    @GetMapping()
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        System.out.println("В бд стучатся за всеми уроками");
        return new ResponseEntity<>(lessonService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        LessonDTO lessonDTO = lessonService.findById(id);
        return lessonDTO != null ? new ResponseEntity<>(lessonDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO createdLessonDTO = lessonService.create(lessonDTO);
        return new ResponseEntity<>(createdLessonDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        LessonDTO updatedLessonDTO = lessonService.update(lessonDTO);
        return updatedLessonDTO != null ? new ResponseEntity<>(updatedLessonDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        boolean deleted = lessonService.deleteById(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/with_user/{hr_id}")
    public ResponseEntity<List<LessonDTO>> getAllUserLessons(@PathVariable String hr_id) {
        System.out.println("В бд стучатся за всеми уроками");
        return new ResponseEntity<>(lessonService.findAllByUserId(hr_id), HttpStatus.OK);
    }

    @GetMapping("/calendar/{hr_id}")
    public ResponseEntity<List<CalendarDTO>> getCalendar(@PathVariable String hr_id) {

        return new ResponseEntity<>(calendarService.getCalendarByHRId(hr_id), HttpStatus.OK);
    }
}

