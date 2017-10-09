package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.security.SecurityService;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("api/")
public class UserRESTController {
    private UserUseCases userUseCases;
    private PasswordEncoder passwordEncoder;
    private SecurityService securityService;

    public UserRESTController(UserUseCases userUseCases, PasswordEncoder passwordEncoder, SecurityService securityService) {
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
    public UserLab createUser(@RequestBody @Valid  UserLab user, Errors errors) {
        if (errors.hasErrors())
            return null;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userUseCases.registerUser(user);

        //insert user role in spring security classes
        securityService.insertUser(user);

        return user;
    }

    @PostMapping(value = "users/{username}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteLab createNote(@RequestBody @Valid  NoteLab note, Errors errors, @PathVariable String username) {
        UserLab user;

        if (errors.hasErrors())
            return null;

        user = userUseCases.getUser(username);
        userUseCases.createUserNote(user, note);

        return note;
    }

    @PutMapping(value = "users/{username}/notes/{oldTitle}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteLab updateNote(@RequestBody @Valid NoteLab note, Errors errors, @PathVariable String username,
                              @PathVariable String oldTitle) {
        UserLab user;

        if (errors.hasErrors())
            return null;

        user = userUseCases.getUser(username);
        userUseCases.updateUserNote(user, note, oldTitle);

        return note;
    }

    @DeleteMapping(value = "users/{username}/notes")
    public NoteLab deleteNote(@RequestBody @Valid NoteLab note, Errors errors, @PathVariable String username) {
        UserLab user;

        if (errors.hasErrors())
            return null;

        user = userUseCases.getUser(username);
        userUseCases.deleteUserNote(user, note);

        return note;
    }

}
