package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.hateoasResources.NoteLabResource;
import cat.tecnocampus.hateoasResources.UserLabResource;
import cat.tecnocampus.security.SecurityService;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public List<UserLabResource> listUsers() {
        List<UserLabResource> userLabResources = new ArrayList<>();

        userUseCases.getUsers().forEach(u-> {
            Link link = linkTo(methodOn(UserRESTController.class).listUsers()).slash(u.getUsername()).withSelfRel();
            UserLabResource ur = buildUserResource(u, link);
            userLabResources.add(ur);
                }
        );

        return userLabResources;
    }

    @GetMapping(value = "users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLabResource showUser(@PathVariable String username) {
        Link link = linkTo(methodOn(UserRESTController.class).showUser(username)).withSelfRel();

        return buildUserResource(userUseCases.getUser(username), link);
    }

    @PostMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLabResource createUser(@RequestBody @Valid  UserLab user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userUseCases.registerUser(user);

        //insert user role in spring security classes
        securityService.insertUser(user);

        Link link = linkTo(methodOn(UserRESTController.class).createUser(user)).slash(user.getUsername()).withSelfRel();

        return buildUserResource(user, link);
    }

    @GetMapping(value = "users/{username}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteLabResource> getUserNotes(@PathVariable String username) {
        List<NoteLabResource> noteLabResources = new ArrayList<>();
        List<NoteLab> noteLabs = userUseCases.getUser(username).getNotesAsList();

        for (int i=0; i < noteLabs.size(); i++) {
            Link link = linkTo(methodOn(UserRESTController.class).getUserNotes(username)).slash(i).withSelfRel();
            noteLabResources.add(buildNoteLabResource(noteLabs.get(i), link));
        }
        return noteLabResources;
    }

    @GetMapping(value = "users/{username}/notes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteLabResource getUserNote(@PathVariable String username, @PathVariable Integer id) {
        Link link = linkTo(methodOn(UserRESTController.class).getUserNote(username, Integer.valueOf(id))).withSelfRel();

        return buildNoteLabResource(userUseCases.getUser(username).getNotesAsList().get(id.intValue()), link);
    }


    @PostMapping(value = "users/{username}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteLabResource createNote(@RequestBody @Valid  NoteLab note, @PathVariable String username) {
        UserLab user;

        user = userUseCases.getUser(username);
        userUseCases.createUserNote(user, note);

        Link link = linkTo(methodOn(UserRESTController.class).getUserNotes(username)).slash(user.getNotesAsList().size()-1).withSelfRel();
        return buildNoteLabResource(note, link);
    }

    @PutMapping(value = "users/{username}/notes/{oldTitle}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteLabResource updateNote(@RequestBody @Valid NoteLab note, @PathVariable String username,
                              @PathVariable String oldTitle) {
        UserLab user;
        NoteLab newNote;

        user = userUseCases.getUser(username);
        newNote = userUseCases.updateUserNote(user, note, oldTitle);

        Link link = linkTo(UserRESTController.class).slash("users").slash(username).slash("notes")
                .slash(user.getNoteIndex(newNote)).withSelfRel();
        return buildNoteLabResource(note, link);
    }

    @DeleteMapping(value = "users/{username}/notes")
    public NoteLab deleteNote(@RequestBody @Valid NoteLab note, @PathVariable String username) {
        UserLab user;

        user = userUseCases.getUser(username);
        userUseCases.deleteUserNote(user, note);

        return note;
    }

    private UserLabResource buildUserResource(UserLab u, Link link) {
        UserLabResource ur = new UserLabResource(u);
        ur.add(link);
        ur.add(linkTo(methodOn(UserRESTController.class).getUserNotes(u.getUsername())).withRel("notes"));
        return ur;
    }

    private NoteLabResource buildNoteLabResource(NoteLab n, Link link) {
        NoteLabResource nr = new NoteLabResource(n);
        System.out.println(n.getTitle());
        nr.add(link);
        return nr;
    }

}
