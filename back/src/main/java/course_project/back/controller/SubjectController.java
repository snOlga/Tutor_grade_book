package course_project.back.controller;

import java.util.List;

import course_project.back.entity.SubjectEntity;
import course_project.back.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping
    public ResponseEntity<List<SubjectEntity>> createLesson() {
        List<SubjectEntity> subjectDTO = subjectRepository.findAll();
        return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
    }
}

