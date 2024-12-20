package course_project.back.controllers;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import course_project.back.services.UserService;
import course_project.back.business.User;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/sign_up")
    public Map<String, String> signUp(@RequestBody Map<String, String[]> json) {
        Map<String, String> response = defaultResponse();

        json.get("password")[0] = passwordEncoder.encode(json.get("password")[0]);
        User user = new User(json);

        String token = userService.signUser(user);
        setResponse(response, true, token);

        return response;
    }

    @PostMapping("/log_in")
    public Map<String, String> logIn(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();
        String token = userService.logUser(json);
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
