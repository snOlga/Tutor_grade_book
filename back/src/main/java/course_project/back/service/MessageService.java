package course_project.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.MessageDTO;
import course_project.back.converters.MessageConverter;
import course_project.back.entity.MessageEntity;
import course_project.back.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageConverter messageConverter;

    public MessageDTO create(MessageDTO messageDTO) {
        MessageEntity messageEntity = messageConverter.fromDTO(messageDTO);
        messageRepository.save(messageEntity);
        return messageConverter.fromEntity(messageEntity);
    }

    public List<MessageDTO> findAllChatMessages(Long chatId) {
        List<MessageEntity> messages = messageRepository.findAllByChatId(chatId);
        messages.sort((msg1, msg2) -> {
            return (msg1.getSentTime().compareTo(msg2.getSentTime()));
        });
        return messages.stream().map(messageConverter::fromEntity).toList();
    }

    public MessageDTO update(Long id, MessageDTO messageDTO) {
        MessageEntity messageEntity = messageRepository.findById(id).orElse(null);
        if (messageEntity == null)
            return null;
        messageEntity.setIsEdited(true);
        messageEntity.setText(messageDTO.getText());
        messageRepository.save(messageEntity);
        return messageConverter.fromEntity(messageEntity);
    }

    public MessageDTO deleteById(Long id) {
        MessageEntity messageEntity = messageRepository.findById(id).get();
        messageEntity.setIsDeleted(true);
        messageRepository.save(messageEntity);
        return messageConverter.fromEntity(messageEntity);
    }

    public MessageDTO findLastMessage(Long id) {
        List<MessageDTO> listOfMessages = findAllChatMessages(id);
        return listOfMessages.size() > 0 ? listOfMessages.get((listOfMessages.size() - 1)) : new MessageDTO();
    }
}
