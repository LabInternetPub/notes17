package cat.tecnocampus.useCases;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.persistence.NoteLabDAO;
import cat.tecnocampus.persistence.UserLabDAO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by roure on 14/09/2017.
 *
 * All methods update the database
 */
@Service("userUseCases")
public class UserUseCases {
	
    private final NoteLabDAO noteLabDAO;
    
    private final UserLabDAO userLabDAO;

    public UserUseCases(NoteLabDAO NoteLabDAO, UserLabDAO UserLabDAO) {
        this.noteLabDAO = NoteLabDAO;
        this.userLabDAO = UserLabDAO;
    }

    public UserLab createUser(String username, String name, String secondName, String email) {
        UserLab userLab = new UserLab.UserLabBuilder(username, email).name(name).secondName(secondName).build();
        registerUser(userLab);
        return userLab;
    }

    //The @Transactiona annotation states that saveUser is a transaction. So ,if a unchecked exception is signaled
    // (and not cached) during the saveUser method the transaction is going to rollback
    @Transactional
    public int registerUser(UserLab userLab) {
        return userLabDAO.insert(userLab);
    }

    public int deleteUser(String username) {
        return userLabDAO.delete(username);
    }

    public NoteLab addUserNote(UserLab userLab, String title, String contents) {
        LocalDateTime now = LocalDateTime.now();
        NoteLab note = new NoteLab.NoteLabBuilder(title, contents).dateEdit(now).dateCreation(now).build();
        userLab.addNote(note);
        noteLabDAO.insert(note, userLab);
        return note;
    }

    public NoteLab addUserNote(UserLab userLab, NoteLab noteLab) {
        userLab.addNote(noteLab);
        noteLabDAO.insert(noteLab, userLab);

        return noteLab;
    }

    public NoteLab createUserNote(UserLab userLab, NoteLab noteLab) {
        noteLab.setDateCreation(LocalDateTime.now());
        noteLab.setDateEdit(LocalDateTime.now());

        return addUserNote(userLab, noteLab);
    }

    public NoteLab updateUserNote(UserLab userLab, NoteLab oldNote, String title, String contents) {
        NoteLab newNote = new NoteLab.NoteLabBuilder(title, contents)
                .dateCreation(oldNote.getDateCreation()).dateEdit(LocalDateTime.now()).build();

        userLab.removeNote(oldNote.getTitle());

        userLab.addNote(newNote);
        noteLabDAO.updateNote(oldNote.getTitle(), newNote);
        return newNote;
    }

    public List<NoteLab> getUserNotes(String userName) {
        return noteLabDAO.findByUsername(userName);
    }

    //Note that users don't have their notes with them
    public List<UserLab> getUsers() {
        return userLabDAO.findAll();
    }

    public UserLab getUser(String userName) {
        return userLabDAO.findByUsername(userName);
    }

    public boolean existsTitle(String title, UserLab user) {
        return user.existsNote(title);
    }

    public List<NoteLab> getAllNotes() {
        return noteLabDAO.findAll();
    }

    public List<NoteLab> getUserNotes(UserLab userLab) {
        if (userLab.getNotes().isEmpty()) {  //in case user has been retrieved lazily
            userLab.addNotes(noteLabDAO.findByUsername(userLab.getUsername()));
        }
        return userLab.getNotesAsList();
    }

}
