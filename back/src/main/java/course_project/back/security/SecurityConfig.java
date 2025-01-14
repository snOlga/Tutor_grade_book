package course_project.back.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import course_project.back.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository repoUser;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/static/**", "/index.html", "/favicon.ico", "/manifest.json").permitAll()

                        .requestMatchers("/auth/log_in").permitAll()
                        .requestMatchers("/auth/sign_up").permitAll()

                        .requestMatchers("/lessons/create").hasAnyAuthority("ADMIN", "TUTOR")
                        .requestMatchers("/lessons/update/**").hasAnyAuthority("ADMIN", "TUTOR")
                        .requestMatchers("/lessons/delete/**").hasAnyAuthority("ADMIN", "TUTOR")

                        .anyRequest().authenticated())
                .addFilterBefore(new SecurityJwtTokenValidator(repoUser), UsernamePasswordAuthenticationFilter.class)
                .cors((cors) -> cors.disable())
                .csrf((csrf) -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
