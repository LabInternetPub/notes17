package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("api/")
public class UserGETcontroller {
    private UserUseCases userUseCases;

    public UserGETcontroller(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
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
        userUseCases.registerUser(user);
        return user;
    }

}
