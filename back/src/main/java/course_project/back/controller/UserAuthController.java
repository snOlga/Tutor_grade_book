package course_project.back.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import course_project.back.DTO.UserDTO;
import course_project.back.service.UserAuthService;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService userService;

    @PostMapping("/sign")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
        String token = userService.signUser(userDTO);
        return (token != null || token != "") ? new ResponseEntity<>(token, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/log")
    public ResponseEntity<String> logIn(@RequestBody UserDTO userDTO) {
        String token = userService.logUser(userDTO);
        return (token != null || token != "") ? new ResponseEntity<>(token, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
