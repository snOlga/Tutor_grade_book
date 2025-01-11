package course_project.back.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ChatDTO;
import course_project.back.entity.ChatEntity;
import course_project.back.repository.ChatRepository;
import course_project.back.repository.UserRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    public ChatDTO create(ChatDTO chatDTO) {
        ChatEntity chatEntity = new ChatEntity(chatDTO);

        chatEntity.setUsers(
                new HashSet<>(chatDTO.getUsers().stream()
                        .map(participator -> userRepository.findByEmail(participator.getEmail())).toList()));

        chatRepository.save(chatEntity);

        return new ChatDTO(chatEntity);
    }

    public List<ChatDTO> findAllByUserEmail(String email) {
        List<ChatEntity> result = chatRepository.findByUsers_Email(email);
        return result.stream().map(ChatDTO::new).toList();
    }
}
