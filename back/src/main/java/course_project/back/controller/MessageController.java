package course_project.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.DTO.MessageDTO;
import course_project.back.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO result = messageService.create(messageDTO);
        messagingTemplate.convertAndSend("/topic/chat/" + result.getChat().getId(),
                this.getallChatMessages(result.getChat().getId()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/with_chat_id/{chatId}")
    public ResponseEntity<List<MessageDTO>> getallChatMessages(@PathVariable Long chatId) {
        List<MessageDTO> result = messageService.findAllChatMessages(chatId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update/{messageId}")
    public ResponseEntity<MessageDTO> putMethodName(@PathVariable Long messageId,
            @RequestBody MessageDTO messageDTO) {
        MessageDTO result = messageService.update(messageId, messageDTO);
        messagingTemplate.convertAndSend("/topic/chat/" + result.getChat().getId(),
                this.getallChatMessages(result.getChat().getId()));
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> deleteLesson(@PathVariable Long id) {
        MessageDTO result = messageService.deleteById(id);
        messagingTemplate.convertAndSend("/topic/chat/" + result.getChat().getId(),
                this.getallChatMessages(result.getChat().getId()));
        return result != null ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/last_message/{chatId}")
    public ResponseEntity<MessageDTO> getLastMessage(@PathVariable Long chatId) {
        MessageDTO result = messageService.findLastMessage(chatId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
