package course_project.back.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.back.DTO.MessageDTO;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import course_project.back.controller.utils.Utils;
import course_project.back.converters.ChatConverter;
import course_project.back.converters.ParticipatorConverter;
import course_project.back.entity.ChatEntity;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.ChatRepository;
import course_project.back.repository.RolesRepository;
import course_project.back.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MessageControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipatorConverter participatorConverter;
    @Autowired
    private ChatConverter chatConverter;
    @Autowired
    private Utils utils;

    private Long testChatId;

    @BeforeEach
    void setup() {
        utils.cleanDb();
    }

    @Test
    @WithMockUser
    void shouldCreateMessage() throws Exception {
        UserEntity student = new UserEntity();
        student.setName("John");
        student.setSecondName("Doe");
        student.setEmail("aaa@test.com");
        student.setPassword("password");
        student.setHumanReadableID("abcabc");
        student.setIsDeleted(false);
        RoleEntity newRole2 = new RoleEntity();
        newRole2.setName("STUDENT");
        rolesRepository.saveAndFlush(newRole2);
        Set<RoleEntity> rolesStudent = new HashSet<>();
        rolesStudent.add(rolesRepository.findByName("STUDENT"));
        student.setRoles(rolesStudent);
        student = userRepository.save(student);

        ChatEntity chat = new ChatEntity();
        chat.setUsers(Collections.singleton(student));
        chat = chatRepository.save(chat);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setText("Hello World");
        messageDTO.setChat(chatConverter.fromEntity(chat));
        messageDTO.setAuthor(participatorConverter.fromEntity(student));
        messageDTO.setSentTime(new Timestamp(System.currentTimeMillis()));
        messageDTO.setIsEdited(false);
        messageDTO.setIsDeleted(false);

        mockMvc.perform(post("/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(messageDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldGetMessagesByChat() throws Exception {
        mockMvc.perform(get("/messages/chat/{chatId}", testChatId))
                .andExpect(status().isOk());
    }
}