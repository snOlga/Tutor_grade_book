package course_project.back.converters;

import java.util.HashSet;

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

    @Override
    public UserEntity fromDTO(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setName(userDTO.getName());
        userEntity.setSecondName(userDTO.getSecondName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setDescription(userDTO.getDescription());
        userEntity.setHumanReadableID(userDTO.getHumanReadableID() == null ? "" : userDTO.getHumanReadableID());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setRoles(new HashSet<>(userDTO.getRoles().stream().map(rolesRepository::findByName).toList()));
        userEntity.setIsDeleted(userDTO.getIsDeleted());
        return userEntity;
    }

    @Override
    public UserDTO fromEntity(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setSecondName(userEntity.getSecondName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setDescription(userEntity.getDescription());
        userDTO.setHumanReadableID(userEntity.getHumanReadableID());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setIsDeleted(userEntity.getIsDeleted());
        // userDTO.setPassword(userEntity.getPassword());
        userDTO.setRoles(new HashSet<>(userEntity.getRoles().stream().map(RoleEntity::getName).toList()));
        return userDTO;
    }

    @Override
    public UserEntity getFromDB(UserDTO userDTO) {
        if (!userDTO.getHumanReadableID().equals(""))
            return userRepository.findByHumanReadableID(userDTO.getHumanReadableID());
        else
            return userRepository.findByEmail(userDTO.getEmail());
    }
}
