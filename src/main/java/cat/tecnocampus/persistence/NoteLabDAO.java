package cat.tecnocampus.persistence;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Created by josep on 8/9/17.
 */
@Repository
public class NoteLabDAO {
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_NOTE = "insert into note_lab (title, content, date_creation, date_edit, owner) values(?, ?, ?, ?, ?)";


    public NoteLabDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(NoteLab noteLab, UserLab userLab) {
        return jdbcTemplate.update(INSERT_NOTE, noteLab.getTitle(), noteLab.getContent(), Timestamp.valueOf(noteLab.getDateCreation()),
                Timestamp.valueOf(noteLab.getDateEdit()), userLab.getUsername());
    }
}
