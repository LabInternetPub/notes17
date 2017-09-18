package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("createUser")
public class UserWebPOSTController {
    private UserUseCases userUseCases;

    public UserWebPOSTController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping
    public String createUser(Model model) {
        model.addAttribute(new UserLab());
        return "userForm";
    }

    @PostMapping
    //See Errors error parameters: it takes the errors from the validators. It MUST be right after the parameters
    public String createUser(@Valid UserLab userLab, Errors errors, Model model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return "userForm";
        }

        model.addAttribute("username", userLab.getUsername());

        userUseCases.registerUser(userLab);

        redirectAttributes.addAttribute("username", userLab.getUsername());

        //return "redirect:users/" + user.getUsername(); //this is dangerous because username can contain a dangerous string (like sql injection)

        return "redirect:/users/{username}";
    }

}
