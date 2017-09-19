package cat.tecnocampus.persistence;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by josep on 8/9/17.
 */
@Repository
public class UserLabDAO {
    private JdbcTemplate jdbcTemplate;
    private NoteLabDAO noteLabDAO;

    private final String INSERT_USER = "insert into user_lab values(?, ?, ?, ?, ?, ?)";
    private final String FIND_ALL = "Select * from user_lab";
    private final String FIND_BY_USERNAME = "Select * from user_lab where username = ?";
    private final String DELETE = "delete from user_lab where username = ?";

    private UserLab userMapper(ResultSet resultSet) throws SQLException {
        UserLab userLab = new UserLab.UserLabBuilder(resultSet.getString("username"), resultSet.getString("email"))
                .name(resultSet.getString("name"))
                .secondName(resultSet.getString("second_name"))
                .build();
        return userLab;
    }

    private RowMapper<UserLab> mapper = (resultSet, i) -> {
        return userMapper(resultSet);
    };

    private RowMapper<UserLab> mapperEager = (resultSet, i) -> {
        UserLab userLab = userMapper(resultSet);

        List<NoteLab> notes = noteLabDAO.findByUsername(userLab.getUsername());
        userLab.addNotes(notes);

        return userLab;
    };

    public UserLabDAO(JdbcTemplate jdbcTemplate, NoteLabDAO noteLabDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.noteLabDAO = noteLabDAO;
    }

    public int insert(UserLab userLab) {
        System.out.println("PPPPPPAAAAASSSSSWWWWord: " + userLab.getPassword());
        return jdbcTemplate.update(INSERT_USER, userLab.getUsername(), userLab.getName(), userLab.getSecondName(),
                userLab.getEmail(), userLab.getPassword(), userLab.getEnabled());

    }

    public List<UserLab> findAllEager() {
        return jdbcTemplate.query(FIND_ALL, mapperEager);
    }

    public List<UserLab> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public UserLab findByUsername(String userName) {
        return jdbcTemplate.queryForObject(FIND_BY_USERNAME, mapperEager, userName);
    }

    public int delete(String username) {
        return jdbcTemplate.update(DELETE, username);
    }
}
