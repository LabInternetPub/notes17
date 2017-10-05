package cat.tecnocampus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by roure on 8/09/2017.
 */
public class UserLab {

    @NotNull(message = "username cannot be null")
    @Size(min = 4, max = 15, message = "username must be between 4 an 15 characters long")
    @Pattern(regexp = "^\\w+", message = "must have alphanumeric characters")
    private String username;

    @NotNull(message = "Name cannot be null")
    @Size(min = 4, max = 50, message = "Name must be between 4 an 15 characters long")
    @Pattern(regexp = "^[A-Z].+", message = "Name must match \\^[A-Z].*")
    private String name;

    @NotNull(message = "Second name cannot be null")
    @Size(min = 4, max = 50, message = "Second name must be between 4 an 15 characters long")
    @Pattern(regexp = "^[A-Z].+", message = "Second name must match \\^[A-Z].*")
    private String secondName;

    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b",
            message = "Email must match \\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b")
    private String email;

    @NotNull
    @Size(min = 5, message = "Password must be 5 or more characters long")
    private String password;

    private boolean enabled;

    private final Map<String,NoteLab> noteLabs;

    public UserLab() {
        noteLabs = new HashMap<>();
        enabled = true;
    }

    private UserLab(UserLabBuilder builder) {
        noteLabs = new HashMap<>();

        username = builder.username;
        name = builder.name;
        secondName = builder.secondName;
        email = builder.email;
        password = builder.password;
        enabled = builder.enabled;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    @JsonIgnore
    public Map<String, NoteLab> getNotes() {
        return this.noteLabs;
    }

    public String getPassword() {
        return password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("notes")
    public List<NoteLab> getNotesAsList() {
        Collection coll = noteLabs.values();
        if (coll instanceof List) {
            return (List) coll;
        }
        else {
            return new ArrayList<>(coll);
        }
    }

    public NoteLab getNote(String title) {
        return noteLabs.get(title);
    }

    public NoteLab addNote(NoteLab noteLab) {
        if (!noteLabs.containsKey(noteLab.getTitle())) {
            noteLabs.put(noteLab.getTitle(),noteLab);
        } else {
            throw new RuntimeException("Note's title is repeated");
        }

        return noteLab;
    }

    public NoteLab removeNote(NoteLab noteLab) {
        getNotes().remove(noteLab);

        return noteLab;
    }

    public NoteLab removeNote(String title) {
        return noteLabs.remove(title);
    }

    public void addNotes(List<NoteLab> notes) {
        notes.forEach(n -> noteLabs.put(n.getTitle(),n));
    }

    public String toString() {
        String value = "Usuari: " + this.username + ", " + this.name + " " + this.secondName + " " + this.password;
        value = value + "\n    " + this.getNotes().keySet().toString();
        return value;
    }

    public boolean existsNote(String title) {
        return noteLabs.containsKey(title);
    }

    public static class UserLabBuilder {
        private final String username;
        private String name;
        private String secondName;
        private final String email;
        private String password;
        private boolean enabled;

        public UserLabBuilder(String username, String email) {
            this.username = username;
            this.email = email;
            this.enabled = true;
        }

        public UserLabBuilder name(String name) {
            this.name = name;
            return  this;
        }

        public UserLabBuilder secondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public UserLabBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserLabBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserLab build() {
            return new UserLab(this);
        }
    }

}