package course_project.back.controller;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import course_project.back.DTO.UserDTO;
import course_project.back.service.UserAuthService;
import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAuthService userService;

    @PermitAll
    @PostMapping("/sign")
    public Map<String, String> signUp(@RequestBody UserDTO userDTO) {
        Map<String, String> response = defaultResponse();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        String token = userService.signUser(userDTO);
        setResponse(response, true, token);

        return response;
    }

    @PermitAll
    @PostMapping("/log")
    public Map<String, String> logIn(@RequestBody UserDTO userDTO) {
        Map<String, String> response = defaultResponse();
        String token = userService.logUser(userDTO);
        setResponse(response, true, token);
        return response;
    }

    private Map<String, String> defaultResponse() {
        Map<String, String> response = new TreeMap<>();
        setResponse(response, false, "");
        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String token) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("token", token);
    }
}
