package course_project.back.service;

import java.util.HashSet;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Service;

import course_project.back.DTO.ChatDTO;
import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.LessonRequestDTO;
import course_project.back.DTO.MessageDTO;
import course_project.back.DTO.ParticipatorDTO;
import course_project.back.DTO.TokenDTO;
import course_project.back.DTO.UserDTO;

@Service
public class SanitizerService {

    PolicyFactory policy = Sanitizers.FORMATTING
            .and(Sanitizers.LINKS);

    public String sanitize(String input) {
        return policy.sanitize(input);
    }

    public ChatDTO sanitize(ChatDTO input) {
        input.setId(policy.sanitize(input.getId()));
        return input;
    }

    public LessonDTO sanitize(LessonDTO input) {
        input.setId(policy.sanitize(input.getId()));
        input.setHomework(policy.sanitize(input.getHomework()));
        input.setDescription(policy.sanitize(input.getDescription()));
        input.setHumanReadableId(policy.sanitize(input.getHumanReadableId()));
        input.setHeading(policy.sanitize(input.getHeading()));
        return input;
    }

    public LessonRequestDTO sanitize(LessonRequestDTO input) {
        input.setId(policy.sanitize(input.getId()));
        return input;
    }

    public MessageDTO sanitize(MessageDTO input) {
        input.setId(policy.sanitize(input.getId()));
        input.setText(policy.sanitize(input.getText()));
        return input;
    }

    public ParticipatorDTO sanitize(ParticipatorDTO input) {
        input.setName(policy.sanitize(input.getName()));
        input.setSecondName(policy.sanitize(input.getSecondName()));
        input.setPhone(policy.sanitize(input.getPhone()));
        input.setDescription(policy.sanitize(input.getDescription()));
        input.setHumanReadableID(policy.sanitize(input.getHumanReadableID()));
        input.setRoles(input.getRoles().stream().map(policy::sanitize).toList());
        return input;
    }

    public TokenDTO sanitize(TokenDTO input) {
        return new TokenDTO(
                policy.sanitize(input.getAccessToken()),
                policy.sanitize(input.getRefreshToken()));
    }

    public UserDTO sanitize(UserDTO input) {
        input.setId(policy.sanitize(input.getId()));
        input.setName(policy.sanitize(input.getName()));
        input.setSecondName(policy.sanitize(input.getSecondName()));
        input.setPhone(policy.sanitize(input.getPhone()));
        input.setDescription(policy.sanitize(input.getDescription()));
        input.setHumanReadableID(policy.sanitize(input.getHumanReadableID()));
        input.setPassword(policy.sanitize(input.getPassword()));
        input.setRoles(new HashSet<>(input.getRoles().stream().map(policy::sanitize).toList()));
        return input;
    }
}
