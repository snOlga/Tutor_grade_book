package course_project.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.DTO.ChatDTO;
import course_project.back.DTO.MessageDTO;
import course_project.back.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO result = messageService.create(messageDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/with_user/{email}")
    public ResponseEntity<List<MessageDTO>> getallChatMessages(@PathVariable String email,
            @RequestBody ChatDTO chatDTO) {
        List<MessageDTO> result = messageService.findAllChatMessagesByUserEmail(email, chatDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
