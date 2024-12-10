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
    public Map<String, String> signUp(@RequestBody Map<String, String[]> json) {
        Map<String, String> response = defaultResponse();

        json.get("password")[0] = passwordEncoder.encode(json.get("password")[0]);
        User user = new User(json);

        if (userExists(user.getEmail()))
            return response;

        repoUser.add(user);
        user.setDefaultHumanRedableID();
        repoUser.update(user);

        String token = setUserToSecurity(user);
        setResponse(response, true, token);

        return response;
    }

    @PostMapping("/log_in")
    public Map<String, String> logIn(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();

        if (validateUser(json.get("email"), json.get("password"))) {
            User user = repoUser.findByEmail(json.get("email"));
            String token = setUserToSecurity(user);
            setResponse(response, true, token);
        }
        return response;
    }

    private String setUserToSecurity(User user) {
        SecurityUser securityUser = new SecurityUser(user.getName(), user.getPassword(),
                roleSetToString(user.getRoles()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return token;
    }

    private boolean validateUser(String login, String password) {
        User user = repoUser.findByEmail(login);
        if (!passwordEncoder.matches(password, user.getPassword()))
            return false;
        return true;
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

    private boolean userExists(String login) {
        try {
            repoUser.findByEmail(login);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Set<String> roleSetToString(Set<UserRoles> currentSet) {
        Set<String> rolesStrSet = new HashSet<String>();
        for (UserRoles role : currentSet) {
            rolesStrSet.add(role.getRoleName());
        }
        return rolesStrSet;
    }
}
