package course_project.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ChatDTO;
import course_project.back.DTO.MessageDTO;
import course_project.back.entity.MessageEntity;
import course_project.back.repository.ChatRepository;
import course_project.back.repository.MessageRepository;
import course_project.back.repository.UserRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    public MessageDTO create(MessageDTO messageDTO) {
        MessageEntity messageEntity = new MessageEntity(messageDTO);

        messageEntity.setAuthor(userRepository.findByEmail(messageDTO.getAuthor().getEmail()));
        messageEntity.setChat(chatRepository.findById(messageDTO.getChat().getId()).get());

        messageRepository.save(messageEntity);

        return new MessageDTO(messageEntity);
    }

    public List<MessageDTO> findAllChatMessagesByUserEmail(String email, ChatDTO chatDTO) {
        List<MessageEntity> messages = messageRepository.findAllByChatAndAuthor_Email(chatDTO.getId(), email);

        messages.sort((msg1, msg2) -> {
            return (msg1.getSentTime().compareTo(msg2.getSentTime()));
        });

        return messages.stream().map(MessageDTO::new).toList();
    }
}
