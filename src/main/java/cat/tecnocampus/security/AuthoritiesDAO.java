package cat.tecnocampus.security;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("security_jdbc")
@Repository
public class AuthoritiesDAO {
    private JdbcTemplate jdbcTemplate;
    private final String INSERT_ROLE = "insert into authorities (username, role) values(?, ?)";
    private final String FIND_USER_ROLES = "select role from authorities where username = ?";


    public AuthoritiesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUserRole(String username) {
        jdbcTemplate.update(INSERT_ROLE, username, "ROLE_USER");
    }

    public List<String> findUserRoles(String username) {
        return jdbcTemplate.query(FIND_USER_ROLES,
                (rs,i) -> rs.getString("role"),
                username);
    }

}
