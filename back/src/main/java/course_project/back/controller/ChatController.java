package course_project.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.back.DTO.ChatDTO;
import course_project.back.service.ChatService;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatDTO chatDTO) {
        ChatDTO result = chatService.create(chatDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/with_user/{email}")
    public ResponseEntity<List<ChatDTO>> getAllUserChats(@PathVariable String email) {
        List<ChatDTO> result = chatService.findAllByUserEmail(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/with_users/{emailFirst}/{emailSecond}")
    public ResponseEntity<ChatDTO> getAllUserChats(
            @PathVariable String emailFirst,
            @PathVariable String emailSecond) {

        ChatDTO result = chatService.findByUsersEmails(emailFirst, emailSecond);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAllChats() {
        List<ChatDTO> result = chatService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        boolean deleted = chatService.deleteById(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
