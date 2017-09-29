package cat.tecnocampus.security;

import cat.tecnocampus.domain.UserLab;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Profile("security_memory")
@Service("securityService")
public class SecurityServiceMemory implements SecurityService {
    private InMemoryUserDetailsManager auth;

    public SecurityServiceMemory(InMemoryUserDetailsManager auth) {
        this.auth = auth;
    }

    @Override
    public void login(String username, String password) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        User user = new User(username, password, grantedAuthorities);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    public void insertUser(UserLab user)  {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        try {
            auth.createUser(new User(user.getUsername(), user.getPassword(), grantedAuthorities));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
