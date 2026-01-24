package course_project.back.converters;

import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ChatDTO;
import course_project.back.converters.i.ConverterInterface;
import course_project.back.entity.ChatEntity;
import course_project.back.repository.ChatRepository;

@Service
public class ChatConverter implements ConverterInterface<ChatDTO, ChatEntity> {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ParticipatorConverter participatorConverter;
    private final ModelMapper mapper = new ModelMapper();

    public ChatConverter() {
        configureMappings();
    }

    private void configureMappings() {
        TypeMap<ChatEntity, ChatDTO> toDtoTypeMap = mapper.createTypeMap(ChatEntity.class,
                ChatDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(ChatDTO::setId);
            m.skip(ChatDTO::setUsers);
        });

        TypeMap<ChatDTO, ChatEntity> toEntityTypeMap = mapper.createTypeMap(ChatDTO.class,
                ChatEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(ChatEntity::setId);
            m.skip(ChatEntity::setUsers);
        });
    }

    @Override
    public ChatEntity fromDTO(ChatDTO chatDTO) {
        ChatEntity chatEntity = mapper.map(chatDTO, ChatEntity.class);
        chatEntity.setId(Utils.fromDTO(chatDTO.getId()));
        chatEntity.setUsers(new HashSet<>(chatDTO.getUsers().stream().map(participatorConverter::fromDTO).toList()));
        return chatEntity;
    }

    @Override
    public ChatDTO fromEntity(ChatEntity chatEntity) {
        if (chatEntity == null)
            return null;

        ChatDTO chatDTO = mapper.map(chatEntity, ChatDTO.class);
        chatDTO.setId(chatEntity.getId().toString());
        chatDTO.setUsers(new HashSet<>(chatEntity.getUsers().stream().map(participatorConverter::fromEntity).toList()));

        return chatDTO;
    }

    @Override
    public ChatEntity getFromDB(ChatDTO chatDTO) {
        return chatRepository.findById(Utils.fromDTO(chatDTO.getId())).get();
    }
}
