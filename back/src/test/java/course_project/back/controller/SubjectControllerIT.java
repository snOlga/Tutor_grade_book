package course_project.back.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import course_project.back.controller.utils.Utils;
import course_project.back.entity.SubjectEntity;
import course_project.back.repository.SubjectRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SubjectControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private Utils utils;

    @BeforeEach
    void setup() {
        utils.cleanDb();
        subjectRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void shouldReturnAllSubjects() throws Exception {
        SubjectEntity subject1 = new SubjectEntity();
        subject1.setName("Physics");
        subjectRepository.save(subject1);

        SubjectEntity subject2 = new SubjectEntity();
        subject2.setName("Chemistry");
        subjectRepository.save(subject2);

        mockMvc.perform(get("/subjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    @WithMockUser
    void shouldReturnEmptyListWhenNoSubjects() throws Exception {
        mockMvc.perform(get("/subjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser
    void shouldContainSavedSubjectNames() throws Exception {
        SubjectEntity subject1 = new SubjectEntity();
        subject1.setName("Algebra");
        subjectRepository.save(subject1);

        SubjectEntity subject2 = new SubjectEntity();
        subject2.setName("Geometry");
        subjectRepository.save(subject2);

        mockMvc.perform(get("/subjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Algebra")))
                .andExpect(content().string(containsString("Geometry")));
    }
}