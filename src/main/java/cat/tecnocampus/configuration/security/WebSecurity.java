package cat.tecnocampus.configuration.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by roure on 19/9/2017.
 */
@Component
public class WebSecurity {

    public boolean checkUserId(Authentication authentication, String id) {
        return id.equals(authentication.getName());
    }
}
