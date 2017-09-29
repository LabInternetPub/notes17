package cat.tecnocampus.security;

import cat.tecnocampus.domain.UserLab;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by roure on 28/09/2017.
 */
@Profile("security_jdbc")
@Service("securityService")
public class SecurityServiceJdbc implements SecurityService {
	
    private final AuthenticationManager authenticationManager;
    private final AuthoritiesDAO authoritiesDAO;
    
    public SecurityServiceJdbc(AuthenticationManager authenticationManager, AuthoritiesDAO authoritiesDAO) {
        this.authenticationManager = authenticationManager;
        this.authoritiesDAO = authoritiesDAO;
    }

    @Override
    public void login(String username, String password) {
        Set<GrantedAuthority> grantedAuthorities = authoritiesDAO.findUserRoles(username)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        User user = new User(username, password, grantedAuthorities);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    public void insertUser(UserLab user) {
        authoritiesDAO.insertUserRole(user.getUsername());
    }
}
