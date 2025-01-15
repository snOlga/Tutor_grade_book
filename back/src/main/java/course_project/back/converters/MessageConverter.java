package course_project.back.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.MessageDTO;
import course_project.back.converters.i.ConverterInterface;
import course_project.back.entity.MessageEntity;
import course_project.back.repository.MessageRepository;

@Service
public class MessageConverter implements ConverterInterface<MessageDTO, MessageEntity> {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatConverter chatConverter;
    @Autowired
    private ParticipatorConverter participatorConverter;

    @Override
    public MessageEntity fromDTO(MessageDTO messageDTO) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(messageDTO.getId());
        messageEntity.setIsEdited(messageDTO.getIsEdited());
        messageEntity.setIsDeleted(messageDTO.getIsDeleted());
        messageEntity.setSentTime(messageDTO.getSentTime());
        messageEntity.setText(messageDTO.getText());
        messageEntity.setAuthor(participatorConverter.getFromDB(messageDTO.getAuthor()));
        messageEntity.setChat(chatConverter.getFromDB(messageDTO.getChat()));
        return messageEntity;
    }

    @Override
    public MessageDTO fromEntity(MessageEntity messageEntity) {
        if (messageEntity == null)
            return null;

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(messageEntity.getId());
        messageDTO.setIsEdited(messageEntity.getIsEdited());
        messageDTO.setIsDeleted(messageEntity.getIsDeleted());
        messageDTO.setSentTime(messageEntity.getSentTime());
        messageDTO.setText(messageEntity.getText());
        messageDTO.setAuthor(participatorConverter.fromEntity(messageEntity.getAuthor()));
        messageDTO.setChat(chatConverter.fromEntity(messageEntity.getChat()));
        return messageDTO;
    }

    @Override
    public MessageEntity getFromDB(MessageDTO messageDTO) {
        return messageRepository.findById(messageDTO.getId()).get();
    }
}
