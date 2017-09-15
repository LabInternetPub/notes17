package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @PostMapping("redirectAttsExample")


}
