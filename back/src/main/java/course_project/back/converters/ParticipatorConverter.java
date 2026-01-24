package course_project.back.converters;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ParticipatorDTO;
import course_project.back.converters.i.ConverterInterface;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.UserRepository;

@Service
public class ParticipatorConverter implements ConverterInterface<ParticipatorDTO, UserEntity> {

    @Autowired
    private UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();

    public ParticipatorConverter() {
        configureMappings();
    }

    private void configureMappings() {
        TypeMap<UserEntity, ParticipatorDTO> toDtoTypeMap = mapper.createTypeMap(UserEntity.class, ParticipatorDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(ParticipatorDTO::setRoles);
        });
    }

    @Override
    public UserEntity fromDTO(ParticipatorDTO participatorDTO) {
        return getFromDB(participatorDTO);
    }

    @Override
    public ParticipatorDTO fromEntity(UserEntity userEntity) {
        if (userEntity == null)
            return null;

        ParticipatorDTO dto = mapper.map(userEntity, ParticipatorDTO.class);
        dto.setRoles(userEntity.getRoles().stream().map(RoleEntity::getName).toList());
        return dto;
    }

    @Override
    public UserEntity getFromDB(ParticipatorDTO participatorDTO) {
        if (participatorDTO.getHumanReadableID() != null && !participatorDTO.getHumanReadableID().equals(""))
            return userRepository.findByHumanReadableID(participatorDTO.getHumanReadableID());
        else
            return userRepository.findByEmail(participatorDTO.getEmail());
    }

}
