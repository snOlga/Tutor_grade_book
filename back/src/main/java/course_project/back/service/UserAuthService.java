package course_project.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import course_project.back.DTO.UserDTO;
import course_project.back.converters.UserAuthConverter;
import course_project.back.entity.UserEntity;
import course_project.back.repository.UserRepository;
import course_project.back.security.SecurityJwtTokenProvider;
import course_project.back.security.SecurityUser;

@Service
public class UserAuthService {
    private SecurityJwtTokenProvider jwtProvider = new SecurityJwtTokenProvider();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthConverter userAuthConverter;

    public String signUser(UserDTO user) {
        if (userExists(user.getEmail()))
            return "";

        UserEntity userEntity = userAuthConverter.fromDTO(user);

        userRepository.save(userEntity);
        userEntity.setDefaultHumanRedableID();
        userRepository.save(userEntity);
        String token = setUserToSecurity(userEntity);
        return token;
    }

    public String logUser(UserDTO userDTO) {
        String token = "";
        if (validateUser(userDTO.getEmail(), userDTO.getPassword())) {
            UserEntity user = userRepository.findByEmail(userDTO.getEmail());
            token = setUserToSecurity(user);
        }
        return token;
    }

    public void setUserToSecurityByEmail(String email) {
        setUserToSecurity(userRepository.findByEmail(email));
    }

    public String setUserToSecurity(UserEntity user) {
        SecurityUser securityUser = new SecurityUser(user.getEmail(), user.getPassword(),
                user.getRoles());
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    private boolean validateUser(String login, String password) {
        return passwordEncoder.matches(password, userRepository.findByEmail(login).getPassword());
    }

    private boolean userExists(String login) {
        return userRepository.findByEmail(login) != null;
    }
}
