package course_project.back.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ChatDTO;
import course_project.back.converters.ChatConverter;
import course_project.back.entity.ChatEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.ChatRepository;
import course_project.back.repository.UserRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatConverter chatConverter;

    public ChatDTO create(ChatDTO chatDTO) {
        ChatEntity chatEntity = chatConverter.fromDTO(chatDTO);
        chatRepository.save(chatEntity);
        return chatConverter.fromEntity(chatEntity);
    }

    public List<ChatDTO> findAllByUserEmail(String email) {
        List<ChatEntity> result = chatRepository.findByUsers_EmailAndUsers_IsDeletedFalse(email);
        return result.stream().map(chatConverter::fromEntity).toList();
    }

    public ChatDTO findByUsersEmails(String emailFirst, String emailSecond) {
        List<ChatEntity> listOfChatsByFirstUser = chatRepository.findByUsers_EmailAndUsers_IsDeletedFalse(emailFirst);
        UserEntity user2 = userRepository.findByEmail(emailSecond);
        ChatEntity result = null;
        for (ChatEntity firstUserChat : listOfChatsByFirstUser) {
            Set<UserEntity> usersInChat = firstUserChat.getUsers();
            if (usersInChat.size() == 2 && (usersInChat.contains(user2))) {
                result = firstUserChat;
                break;
            }
        }
        return result != null ? chatConverter.fromEntity(result) : new ChatDTO();
    }

    public List<ChatDTO> findAll() {
        List<ChatEntity> result = chatRepository.findAll();
        return result.stream().map(chatConverter::fromEntity).toList();
    }

    public boolean deleteById(Long id) {
        ChatEntity chatEntity = chatRepository.findById(id).get();
        chatEntity.setIsDeleted(true);
        chatRepository.save(chatEntity);
        return chatEntity != null;
    }
}
