package cat.tecnocampus.persistence;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import com.sun.tools.corba.se.idl.constExpr.Not;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by josep on 8/9/17.
 */
@Repository
public class NoteLabDAO {
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_NOTE = "insert into note_lab (title, content, date_creation, date_edit, owner) values(?, ?, ?, ?, ?)";
    private final String FIND_ALL = "select * from note_lab";
    private final String FIND_BY_USERNAME = "select * from note_lab where owner = ?";
    private final String FIND_BY_TITLE = "select * from note_lab where title = ?";
    private final String FIND_BY_ID = "select * from note_lab where id = ?";
    private final String INSERT_USER_NOTES = "INSERT INTO note_lab (title, content, date_creation, date_edit, owner) values(?, ?, ?, ?, ?)";
    private final String UPDATE_NOTE = "update note_lab set title = ?, content = ?, date_edit = ? where date_ creation = ? and title = ?";
    private final String EXISTS_NOTE = "select count(*) from note_lab where title = ? and date_creation = ?";

    private RowMapper<NoteLab> mapper = (resultSet, i) -> {
        NoteLab noteLab = new NoteLab.NoteLabBuilder(resultSet.getString("title"), resultSet.getString("content"))
                .dateCreation(resultSet.getTimestamp("date_creation").toLocalDateTime())
                .dateEdit(resultSet.getTimestamp("date_edit").toLocalDateTime())
                .build();
        return noteLab;
    };

    public NoteLabDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NoteLab> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public List<NoteLab> findByUsername(String username) {
        return jdbcTemplate.query(FIND_BY_USERNAME, new Object[]{username}, mapper);
    }

    public NoteLab findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id},mapper);
    }


    public int insert(NoteLab noteLab, UserLab userLab) {
        return jdbcTemplate.update(INSERT_NOTE, noteLab.getTitle(), noteLab.getContent(), Timestamp.valueOf(noteLab.getDateCreation()),
                Timestamp.valueOf(noteLab.getDateEdit()), userLab.getUsername());
    }

    public int[] insertUserNotes(UserLab owner) {
        return jdbcTemplate.batchUpdate(INSERT_USER_NOTES, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                NoteLab note = owner.getNotesAsList().get(i);
                preparedStatement.setString(1, note.getTitle());
                preparedStatement.setString(2, note.getContent());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(note.getDateCreation()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(note.getDateEdit()));
                preparedStatement.setString(5, owner.getUsername());
            }

            @Override
            public int getBatchSize() {
                return owner.getnotes().size();
            }
        });
    }

    public int updateNote(String oldTitle, NoteLab note) {
        return jdbcTemplate.update(UPDATE_NOTE,
                note.getTitle(), note.getContent(), LocalDateTime.now(), note.getDateCreation(), oldTitle);
    }

    public boolean existsNote(NoteLab note) {
        int countOfNotes = jdbcTemplate.queryForObject(
                EXISTS_NOTE, Integer.class, note.getTitle(), Timestamp.valueOf(note.getDateCreation()));
        return countOfNotes > 0;
    }

}
