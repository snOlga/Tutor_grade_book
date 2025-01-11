package course_project.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import course_project.back.entity.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    
    List<MessageEntity> findAllByChatAndAuthor_Email(Long chatId, String email);
}
