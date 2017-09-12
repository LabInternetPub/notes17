package cat.tecnocampus.domain;

import java.util.*;

/**
 * Created by roure on 8/09/2017.
 */
public class UserLab {

    private final String username;
    private final String name;
    private final String secondName;
    private final String email;

    private final Map<String,NoteLab> noteLabs;

    private UserLab(UserLabBuilder builder) {
        noteLabs = new HashMap<>();

        username = builder.username;
        name = builder.name;
        secondName = builder.secondName;
        email = builder.email;
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

    public Map<String, NoteLab> getnotes() {
        return this.noteLabs;
    }

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
        getnotes().remove(noteLab);

        return noteLab;
    }

    public NoteLab removeNote(String title) {
        return noteLabs.remove(title);
    }

    public void addNotes(List<NoteLab> notes) {
        notes.forEach(n -> noteLabs.put(n.getTitle(),n));
    }

    public String toString() {
        String value = "Usuari: " + this.username + ", " + this.name + " " + this.secondName;
        value = value + "\n    " + this.getnotes().keySet().toString();
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

        public UserLabBuilder(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public UserLabBuilder name(String name) {
            this.name = name;
            return  this;
        }

        public UserLabBuilder secondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public UserLab build() {
            return new UserLab(this);
        }
    }

}