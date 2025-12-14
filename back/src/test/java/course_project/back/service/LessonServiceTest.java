package course_project.back.service;

import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.WeekDTO;
import course_project.back.converters.LessonConverter;
import course_project.back.entity.LessonEntity;
import course_project.back.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonConverter lessonConverter;

    @InjectMocks
    private LessonService lessonService;

    @Test
    void shouldReturnLessonsSortedByStartTime() {
        // given
        WeekDTO week = new WeekDTO(
                new Date(2025, 1, 1),
                new Date(2025, 1, 7));

        LessonEntity late = new LessonEntity();
        late.setStartTime(new Timestamp(0, 0, 0, 12, 0, 0, 0) );

        LessonEntity early = new LessonEntity();
        late.setStartTime(new Timestamp(0, 0, 0, 9, 0, 0, 0) );

        when(lessonRepository.findAllBySubjectId(1L, week.getStartDate(), week.getEndDate()))
                .thenReturn(List.of(late, early));

        // when
        List<LessonDTO> result = lessonService.findAllBySubject(1L, week);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStartTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(result.get(1).getStartTime()).isEqualTo(LocalTime.of(12, 0));
    }
}
