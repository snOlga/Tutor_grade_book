package course_project.back.controllers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import course_project.back.security.SecurityUser;
import course_project.back.security.SecutiryJwtTokenProvider;
import course_project.back.repositories.UserRepository;
import course_project.back.business.User;
import course_project.back.enums.UserRoles;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserRepository repoUser = new UserRepository();
    private SecutiryJwtTokenProvider jwtProvider = new SecutiryJwtTokenProvider();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign_up")
    public Map<String, String> signUp(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();
        Set<UserRoles> someSet = new HashSet<UserRoles>();
        someSet.add(UserRoles.TUTOR);

        // TODO: MOCK HERE
        User user = new User(json.get("name"), json.get("login"), json.get("name"),
                passwordEncoder.encode(json.get("password")), someSet);

        if (userExists(user.getName(), user.getLogin()))
            return response;

        repoUser.add(user);

        Set<String> someStrSet = new HashSet<String>();
        someStrSet.add(UserRoles.TUTOR.getRoleName());

        SecurityUser securityUser = new SecurityUser(user.getName(), user.getPassword(), someStrSet);
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        setResponse(response, true, token, user.getName(), "user");

        return response;
    }

    private Map<String, String> defaultResponse() {
        Map<String, String> response = new TreeMap<>();
        setResponse(response, false, "", "", "user");
        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String token, String nickname,
            String role) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("token", token);
        response.put("nickname", nickname);
        response.put("role", role);
    }

    private boolean userExists(String nickname, String login) {
        try {
            repoUser.findByLogin(login);
            repoUser.findByName(nickname);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
