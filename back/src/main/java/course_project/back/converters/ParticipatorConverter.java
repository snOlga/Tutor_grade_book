package course_project.back.converters;

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

    @Override
    public UserEntity fromDTO(ParticipatorDTO participatorDTO) {
        return getFromDB(participatorDTO);
    }

    @Override
    public ParticipatorDTO fromEntity(UserEntity userEntity) {
        ParticipatorDTO participatorDTO = new ParticipatorDTO();
        if (userEntity == null)
            return participatorDTO;
        participatorDTO.setName(userEntity.getName());
        participatorDTO.setSecondName(userEntity.getSecondName());
        participatorDTO.setDescription(userEntity.getDescription());
        participatorDTO.setEmail(userEntity.getEmail());
        participatorDTO.setHumanReadableID(userEntity.getHumanReadableID());
        participatorDTO.setPhone(userEntity.getPhone());
        participatorDTO.setRoles(userEntity.getRoles().stream().map(RoleEntity::getName).toList());
        return participatorDTO;
    }

    @Override
    public UserEntity getFromDB(ParticipatorDTO participatorDTO) {
        if (participatorDTO.getHumanReadableID() != null && !participatorDTO.getHumanReadableID().equals(""))
            return userRepository.findByHumanReadableID(participatorDTO.getHumanReadableID());
        else
            return userRepository.findByEmail(participatorDTO.getEmail());
    }

}
