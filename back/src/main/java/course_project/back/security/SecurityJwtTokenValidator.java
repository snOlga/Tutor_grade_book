package course_project.back.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import course_project.back.repositories.UserRepository;

import java.util.List;

public class SecurityJwtTokenValidator extends OncePerRequestFilter {
    UserRepository repoUser = new UserRepository();

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

            SecutiryJwtTokenProvider jwtProvider = new SecutiryJwtTokenProvider();
            boolean isValid = jwtProvider.validateToken(jwt);

            if (isValid) {
                String login = jwtProvider.getUsernameFromJWT(jwt);

                String authorities = "USER";
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        login, null, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Token"))
                return cookie.getValue();
        }
        return null;
    }
}
