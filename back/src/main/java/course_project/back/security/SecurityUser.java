package course_project.back.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class SecurityUser implements UserDetails {
    String ROLE_PREFIX = "ROLE_";

    private String email;
    private String password;
    private Set<String> roles;

    public SecurityUser(String email, String password, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            //list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
            list.add(new SimpleGrantedAuthority(role));
        }

        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}