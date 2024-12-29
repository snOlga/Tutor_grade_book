package course_project.back.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import course_project.back.DTO.UserDTO;
import course_project.back.entity.RoleEntity;
import course_project.back.entity.UserEntity;
import course_project.back.repository.RolesRepository;
import course_project.back.repository.UserRepository;
import course_project.back.security.SecurityJwtTokenProvider;
import course_project.back.security.SecurityUser;

@Service
public class UserService {
    private SecurityJwtTokenProvider jwtProvider = new SecurityJwtTokenProvider();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private RolesRepository rolesRepository;

    public String signUser(UserDTO user) {
        if (userExists(user.getEmail()))
            return "";

        UserEntity userEntity = fromDTO(user);

        repoUser.save(userEntity);
        userEntity.setDefaultHumanRedableID();
        repoUser.save(userEntity);
        String token = setUserToSecurity(userEntity);
        return token;
    }

    public String logUser(UserDTO userDTO) {
        String token = "";
        if (validateUser(userDTO.getEmail(), userDTO.getPassword())) {
            UserEntity user = repoUser.findByEmail(userDTO.getEmail());
            token = setUserToSecurity(user);
        }
        return token;
    }

    public void setUserToSecurityByEmail(String email) {
        UserEntity user = repoUser.findByEmail(email);
        setUserToSecurity(user);
    }

    public String setUserToSecurity(UserEntity user) {
        SecurityUser securityUser = new SecurityUser(user.getEmail(), user.getPassword(),
                user.getRoles());
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return token;
    }

    private boolean validateUser(String login, String password) {
        UserEntity user = repoUser.findByEmail(login);
        if (!passwordEncoder.matches(password, user.getPassword()))
            return false;
        return true;
    }

    private boolean userExists(String login) {
        UserEntity user = repoUser.findByEmail(login);
        return user != null;
    }

    private UserEntity fromDTO(UserDTO userDTO) {
        Set<RoleEntity> roles = new HashSet<>();
        for (String role : userDTO.getRoles()) {
            RoleEntity currentRole = rolesRepository.findByName(role);
            roles.add(currentRole);
        }

        UserEntity userEntity = new UserEntity(userDTO.getName(), userDTO.getSecondName(), userDTO.getEmail(),
                userDTO.getPhone(), userDTO.getDescription(), userDTO.getHumanReadableID(), userDTO.getPassword(),
                roles);
        return userEntity;
    }
}
