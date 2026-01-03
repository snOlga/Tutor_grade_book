package course_project.back.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import course_project.back.DTO.TokenDTO;
import course_project.back.DTO.UserDTO;
import course_project.back.service.UserAuthService;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService userService;

    @PostMapping("/sign")
    public ResponseEntity<TokenDTO> signUp(@RequestBody UserDTO userDTO) {
        TokenDTO tokens = userService.signUser(userDTO);
        return (tokens.getAccessToken() != "") ? new ResponseEntity<>(tokens, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/log")
    public ResponseEntity<TokenDTO> logIn(@RequestBody UserDTO userDTO) {
        TokenDTO tokens = userService.logUser(userDTO);
        return (tokens.getAccessToken() != "") ? new ResponseEntity<>(tokens, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO refreshToken) {
        TokenDTO tokens = userService.refreshToken(refreshToken.getRefreshToken());
        return (tokens.getAccessToken() != "") ? new ResponseEntity<>(tokens, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
