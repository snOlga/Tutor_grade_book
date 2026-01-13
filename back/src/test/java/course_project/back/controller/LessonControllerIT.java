package course_project.back.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.ParticipatorDTO;
import course_project.back.DTO.SubjectDTO;
import course_project.back.DTO.WeekDTO;
import course_project.back.controller.utils.Utils;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.SubjectEntity;
import course_project.back.entity.UserEntity;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LessonControllerIT {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;
        @Autowired
        private SubjectRepository subjectRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private RolesRepository rolesRepository;
        @Autowired
        private Utils utils;

        private SubjectEntity testSubject;
        private UserEntity testOwner;
        private RoleEntity tutorRole;

        @BeforeEach
        void setup() {
                utils.cleanDb();
                RoleEntity newRole = new RoleEntity();
                newRole.setName("TUTOR");
                rolesRepository.saveAndFlush(newRole);
                tutorRole = rolesRepository.findByName("TUTOR");
                testSubject = new SubjectEntity();
                testSubject.setName("Mathematics");
                testSubject = subjectRepository.save(testSubject);
                testOwner = new UserEntity();
                testOwner.setName("John");
                testOwner.setSecondName("Doe");
                testOwner.setEmail("tutor@test.com");
                testOwner.setPassword("password");
                testOwner.setHumanReadableID("John_Doe_1");
                testOwner.setIsDeleted(false);
                Set<RoleEntity> roles = new HashSet<>();
                roles.add(tutorRole);
                testOwner.setRoles(roles);
                testOwner = userRepository.save(testOwner);
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void adminShouldCreateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();

                mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.heading").value("Test Lesson"))
                                .andExpect(jsonPath("$.durationInMinutes").value(60));
        }

        @Test
        @WithMockUser(authorities = "TUTOR")
        void tutorShouldCreateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();

                mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isCreated());
        }

        @Test
        void unauthenticatedShouldNotCreateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();

                mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void adminShouldUpdateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();
                String response = mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isCreated())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                LessonDTO createdLesson = objectMapper.readValue(response, LessonDTO.class);
                createdLesson.setHeading("Updated Lesson");

                mockMvc.perform(put("/lessons/{id}", createdLesson.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createdLesson)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.heading").value("Updated Lesson"));
        }

        @Test
        @WithMockUser(authorities = "TUTOR")
        void tutorShouldUpdateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();
                String response = mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isCreated())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                LessonDTO createdLesson = objectMapper.readValue(response, LessonDTO.class);
                createdLesson.setHeading("Updated Lesson");

                mockMvc.perform(put("/lessons/{id}", createdLesson.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createdLesson)))
                                .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void adminShouldDeleteLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();
                String response = mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isCreated())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                LessonDTO createdLesson = objectMapper.readValue(response, LessonDTO.class);

                mockMvc.perform(delete("/lessons/{id}", createdLesson.getId()))
                                .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser(authorities = "TUTOR")
        void tutorShouldDeleteLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();
                String response = mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isCreated())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                LessonDTO createdLesson = objectMapper.readValue(response, LessonDTO.class);

                mockMvc.perform(delete("/lessons/{id}", createdLesson.getId()))
                                .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void shouldReturn404WhenDeletingNonExistentLesson() throws Exception {
                mockMvc.perform(delete("/lessons/{id}", "68753A44-4D6F-1226-9C60-0050E4C00067"))
                                .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(authorities = "TUTOR")
        void shouldGetAllUserLessons() throws Exception {
                WeekDTO weekDTO = createTestWeekDTO();

                mockMvc.perform(put("/lessons/user/{email}", "test@example.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(weekDTO)))
                                .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(authorities = "TUTOR")
        void shouldGetAllSubjectLessons() throws Exception {
                WeekDTO weekDTO = createTestWeekDTO();

                mockMvc.perform(put("/lessons/subject/{subjectId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(weekDTO)))
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
                subjectDTO.setId(testSubject.getId());
                subjectDTO.setName(testSubject.getName());
                lessonDTO.setSubject(subjectDTO);
                lessonDTO.setIsDeleted(false);

                ParticipatorDTO ownerDTO = new ParticipatorDTO();
                ownerDTO.setName(testOwner.getName());
                ownerDTO.setSecondName(testOwner.getSecondName());
                ownerDTO.setEmail(testOwner.getEmail());
                ownerDTO.setHumanReadableID(testOwner.getHumanReadableID());
                ownerDTO.setRoles(testOwner.getRoles().stream()
                                .map(RoleEntity::getName)
                                .toList());
                lessonDTO.setOwner(ownerDTO);

                List<ParticipatorDTO> users = new ArrayList<>();
                users.add(ownerDTO);
                lessonDTO.setUsers(users);

                return lessonDTO;
        }

        private WeekDTO createTestWeekDTO() {
                Calendar cal = Calendar.getInstance();
                Date startDate = new Date(cal.getTimeInMillis());
                cal.add(Calendar.DAY_OF_MONTH, 7);
                Date endDate = new Date(cal.getTimeInMillis());

                WeekDTO weekDTO = new WeekDTO();
                weekDTO.setStartDate(startDate);
                weekDTO.setEndDate(endDate);
                return weekDTO;
        }
}
