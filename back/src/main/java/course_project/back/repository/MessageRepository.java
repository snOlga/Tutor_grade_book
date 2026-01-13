package course_project.back.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import course_project.back.entity.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    @Query("SELECT m FROM MessageEntity m " +
            "WHERE m.author.isDeleted = false " +
            "AND m.isDeleted = false " +
            "AND m.chat.isDeleted = false " +
            "AND m.chat.id = :chatId ")
    List<MessageEntity> findAllByChatId(UUID chatId);
}
