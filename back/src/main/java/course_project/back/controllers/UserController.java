package course_project.back.controllers;

import java.util.HashSet;
import java.util.List;
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
        Set<UserRoles> roles = new HashSet<>();

        if (Boolean.parseBoolean(json.get("isTutor")))
            roles.add(UserRoles.TUTOR);
        if (Boolean.parseBoolean(json.get("isStudent")))
            roles.add(UserRoles.STUDENT);

        User user = new User(
                json.get("name"),
                json.get("secondName"),
                json.get("email"),
                json.get("phone"),
                json.get("description"),
                "",
                passwordEncoder.encode(json.get("password")), roles);

        if (userExists(user.getName(), user.getEmail()))
            return response;

        repoUser.add(user);
        user.setDefaultHumanRedableID();
        repoUser.update(user);

        Set<String> rolesStrSet = new HashSet<String>();
        for (UserRoles role : roles) {
            rolesStrSet.add(role.getRoleName());
        }

        SecurityUser securityUser = new SecurityUser(user.getName(), user.getPassword(), rolesStrSet);
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        setResponse(response, true, token, user.getName());

        return response;
    }

    @PostMapping("/log_in")
    public Map<String, String> logIn(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();

        if (validateUser(json.get("email"), json.get("password"))) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(json.get("email"), null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            String currentUserNickname = repoUser.findByEmail(json.get("email")).getEmail();
            setResponse(response, true, token, currentUserNickname);
            if (jwtProvider.isAdmin(token))
                setResponse(response, true, token, currentUserNickname);
        }
        return response;
    }

    private boolean validateUser(String login, String password) {
        List<User> users = repoUser.findAll(login);

        if (users.size() == 0)
            return false;

        for (User itUser : users) {
            if (!passwordEncoder.matches(password, itUser.getPassword()))
                return false;
        }

        return true;
    }

    private Map<String, String> defaultResponse() {
        Map<String, String> response = new TreeMap<>();
        setResponse(response, false, "", "");
        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String token, String nickname) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("token", token);
        response.put("nickname", nickname);
    }

    private boolean userExists(String nickname, String login) {
        try {
            repoUser.findByEmail(login);
            repoUser.findByName(nickname);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
