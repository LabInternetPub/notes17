package cat.tecnocampus.hateoasResources;

import cat.tecnocampus.RESTcontrollers.UserRESTController;
import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserLabResource extends ResourceSupport {

    private String username;

    private String name;

    private String secondName;

    private String email;

    private String password;

    private boolean enabled;

    private final List<NoteLabResource> noteLabs;


    public UserLabResource(UserLab user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.secondName = user.getSecondName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.getEnabled();
        this.noteLabs = new ArrayList<>();

        for (int i = 0; i < user.getNotesAsList().size(); i++) {
            Link link = linkTo(methodOn(UserRESTController.class).getUserNotes(username)).slash(i).withSelfRel();
            this.noteLabs.add(buildNoteLabResource(user.getNotesAsList().get(i), link));
        }
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<NoteLabResource> getNotes() {
        return noteLabs;
    }

    private NoteLabResource buildNoteLabResource(NoteLab n, Link link) {
        NoteLabResource nr = new NoteLabResource(n);
        nr.add(link);
        return nr;
    }

}
