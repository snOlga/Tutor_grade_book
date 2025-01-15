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

    public String signUser(UserDTO userDTO) {
        if (userExists(userDTO.getEmail()))
            return "";

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity userEntity = userAuthConverter.fromDTO(userDTO);

        userRepository.save(userEntity);
        userEntity.setDefaultHumanRedableID();
        userRepository.save(userEntity);
        String token = setUserToSecurity(userEntity);
        return token;
    }

    public String logUser(UserDTO userDTO) {
        String token = "";
        UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());
        if (validateUser(userDTO.getPassword(), userEntity.getPassword())) {
            token = setUserToSecurity(userEntity);
        }
        return token;
    }

    public String setUserToSecurity(UserEntity user) {
        SecurityUser securityUser = new SecurityUser(user.getEmail(), user.getPassword(),
                user.getRoles());
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    private boolean validateUser(String dtoPassword, String entityPassword) {
        return passwordEncoder.matches(dtoPassword, entityPassword);
    }

    private boolean userExists(String login) {
        return userRepository.findByEmail(login) != null;
    }
}
