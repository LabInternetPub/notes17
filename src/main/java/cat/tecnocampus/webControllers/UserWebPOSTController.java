package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.security.AuthoritiesDAO;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserWebPOSTController {
    private UserUseCases userUseCases;
    private AuthoritiesDAO authoritiesDAO;
    private PasswordEncoder passwordEncoder;

    public UserWebPOSTController(UserUseCases userUseCases, AuthoritiesDAO authoritiesDAO, PasswordEncoder passwordEncoder) {
        this.userUseCases = userUseCases;
        this.authoritiesDAO = authoritiesDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("createUser")
    public String createUser(Model model) {
        model.addAttribute(new UserLab());
        return "userForm";
    }

    @PostMapping("createUser")
    //See Errors error parameters: it takes the errors from the validators. It MUST be right after the parameters
    public String createUser(@Valid UserLab userLab, Errors errors, Model model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return "userForm";
        }

        model.addAttribute("username", userLab.getUsername());

        // encrypt the password
        userLab.setPassword(passwordEncoder.encode((CharSequence) userLab.getPassword()));

        userUseCases.registerUser(userLab);

        authoritiesDAO.insertUserRole(userLab.getUsername());

        redirectAttributes.addAttribute("username", userLab.getUsername());

        //return "redirect:users/" + user.getUsername(); //this is dangerous because username can contain a dangerous string (like sql injection)

        return "redirect:/users/{username}";
    }

    @GetMapping("/users/{user}/createNote")
    public String getCreateNote(@PathVariable String user, Model model) {

        model.addAttribute("noteLab", new NoteLab());
        model.addAttribute("user", user);

        return "noteForm";
    }

    @PostMapping("/users/{user}/createNote")
    public String postCreateNote(@Valid NoteLab noteLab, Errors errors, @PathVariable String user, Model model) {

        if (errors.hasErrors()) {
            return "noteForm";
        }

        userUseCases.createUserNote(userUseCases.getUser(user), noteLab);

        return "redirect:/users/{user}";
    }

}
