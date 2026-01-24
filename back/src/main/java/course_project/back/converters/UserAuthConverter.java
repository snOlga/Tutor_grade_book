package course_project.back.converters;

import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.UserDTO;
import course_project.back.converters.i.ConverterInterface;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.RolesRepository;
import course_project.back.repository.UserRepository;

@Service
public class UserAuthConverter implements ConverterInterface<UserDTO, UserEntity> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    private final ModelMapper mapper = new ModelMapper();

    public UserAuthConverter() {
        configureMappings();
    }

    private void configureMappings() {
        TypeMap<UserEntity, UserDTO> toDtoTypeMap = mapper.createTypeMap(UserEntity.class, UserDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(UserDTO::setRoles);
            m.skip(UserDTO::setPassword);
        });

        TypeMap<UserDTO, UserEntity> toEntityTypeMap = mapper.createTypeMap(UserDTO.class, UserEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(UserEntity::setId);
            m.skip(UserEntity::setHumanReadableID);
            m.skip(UserEntity::setRoles);
        });
    }

    @Override
    public UserEntity fromDTO(UserDTO userDTO) {
        UserEntity entity = mapper.map(userDTO, UserEntity.class);
        entity.setId(Utils.fromDTO(userDTO.getId()));
        entity.setHumanReadableID(userDTO.getHumanReadableID() == null ? "" : userDTO.getHumanReadableID());
        entity.setRoles(new HashSet<>(userDTO.getRoles().stream().map(rolesRepository::findByName).toList()));
        return entity;
    }

    @Override
    public UserDTO fromEntity(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        UserDTO dto = mapper.map(userEntity, UserDTO.class);
        dto.setRoles(new HashSet<>(userEntity.getRoles().stream().map(RoleEntity::getName).toList()));

        if (dto.getRoles().isEmpty())
            return null;

        return dto;
    }

    @Override
    public UserEntity getFromDB(UserDTO userDTO) {
        if (!userDTO.getHumanReadableID().equals(""))
            return userRepository.findByHumanReadableID(userDTO.getHumanReadableID());
        else
            return userRepository.findByEmail(userDTO.getEmail());
    }
}
