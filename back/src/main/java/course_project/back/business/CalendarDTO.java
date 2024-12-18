package course_project.back.business;

import java.sql.Timestamp;
import java.util.List;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDTO {
    private Long id;
    private List<ParticipatorDTO> studentsParticipator;
    private List<ParticipatorDTO> tutorsParticipator;
    private Timestamp timestamp;
    private Integer duration;
    private String heading;
    private String description;
}
