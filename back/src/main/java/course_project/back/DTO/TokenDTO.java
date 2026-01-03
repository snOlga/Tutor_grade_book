package course_project.back.DTO;

import lombok.*;

@Getter
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
