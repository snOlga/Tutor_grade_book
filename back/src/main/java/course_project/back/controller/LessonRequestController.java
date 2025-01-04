package course_project.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.DTO.LessonRequestDTO;
import course_project.back.service.LessonRequestService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/lesson_requests")
public class LessonRequestController {

    @Autowired
    private LessonRequestService lessonRequestService;

    @GetMapping("/income/with_user/{email}")
    public ResponseEntity<List<LessonRequestDTO>> getAllUserIncomeLessonsRequests(@PathVariable String email) {
        List<LessonRequestDTO> allRequests = lessonRequestService.findAllIncomeByUserEmail(email);
        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }

    @GetMapping("/outcome/with_user/{email}")
    public ResponseEntity<List<LessonRequestDTO>> getAllUserOutcomeLessonsRequests(@PathVariable String email) {
        List<LessonRequestDTO> allRequests = lessonRequestService.findAllOutcomeByUserEmail(email);
        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }

    @PutMapping("/update_approvement/{id}")
    public ResponseEntity<LessonRequestDTO> approveRequest(@PathVariable Long id,
            @RequestBody LessonRequestDTO lessonRequestDTO) {
        LessonRequestDTO updatedLessonRequestDTO = lessonRequestService.updateApprovement(id, lessonRequestDTO);
        return updatedLessonRequestDTO != null ? new ResponseEntity<>(updatedLessonRequestDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
