package course_project.back.converters;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
    private final ModelMapper mapper = new ModelMapper();

    public MessageConverter() {
        configureMappings();
    }

    private void configureMappings() {
        TypeMap<MessageEntity, MessageDTO> toDtoTypeMap = mapper.createTypeMap(MessageEntity.class, MessageDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(MessageDTO::setId);
        });

        TypeMap<MessageDTO, MessageEntity> toEntityTypeMap = mapper.createTypeMap(MessageDTO.class,
                MessageEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(MessageEntity::setId);
        });
    }

    @Override
    public MessageEntity fromDTO(MessageDTO messageDTO) {
        MessageEntity entity = mapper.map(messageDTO, MessageEntity.class);
        entity.setId(Utils.fromDTO(messageDTO.getId()));
        entity.setAuthor(participatorConverter.getFromDB(messageDTO.getAuthor()));
        entity.setChat(chatConverter.getFromDB(messageDTO.getChat()));
        return entity;
    }

    @Override
    public MessageDTO fromEntity(MessageEntity messageEntity) {
        if (messageEntity == null)
            return null;

        MessageDTO entity = mapper.map(messageEntity, MessageDTO.class);
        entity.setId(messageEntity.getId().toString());
        entity.setAuthor(participatorConverter.fromEntity(messageEntity.getAuthor()));
        entity.setChat(chatConverter.fromEntity(messageEntity.getChat()));
        return entity;
    }

    @Override
    public MessageEntity getFromDB(MessageDTO messageDTO) {
        return messageRepository.findById(UUID.fromString(messageDTO.getId())).get();
    }
}
