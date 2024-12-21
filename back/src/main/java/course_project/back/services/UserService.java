package course_project.back.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import course_project.back.business.User;
import course_project.back.enums.UserRoles;
import course_project.back.repositories.UserRepository;
import course_project.back.security.SecurityJwtTokenProvider;
import course_project.back.security.SecurityUser;

@Service
public class UserService {
    private SecurityJwtTokenProvider jwtProvider = new SecurityJwtTokenProvider();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repoUser;

    public String signUser(User user) {
        if (userExists(user.getEmail()))
            return "";
        repoUser.save(user);
        user.setDefaultHumanRedableID();
        repoUser.save(user);
        String token = setUserToSecurity(user);
        return token;
    }

    public String logUser(@RequestBody Map<String, String> json) {
        String token = "";
        if (validateUser(json.get("email"), json.get("password"))) {
            User user = repoUser.findByEmail(json.get("email"));
            token = setUserToSecurity(user);
        }
        return token;
    }

    public void setUserToSecurityByEmail(String email) {
        User user = repoUser.findByEmail(email);
        setUserToSecurity(user);
    }

    public String setUserToSecurity(User user) {
        SecurityUser securityUser = new SecurityUser(user.getEmail(), user.getPassword(),
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
