package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.security.SecurityService;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("api/")
public class UserGETcontroller {
    private UserUseCases userUseCases;
    private PasswordEncoder passwordEncoder;
    private SecurityService securityService;

    public UserGETcontroller(UserUseCases userUseCases, PasswordEncoder passwordEncoder, SecurityService securityService) {
        this.userUseCases = userUseCases;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }

    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserLab> listUsers() {
        return userUseCases.getUsers();
    }

    @GetMapping(value = "users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLab showUser(@PathVariable String username, Model model) {
        return userUseCases.getUser(username);
    }

    @PostMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLab createUser(@RequestBody UserLab user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userUseCases.registerUser(user);

        //insert user role in spring security classes
        securityService.insertUser(user);

        return user;
    }

}
