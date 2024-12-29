package course_project.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.DTO.ParticipatorDTO;
import course_project.back.service.ParticipatorService;

@RestController
@RequestMapping("/participator")
public class ParticipatorController {
    
    @Autowired
    private ParticipatorService participatorService;

    @GetMapping("/students/{id}")
    public ResponseEntity<ParticipatorDTO> getStudentByHumanReadableID(@PathVariable String id) {
        ParticipatorDTO participatorDTO = participatorService.getStudentByHumanReadableID(id);
        return participatorDTO != null ? new ResponseEntity<>(participatorDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tutors/{id}")
    public ResponseEntity<ParticipatorDTO> getTutorByHumanReadableID(@PathVariable String id) {
        ParticipatorDTO participatorDTO = participatorService.getTutorByHumanReadableID(id);
        return participatorDTO != null ? new ResponseEntity<>(participatorDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
