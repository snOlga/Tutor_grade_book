package course_project.back.business;
import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipatorDTO {
    private Long user_id;
    private String name;
}
