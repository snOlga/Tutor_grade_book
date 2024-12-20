package course_project.back.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import course_project.back.business.User;
import course_project.back.enums.UserRoles;
import course_project.back.repositories.UserRepository;

@Component
public class SecurityJwtTokenValidator extends OncePerRequestFilter {
    private SecurityJwtTokenProvider jwtProvider = new SecurityJwtTokenProvider();
    private UserRepository repoUser;

    public SecurityJwtTokenValidator(UserRepository repoUser) {
        this.repoUser = repoUser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String jwt = null;
        try {
            jwt = getTokenCookie(request.getCookies());
        } catch (Exception e) {
            filterChain.doFilter(request, response);
        }

        if (jwt != null) {
            System.out.println("got token: " + jwt);

            boolean isValid = jwtProvider.validateToken(jwt);

            if (isValid) {
                String userEmail = jwtProvider.getUsernameFromJWT(jwt);
                User user = repoUser.findByEmail(userEmail);
                SecurityUser securityUser = new SecurityUser(user.getEmail(), user.getPassword(),
                        roleSetToString(user.getRoles()));
                Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                        securityUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else {
                throw new SecurityException();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("token"))
                return cookie.getValue();
        }
        return null;
    }

    private Set<String> roleSetToString(Set<UserRoles> currentSet) {
        Set<String> rolesStrSet = new HashSet<String>();
        for (UserRoles role : currentSet) {
            rolesStrSet.add(role.getRoleName());
        }
        return rolesStrSet;
    }
}
