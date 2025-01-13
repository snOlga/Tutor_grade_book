package course_project.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ParticipatorDTO;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.UserRepository;

@Service
public class ParticipatorService {
    @Autowired
    private UserRepository repoUser;

    public ParticipatorDTO getStudentByHumanReadableID(String humanReadableID) {
        UserEntity user = repoUser.findByHumanReadableID(humanReadableID);
        boolean isStudent = user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleEntity.ROLE_STUDENT));
        return isStudent ? new ParticipatorDTO(user) : null;
    }

    public ParticipatorDTO getTutorByHumanReadableID(String humanReadableID) {
        UserEntity user = repoUser.findByHumanReadableID(humanReadableID);
        boolean isStudent = user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleEntity.ROLE_TUTOR));
        return isStudent ? new ParticipatorDTO(user) : null;
    }

    public ParticipatorDTO getParticipatorByEmail(String email) {
        UserEntity user = repoUser.findByEmail(email);
        return new ParticipatorDTO(user);
    }

    public ParticipatorDTO getParticipatorByHumanReadableId(String humanReadableID) {
        UserEntity user = repoUser.findByHumanReadableID(humanReadableID);
        return new ParticipatorDTO(user);
    }

    public ParticipatorDTO update(String humanReadableId, ParticipatorDTO participatorDTO) {
        UserEntity user = repoUser.findByHumanReadableID(humanReadableId);
        user.setName(participatorDTO.getName());
        user.setSecondName(participatorDTO.getSecondName());
        user.setEmail(participatorDTO.getEmail());
        user.setPhone(participatorDTO.getPhone());
        user.setDescription(participatorDTO.getDescription());
        user.setDefaultHumanRedableID();
        UserEntity result = repoUser.save(user);
        return new ParticipatorDTO(result);
    }

    public ParticipatorDTO deleteById(String id) {
        UserEntity user = repoUser.findByEmail(id);
        user.setIsDeleted(true);
        repoUser.save(user);
        return new ParticipatorDTO(user);
    }
}
