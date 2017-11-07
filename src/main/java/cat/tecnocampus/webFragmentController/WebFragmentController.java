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

        List<NoteLab> notes = userUseCases.getUserNotes(principal.getName());
        model.addAttribute("notes", notes);
        model.addAttribute("noteLab", new NoteLab());

        return "fragment/currentUser";
    }

    @GetMapping("notes/{idx}")
    public String userNote(@PathVariable int idx, Principal principal, Model model) {
        List<NoteLab> notes = userUseCases.getUserNotes(principal.getName());

        model.addAttribute("noteLab", notes.get(idx));

        //return "fragment/noteDetail :: noteDetail";
        model.addAttribute("notes", notes);
        return "fragment/currentUser";
    }
}
