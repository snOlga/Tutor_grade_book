package course_project.back.converters;

import java.util.HashSet;

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

    @Override
    public ChatEntity fromDTO(ChatDTO chatDTO) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chatDTO.getId());
        chatEntity.setIsDeleted(chatDTO.getIsDeleted());
        chatEntity.setUsers(new HashSet<>(chatDTO.getUsers().stream().map(participatorConverter::fromDTO).toList()));
        return chatEntity;
    }

    @Override
    public ChatDTO fromEntity(ChatEntity chatEntity) {
        if (chatEntity == null)
            return null;

        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setId(chatEntity.getId());
        chatDTO.setIsDeleted(chatEntity.getIsDeleted());
        chatDTO.setUsers(new HashSet<>(chatEntity.getUsers().stream().map(participatorConverter::fromEntity).toList()));

        return chatDTO;
    }

    @Override
    public ChatEntity getFromDB(ChatDTO chatDTO) {
        return chatRepository.findById(chatDTO.getId()).get();
    }
}
