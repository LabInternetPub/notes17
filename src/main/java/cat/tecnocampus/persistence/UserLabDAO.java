package cat.tecnocampus.persistence;

import cat.tecnocampus.domain.UserLab;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by josep on 8/9/17.
 */
@Repository
public class UserLabDAO {
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_USER = "insert into user_lab values(?, ?, ?, ?)";

    public UserLabDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(UserLab userLab) {
        return jdbcTemplate.update(INSERT_USER, userLab.getUsername(), userLab.getName(), userLab.getSecondName(), userLab.getEmail());

    }
}
