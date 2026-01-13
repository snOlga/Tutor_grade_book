package course_project.back.service;

import java.util.ArrayList;
import java.util.List;

import course_project.back.DTO.LessonDTO;
import course_project.back.DTO.WeekDTO;
import course_project.back.converters.LessonConverter;
import course_project.back.converters.Utils;
import course_project.back.entity.LessonEntity;
import course_project.back.repository.LessonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import course_project.back.DTO.LessonUserUpdateDTO;
import course_project.back.repository.UserRepository;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonConverter lessonConverter;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public LessonDTO create(LessonDTO lessonDTO) {
        LessonEntity result = setHumanReadableIdAndSave(lessonConverter.fromDTO(lessonDTO));
        LessonDTO resultDTO = lessonConverter.fromEntity(result);
        return resultDTO;
    }

    public LessonDTO update(LessonDTO lessonDTO) {
        if(!lessonRepository.findById(Utils.fromDTO(lessonDTO.getId())).isPresent())
            return null;
        return create(lessonDTO);
    }

    public boolean deleteById(UUID id) {
        if(!lessonRepository.findById(id).isPresent())
            return false;
        LessonEntity lessonEntity = lessonRepository.findById(id).get();
        lessonEntity.setIsDeleted(true);
        lessonRepository.save(lessonEntity);
        return true;
    }

    public List<LessonDTO> findAllByUserEmail(String email, WeekDTO weekDTO) {
        List<LessonEntity> result = lessonRepository.findAllByUserEmail(email, weekDTO.getStartDate(),
                weekDTO.getEndDate());
        result.sort((lesson1, lesson2) -> {
            return (lesson1.getStartTime().compareTo(lesson2.getStartTime()));
        });
        return result.stream().map(lessonConverter::fromEntity).toList();
    }

    public List<LessonDTO> findAllBySubject(Long id, WeekDTO weekDTO) {
        List<LessonEntity> result = new ArrayList<LessonEntity>(lessonRepository.findAllBySubjectId(id, weekDTO.getStartDate(),
                weekDTO.getEndDate()));
        result.sort((lesson1, lesson2) -> {
            return (lesson1.getStartTime().compareTo(lesson2.getStartTime()));
        });
        return result.stream().map(lessonConverter::fromEntity).toList();
    }

    private LessonEntity setHumanReadableIdAndSave(LessonEntity lessonEntity) {
        LessonEntity result = lessonRepository.save(lessonEntity);
        result.setDefaultHumanRedableID();
        return lessonRepository.save(result);
    }

    @KafkaListener(topics = "${lesson.user.update.topic}", groupId = "lesson-service-group", containerFactory = "lessonUserUpdateKafkaListenerContainerFactory")
    @Transactional
    public void handleLessonUserUpdate(LessonUserUpdateDTO dto) {
        if (dto == null || dto.getLessonId() == null) return;
        Optional<LessonEntity> maybeLesson = lessonRepository.findById(dto.getLessonId());
        if (!maybeLesson.isPresent()) return;
        LessonEntity lesson = maybeLesson.get();
        if (lesson.getUsers() == null) {
            lesson.setUsers(new HashSet<>());
        }
        if (dto.getSenderId() != null) {
            userRepository.findById(dto.getSenderId()).ifPresent(u -> lesson.getUsers().add(u));
        }
        if (dto.getRecieverId() != null) {
            userRepository.findById(dto.getRecieverId()).ifPresent(u -> lesson.getUsers().add(u));
        }
        lessonRepository.save(lesson);
    }
}
