package course_project.back.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.LessonRequestDTO;
import course_project.back.DTO.ParticipatorDTO;
import course_project.back.DTO.SubjectDTO;
import course_project.back.controller.utils.Utils;
import course_project.back.converters.ParticipatorConverter;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.SubjectEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.LessonRepository;
import course_project.back.repository.RolesRepository;
import course_project.back.repository.SubjectRepository;
import course_project.back.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LessonRequestControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ParticipatorConverter participatorConverter;
    @Autowired
    private Utils utils;

    private UserEntity student;
    private UserEntity tutor;
    private SubjectEntity subject;

    @BeforeEach
    void setup() {
        utils.cleanDb();

        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setName("Math");
        subject = subjectRepository.save(subjectEntity);

        RoleEntity newRole = new RoleEntity();
        newRole.setName("TUTOR");
        rolesRepository.saveAndFlush(newRole);
        RoleEntity newRole2 = new RoleEntity();
        newRole2.setName("STUDENT");
        rolesRepository.saveAndFlush(newRole2);

        student = new UserEntity();
        student.setName("John");
        student.setSecondName("Doe");
        student.setEmail("aaa@test.com");
        student.setPassword("password");
        student.setHumanReadableID("abcabc");
        student.setIsDeleted(false);
        Set<RoleEntity> rolesStudent = new HashSet<>();
        rolesStudent.add(rolesRepository.findByName("STUDENT"));
        student.setRoles(rolesStudent);
        student = userRepository.save(student);

        tutor = new UserEntity();
        tutor.setName("John");
        tutor.setSecondName("Doe");
        tutor.setEmail("bbb@test.com");
        tutor.setPassword("password");
        tutor.setHumanReadableID("John_Doe_1");
        tutor.setIsDeleted(false);
        Set<RoleEntity> rolesTutor = new HashSet<>();
        rolesTutor.add(rolesRepository.findByName("TUTOR"));
        tutor.setRoles(rolesTutor);
        tutor = userRepository.save(tutor);
    }

    @Test
    @WithMockUser
    void shouldCreateLessonRequest() throws Exception {
        LessonDTO lessonDTO = createTestLessonDTO();
        course_project.back.entity.LessonEntity lessonEntity = new course_project.back.entity.LessonEntity();
        lessonEntity.setStartTime(lessonDTO.getStartTime());
        lessonEntity.setDurationInMinutes(lessonDTO.getDurationInMinutes());
        lessonEntity.setIsOpen(lessonDTO.getIsOpen());
        lessonEntity.setIsDeleted(lessonDTO.getIsDeleted());
        lessonEntity.setHumanReadableId(lessonDTO.getHumanReadableId());
        lessonEntity.setHeading(lessonDTO.getHeading());
        lessonEntity.setDescription(lessonDTO.getDescription());
        lessonEntity.setSubject(subject);
        lessonEntity.setOwner(userRepository.findById(tutor.getId()).get());
        lessonEntity.setUsers(new java.util.HashSet<>());
        lessonEntity = lessonRepository.save(lessonEntity);
        lessonDTO.setId(lessonEntity.getId().toString());
        lessonDTO.setOwner(participatorConverter.fromEntity(tutor));
        LessonRequestDTO requestDTO = new LessonRequestDTO();
        requestDTO.setSender(participatorConverter.fromEntity(tutor));
        requestDTO.setReciever(participatorConverter.fromEntity(student));
        requestDTO.setIsDeleted(false);
        requestDTO.setLesson(lessonDTO);

        mockMvc.perform(post("/lesson-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldGetIncomingRequests() throws Exception {
        mockMvc.perform(get("/lesson-requests/income/{email}", tutor.getEmail()))
                .andExpect(status().isOk());
    }

     private LessonDTO createTestLessonDTO() {
                LessonDTO lessonDTO = new LessonDTO();
                lessonDTO.setStartTime(new Timestamp(System.currentTimeMillis()));
                lessonDTO.setDurationInMinutes(60);
                lessonDTO.setIsOpen(true);
                lessonDTO.setHumanReadableId("TEST-001");
                lessonDTO.setHeading("Test Lesson");
                lessonDTO.setDescription("Test Description");
                lessonDTO.setIsDeleted(false);

                SubjectDTO subjectDTO = new SubjectDTO();
                subjectDTO.setId(subject.getId());
                subjectDTO.setName(subject.getName());
                lessonDTO.setSubject(subjectDTO);
                lessonDTO.setIsDeleted(false);

                ParticipatorDTO ownerDTO = participatorConverter.fromEntity(tutor);

                List<ParticipatorDTO> users = new ArrayList<>();
                users.add(ownerDTO);
                lessonDTO.setUsers(users);

                return lessonDTO;
        }
}