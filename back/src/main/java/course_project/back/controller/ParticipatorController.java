package course_project.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.DTO.ParticipatorDTO;
import course_project.back.service.ParticipatorService;
import course_project.back.service.SanitizerService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/participators")
public class ParticipatorController {

    @Autowired
    private ParticipatorService participatorService;
    @Autowired
    private SanitizerService sanitizerService;

    @GetMapping("/student/{id}")
    public ResponseEntity<ParticipatorDTO> getStudentByID(@PathVariable String id) {
        ParticipatorDTO participatorDTO = participatorService.getStudentByHumanReadableID(sanitizerService.sanitize(id));
        return participatorDTO != null ? new ResponseEntity<>(participatorDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tutor/{id}")
    public ResponseEntity<ParticipatorDTO> getTutorByID(@PathVariable String id) {
        ParticipatorDTO participatorDTO = participatorService.getTutorByHumanReadableID(sanitizerService.sanitize(id));
        return participatorDTO != null ? new ResponseEntity<>(participatorDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ParticipatorDTO> getParticipatorByEmail(@PathVariable String email) {
        ParticipatorDTO participatorDTO = participatorService.getParticipatorByEmail(email);
        return participatorDTO != null ? new ResponseEntity<>(participatorDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/human-readable-id/{humanReadableId}")
    public ResponseEntity<ParticipatorDTO> getParticipatorByHumanReadableId(@PathVariable String humanReadableId) {
        ParticipatorDTO participatorDTO = participatorService.getParticipatorByHumanReadableId(sanitizerService.sanitize(humanReadableId));
        return participatorDTO != null ? new ResponseEntity<>(participatorDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{humanReadableId}")
    public ResponseEntity<ParticipatorDTO> updateParticipator(@PathVariable String humanReadableId,
            @RequestBody ParticipatorDTO participatorDTO) {
        ParticipatorDTO result = participatorService.update(sanitizerService.sanitize(humanReadableId), sanitizerService.sanitize(participatorDTO));
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{humanReadableId}")
    public ResponseEntity<ParticipatorDTO> deleteUser(@PathVariable String humanReadableId) {
        ParticipatorDTO result = participatorService.deleteById(sanitizerService.sanitize(humanReadableId));
        return result != null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
