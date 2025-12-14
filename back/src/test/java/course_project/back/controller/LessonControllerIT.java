package course_project.back.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.ParticipatorDTO;
import course_project.back.DTO.SubjectDTO;
import course_project.back.DTO.WeekDTO;
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
        private LessonRepository lessonRepository;

        @Autowired
        private SubjectRepository subjectRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RolesRepository rolesRepository;

        private SubjectEntity testSubject;
        private UserEntity testOwner;
        private RoleEntity tutorRole;

        @BeforeEach
        void setup() {
                lessonRepository.deleteAll();
                userRepository.deleteAll();
                subjectRepository.deleteAll();
                rolesRepository.deleteAll();

                // Create test role
                RoleEntity newRole = new RoleEntity();
                newRole.setName("TUTOR");
                rolesRepository.saveAndFlush(newRole);
                // Fetch by name to get a fresh managed entity (avoids detached entity issues
                // with cascade)
                tutorRole = rolesRepository.findByName("TUTOR");

                // Create test subject
                testSubject = new SubjectEntity();
                testSubject.setName("Mathematics");
                testSubject = subjectRepository.save(testSubject);

                // Create test owner (tutor)
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
        @WithMockUser(authorities = "USER")
        void userShouldNotCreateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();

                mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isForbidden());
        }

        @Test
        void unauthenticatedShouldNotCreateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();

                mockMvc.perform(post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isUnauthorized());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void adminShouldUpdateLesson() throws Exception {
                // First create a lesson through the service
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
        void shouldReturn404WhenUpdatingNonExistentLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();

                mockMvc.perform(put("/lessons/{id}", 999L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(authorities = "USER")
        void userShouldNotUpdateLesson() throws Exception {
                LessonDTO lessonDTO = createTestLessonDTO();

                mockMvc.perform(put("/lessons/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(lessonDTO)))
                                .andExpect(status().isForbidden());
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
                mockMvc.perform(delete("/lessons/{id}", 999L))
                                .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(authorities = "USER")
        void userShouldNotDeleteLesson() throws Exception {
                mockMvc.perform(delete("/lessons/{id}", 1L))
                                .andExpect(status().isForbidden());
        }

        @Test
        void shouldGetAllUserLessons() throws Exception {
                WeekDTO weekDTO = createTestWeekDTO();

                mockMvc.perform(put("/lessons/user/{email}", "test@example.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(weekDTO)))
                                .andExpect(status().isOk());
        }

        @Test
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

                // Set SubjectDTO
                SubjectDTO subjectDTO = new SubjectDTO();
                subjectDTO.setId(testSubject.getId());
                subjectDTO.setName(testSubject.getName());
                lessonDTO.setSubject(subjectDTO);
                lessonDTO.setIsDeleted(false);

                // Set Owner ParticipatorDTO
                ParticipatorDTO ownerDTO = new ParticipatorDTO();
                ownerDTO.setName(testOwner.getName());
                ownerDTO.setSecondName(testOwner.getSecondName());
                ownerDTO.setEmail(testOwner.getEmail());
                ownerDTO.setHumanReadableID(testOwner.getHumanReadableID());
                ownerDTO.setRoles(testOwner.getRoles().stream()
                                .map(RoleEntity::getName)
                                .toList());
                lessonDTO.setOwner(ownerDTO);

                // Set users list
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
