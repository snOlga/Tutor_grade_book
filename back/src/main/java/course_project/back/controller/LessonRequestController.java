package course_project.back.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.DTO.LessonRequestDTO;
import course_project.back.service.LessonRequestService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/lesson-requests")
public class LessonRequestController {

    @Autowired
    private LessonRequestService lessonRequestService;

    @GetMapping("/income/{email}")
    public ResponseEntity<List<LessonRequestDTO>> getAllUserIncomeLessonsRequests(@PathVariable String email) {
        List<LessonRequestDTO> allRequests = lessonRequestService.findAllIncomeByUserEmail(email);
        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }

    @GetMapping("/outcome/{email}")
    public ResponseEntity<List<LessonRequestDTO>> getAllUserOutcomeLessonsRequests(@PathVariable String email) {
        List<LessonRequestDTO> allRequests = lessonRequestService.findAllOutcomeByUserEmail(email);
        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonRequestDTO> approveRequest(@PathVariable String id,
            @RequestBody LessonRequestDTO lessonRequestDTO) {
        LessonRequestDTO updatedLessonRequestDTO = lessonRequestService.updateApprovement(UUID.fromString(id), lessonRequestDTO);
        return updatedLessonRequestDTO != null ? new ResponseEntity<>(updatedLessonRequestDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LessonRequestDTO> deleteRequest(@PathVariable String id) {
        boolean deleted = lessonRequestService.deleteById(UUID.fromString(id));
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<LessonRequestDTO> createLessonRequest(@RequestBody LessonRequestDTO lessonRequestDTO) {
        LessonRequestDTO createdLessonDTO = lessonRequestService.create(lessonRequestDTO);
        return new ResponseEntity<>(createdLessonDTO, HttpStatus.CREATED);
    }

}
