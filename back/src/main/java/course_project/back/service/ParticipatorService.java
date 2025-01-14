package course_project.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ParticipatorDTO;
import course_project.back.converters.ParticipatorConverter;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.UserRepository;

@Service
public class ParticipatorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipatorConverter participatorConverter;

    public ParticipatorDTO getStudentByHumanReadableID(String humanReadableID) {
        UserEntity user = userRepository.findByHumanReadableID(humanReadableID);
        boolean isStudent = user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleEntity.ROLE_STUDENT));
        return isStudent ? participatorConverter.fromEntity(user) : null;
    }

    public ParticipatorDTO getTutorByHumanReadableID(String humanReadableID) {
        UserEntity user = userRepository.findByHumanReadableID(humanReadableID);
        boolean isStudent = user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleEntity.ROLE_TUTOR));
        return isStudent ? participatorConverter.fromEntity(user) : null;
    }

    public ParticipatorDTO getParticipatorByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return participatorConverter.fromEntity(user);
    }

    public ParticipatorDTO getParticipatorByHumanReadableId(String humanReadableID) {
        UserEntity user = userRepository.findByHumanReadableID(humanReadableID);
        return participatorConverter.fromEntity(user);
    }

    public ParticipatorDTO update(String humanReadableId, ParticipatorDTO participatorDTO) {
        UserEntity user = userRepository.findByHumanReadableID(humanReadableId);
        user.setName(participatorDTO.getName());
        user.setSecondName(participatorDTO.getSecondName());
        user.setEmail(participatorDTO.getEmail());
        user.setPhone(participatorDTO.getPhone());
        user.setDescription(participatorDTO.getDescription());
        user.setDefaultHumanRedableID();
        UserEntity result = userRepository.save(user);
        return participatorConverter.fromEntity(result);
    }

    public ParticipatorDTO deleteById(String id) {
        UserEntity user = userRepository.findByEmail(id);
        user.setIsDeleted(true);
        userRepository.save(user);
        return participatorConverter.fromEntity(user);
    }
}
