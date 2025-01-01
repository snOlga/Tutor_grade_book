package course_project.back.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import course_project.back.DTO.LessonDTO;
import course_project.back.entity.LessonEntity;
import course_project.back.entity.SubjectEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.LessonRepository;
import course_project.back.repository.SubjectRepository;
import course_project.back.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private LessonRequestService lessonRequestService;

    public List<LessonDTO> findAll() {
        System.out.println("Иду в бд за всеми уроками...");
        List<LessonEntity> res = lessonRepository.findAll();
        System.out.println(res.get(0) + " сущность");
        return res.stream().map(LessonDTO::new).toList();
    }

    public LessonDTO findById(Long id) {
        Optional<LessonEntity> lesson = lessonRepository.findById(id);
        return lesson.map(LessonDTO::new).orElse(null);

    }

    public LessonDTO create(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = prepareLessonEntityFromDTO(lessonDTO);
        LessonEntity result = setHumanReadableIdAndSave(lessonEntity);
        LessonDTO resultDTO = new LessonDTO(result);
        resultDTO.setUsers(lessonDTO.getUsers());
        lessonRequestService.inviteParticipators(resultDTO);
        return resultDTO;
    }

    public LessonDTO update(LessonDTO lessonDTO) {
        return create(lessonDTO);
    }

    public boolean deleteById(Long id) {
        try {
            LessonEntity lessonEntity = lessonRepository.findById(id).get(); // unexcpected behaviour of hibernate of
                                                                             // deleting by id, logged in file
            lessonEntity.setIsDeleted(true);
            lessonRepository.save(lessonEntity);
            return true;
        } catch (Exception e) {
            System.out.println("Cannot delete");
            return false;
        }
    }

    public List<LessonDTO> findAllByUserEmail(String email) {
        List<LessonEntity> res = lessonRepository.findByUsers_Email(email);
        return res.stream().map(LessonDTO::new).toList();
    }

    private LessonEntity prepareLessonEntityFromDTO(LessonDTO lessonDTO) {
        UserEntity owner = userRepository.findByEmail(lessonDTO.getOwner().getEmail());
        HashSet<UserEntity> participators = new HashSet<>();
        participators.add(owner);
        SubjectEntity subject = subjectRepository.findByName(lessonDTO.getSubject().getName());

        LessonEntity lessonEntity = new LessonEntity(lessonDTO);
        lessonEntity.setOwner(owner);
        lessonEntity.setUsers(participators);
        lessonEntity.setSubject(subject);
        lessonEntity.setHumanReadableId("");

        return lessonEntity;
    }

    private LessonEntity setHumanReadableIdAndSave(LessonEntity lessonEntity) {
        LessonEntity result = lessonRepository.save(lessonEntity);
        result.setHumanReadableId(result.getHeading() + "_" + result.getOwner().getName() + "_" + result.getId());
        LessonEntity returnedResult = lessonRepository.save(result);
        return returnedResult;
    }
}
