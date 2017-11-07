package cat.tecnocampus.webFragmentController;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.useCases.UserUseCases;
import com.sun.tools.corba.se.idl.constExpr.Not;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("currentUser")
public class WebFragmentController {
    UserUseCases userUseCases;

    public WebFragmentController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping()
    public String currentUser(Model model, Principal principal) {

        return "fragment/currentUser";
    }

}
