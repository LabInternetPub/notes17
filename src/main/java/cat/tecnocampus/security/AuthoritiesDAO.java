package cat.tecnocampus.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthoritiesDAO {
    JdbcTemplate jdbcTemplate;
    final String INSERT_ROLE = "insert into authorities (username, role) values(?, ?)";

    public AuthoritiesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUserRole(String username) {
        jdbcTemplate.update(INSERT_ROLE, username, "ROLE_USER");
    }

}
