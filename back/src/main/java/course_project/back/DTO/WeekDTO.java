package course_project.back.DTO;

import java.sql.Date;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeekDTO {
    @NonNull
    private Date startDate;
    @NonNull
    private Date endDate;
}
