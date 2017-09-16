package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("createUser")
public class userWebPOSTController {
    private UserUseCases userUseCases;

    public userWebPOSTController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping("noErrorControl")
    public String createUser(Model model) {
        model.addAttribute(new UserLab());
        return "userFormNoErrorControl";
    }

    @PostMapping("noErrorControl")
    public String createUserNoErrorControl(UserLab userLab, Model model, RedirectAttributes redirectAttributes) {

        userUseCases.registerUser(userLab);

        redirectAttributes.addAttribute("username", userLab.getUsername());

        //return "redirect:users/" + user.getUsername(); //this is dangerous because username can contain a dangerous string (like sql injection)


        return "redirect:/users/{username}";
    }

    @GetMapping("redirectAttributesExample")
    public String createUserRedirectAttributesExample(Model model) {
        model.addAttribute(new UserLab());
        return "userFormNoErrorControl";
    }

    @PostMapping("redirectAttributesExample")
    public String createUserRedirectAttributesExample(UserLab userLab, Model model, RedirectAttributes redirectAttributes) {

        userUseCases.registerUser(userLab);

        userLab.setName("New user name");

        redirectAttributes.addAttribute("username", userLab.getUsername());  //sent as path variable
        redirectAttributes.addAttribute("requestParam", "request param content"); //sent as request param
        redirectAttributes.addFlashAttribute("newUser", userLab); //sent in the model

        return "redirect:/createUser/showUserRedirectAttributesExample/{username}";
    }

    @GetMapping("showUserRedirectAttributesExample/{username}")
    public String showUser(@PathVariable String username, @RequestParam String requestParam, Model model) {
        model.addAttribute("registeredUserLab", userUseCases.getUser(username));
        model.addAttribute("requestParam", requestParam);

        return "showUserRedirectAttributesExample";
    }
}
