package cat.tecnocampus.hateoasResources;

import cat.tecnocampus.domain.NoteLab;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

public class NoteLabResource extends ResourceSupport{
    @JsonUnwrapped
    NoteLab note;

    public NoteLabResource(NoteLab note) {

        this.note = note;
    }

    public NoteLab getNote() {
        return note;
    }
}
