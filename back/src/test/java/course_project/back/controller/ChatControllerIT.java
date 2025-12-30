package course_project.back.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import course_project.back.DTO.ChatDTO;
import course_project.back.DTO.ParticipatorDTO;
import course_project.back.converters.ParticipatorConverter;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.RolesRepository;
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
import course_project.back.controller.utils.Utils;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ChatControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ParticipatorConverter participatorConverter;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;

    private UserEntity user1;
    private UserEntity user2;

    @BeforeEach
    void setup() {
        utils.cleanDb();

        RoleEntity tutorRole = new RoleEntity();
        tutorRole.setName("TUTOR");
        rolesRepository.saveAndFlush(tutorRole);

        user1 = new UserEntity();
        user1.setName("John");
        user1.setSecondName("Doe");
        user1.setEmail("aaa@test.com");
        user1.setPassword("password");
        user1.setHumanReadableID("abcabc");
        user1.setIsDeleted(false);
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(rolesRepository.findByName("TUTOR"));
        user1.setRoles(roles);
        user1 = userRepository.save(user1);

        user2 = new UserEntity();
        user2.setName("John");
        user2.setSecondName("Doe");
        user2.setEmail("bbb@test.com");
        user2.setPassword("password");
        user2.setHumanReadableID("John_Doe_1");
        user2.setIsDeleted(false);
        user2.setRoles(roles);
        user2 = userRepository.save(user2);
    }

    @Test
    @WithMockUser
    void shouldCreateChat() throws Exception {
        ChatDTO chatDTO = new ChatDTO();
        Set<ParticipatorDTO> usersSet = new HashSet<>();

        usersSet.add(participatorConverter.fromEntity(user1));
        usersSet.add(participatorConverter.fromEntity(user2));
        chatDTO.setUsers(usersSet);

        mockMvc.perform(post("/chats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chatDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldGetChatByUsers() throws Exception {
        mockMvc.perform(get("/chats/{email1}/{email2}", user1.getEmail(), user2.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void adminShouldGetAllChats() throws Exception {
        mockMvc.perform(get("/chats"))
                .andExpect(status().isOk());
    }
}